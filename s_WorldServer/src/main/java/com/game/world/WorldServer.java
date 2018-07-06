package com.game.world;

import com.game.cache.ehcache.MyCacheManager;
import com.game.http.netty.MyHttpServer;
import com.game.world.config.ServerConfig;
import com.game.world.event.EventManager;
import com.game.world.net.IDataHandler;
import com.game.world.net.IHandler;
import com.game.world.net.ServerInitializer;
import com.game.world.procol.ProtocolFactory;
import com.game.world.thread.ShutdownThread;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Map;

public class WorldServer {
    private ServerConfig serverConfig; // 服务器配置
    private final EventLoopGroup boss = new NioEventLoopGroup(1);
    private final EventLoopGroup worker = new NioEventLoopGroup();
    private ChannelFuture channelFuture;
    private static ApplicationContext SERVER_CTX;

    public static void main(String[] args) throws Exception{
        // 配置spring上下文
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        SERVER_CTX = ctx;

        // 配置结束
        new WorldServer().launch();
    }

    public  static ApplicationContext getContext() {
        return SERVER_CTX;
    }

    public void launch() throws Exception {
        System.out.println("----准备启动 world server----");
        long t1 = System.currentTimeMillis();
        onStart();
        long t2 = System.currentTimeMillis();
        System.out.println("===time: " + (t2 - t1) + " ms");
        System.out.println("----world server 启动完成----");
        // 程序退出钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownThread()));
    }

    private void onStart() throws Exception{
        initConfig();
        initHttp();
        initProtocol();
        initEvent();
        initNetwork();
        initService();
    }

    /**
     * 初始化基本配置
     * @throws Exception
     */
    private void initConfig() throws Exception{
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.setEncoding("utf-8");
        config.load("config.properties");
        this.serverConfig = new ServerConfig(config);
    }

    /**
     * 开启一个http 服务器
     */
    private void initHttp() throws Exception{
        // 一个自定义的http服务器
        new Thread(()->{
            try {
                new MyHttpServer(9000).launch(SERVER_CTX);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 处理协议与Handler的对应关系
     */
    private void initProtocol() {
        ApplicationContext context = SERVER_CTX;
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(IHandler.class);
        for (Object obj : beansWithAnnotation.values()) {
            Class clazz = obj.getClass();
            IHandler handler = (IHandler)clazz.getAnnotation(IHandler.class);
            ProtocolFactory.register( handler.handData(), (IDataHandler) obj);
        }
    }

    /**
     * 处理事件
     */
    private void initEvent() {
        EventManager.getInstance().initEvent(SERVER_CTX);
    }

    /**
     * 开启网络层，内部用netty解析
     * @throws Exception
     */
    private void initNetwork() throws Exception{
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
             .childHandler(new ServerInitializer())
             .channel(NioServerSocketChannel.class);
            channelFuture = b.bind(serverConfig.getServerPort()).sync();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                shutdownNet();
            }));
        }
    }

    private void shutdownNet(){
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    private void initService() {
    }

}
