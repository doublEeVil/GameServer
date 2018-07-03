package com.game.http.netty;

import com.game.common.util.ClassUtils;
import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/12/28.
 */
public class MyHttpServer {
    private final static Logger logger = LoggerFactory.getLogger(MyHttpServer.class);

    private final int port;
    private static Map<String, BaseHttpController> urlControllerMap;

    public MyHttpServer(int port) {
        this.port = port;
    }

    public void launch() throws Exception {
        initHttpController();

        ServerBootstrap server = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            server.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new MyNettyInitalizer());

            logger.info("Netty server has started on port : " + port);

            server.bind().sync().channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }
    }

    private void initHttpController() {
        urlControllerMap = new HashMap<>();
        Map<String, Class<?>> classMap = ClassUtils.getClassMapByAnnounce("com.game", HandleHttp.class);
        for (Class clazz : classMap.values()) {
            HandleHttp handler = (HandleHttp)clazz.getAnnotation(HandleHttp.class);
            String url = handler.url();
            if (urlControllerMap.containsKey(url)) {
                throw new RuntimeException("注解复用:" + clazz.getName());
            }
            try {
                BaseHttpController controller = (BaseHttpController)clazz.newInstance();
                urlControllerMap.put(url, controller);
            } catch (Exception e) {
                throw new RuntimeException("注解类必须是继承BaseHttpController：" + clazz);
            }
        }
    }

    public static Map<String, BaseHttpController> getUrlControllerMap() {
        return urlControllerMap;
    }
}
