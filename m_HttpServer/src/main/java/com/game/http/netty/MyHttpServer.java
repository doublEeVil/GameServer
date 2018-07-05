package com.game.http.netty;

import com.game.common.util.ClassUtils;
import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


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

    /**
     * 指定基础包方式启动
     * @param basePackage
     * @throws Exception
     */
    public void launch(String basePackage) throws Exception {
        if (null == basePackage) {
            basePackage = "";
        }
        initHttpController(basePackage);
        launch0();
    }

    /**
     * 启动application context的方式启动
     * 如果是使用这种方式启动的，必须添加注解@Comment
     * 自定义注解默认是不会扫描的
     * @param ctx
     * @throws Exception
     */
    public void launch(ApplicationContext ctx) throws Exception {
        //
        urlControllerMap = new HashMap<>();
        Map<String, Object> map = ctx.getBeansWithAnnotation(HandleHttp.class);
        for (Object obj : map.values()) {
            HandleHttp handler = obj.getClass().getAnnotation(HandleHttp.class);
            String url = handler.url();
            if (urlControllerMap.containsKey(url)) {
                throw new RuntimeException("注解复用:" + obj.getClass().getName());
            }
            try {
                BaseHttpController controller = (BaseHttpController)obj;
                urlControllerMap.put(url, controller);
            } catch (Exception e) {
                throw new RuntimeException("注解类必须是继承BaseHttpController：" + obj);
            }
        }
        launch0();
    }

    private void launch0() throws Exception{
        System.out.println("----开启启动http server port:" + port);
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

    private void initHttpController(String basePackage) {
        urlControllerMap = new HashMap<>();
        Map<String, Class<?>> classMap = ClassUtils.getClassMapByAnnounce(basePackage, HandleHttp.class);
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
