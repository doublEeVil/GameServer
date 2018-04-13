package com.game.world;


import com.game.world.config.ServerConfig;
import com.game.world.net.ServerInitializer;
import com.game.world.servlet.TestServlet;
import com.game.world.thread.ShutdownThread;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class WorldServer {
    ServerConfig serverConfig; // 服务器配置

    public static void main(String[] args) throws Exception{
        new WorldServer().launch();

    }

    public void launch() throws Exception {
        System.out.println("----准备启动 world server----");
        onStart();
        System.out.println("----world server 启动完成----");
        // 程序退出钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownThread()));
    }

    private void onStart() throws Exception{
        initConfig();
        initServlet();
        initProcol();
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
    private void initServlet() throws Exception{
        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(serverConfig.getHttpPort());
        server.addConnector(connector);
        Context root = new Context(server, "/", 1);
        root.addServlet(new ServletHolder(new TestServlet()), "/test");
        server.start();
    }

    private void initProcol() {

    }

    private void initEvent() {

    }

    private void initNetwork() throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
             .childHandler(new ServerInitializer())
             .channel(NioServerSocketChannel.class);
            Channel ch = b.bind(serverConfig.getServerPort()).sync().channel();
            //ch.closeFuture().sync();
        } finally {
            //boss.shutdownGracefully();
            //worker.shutdownGracefully();
        }
    }

    private void initService() {

    }

}
