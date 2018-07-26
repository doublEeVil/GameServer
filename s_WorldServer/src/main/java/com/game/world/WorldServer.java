package com.game.world;

import com.game.http.netty.MyHttpServer;
import com.game.net.server.NetServer;
import com.game.net.server.ServerType;
import com.game.world.config.ServerConfig;
import com.game.world.event.EventManager;
import com.game.world.listener.WorldServerListener;
import com.game.world.thread.ShutdownThread;
import com.game.zookeeper.ZkpRegistry;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WorldServer {
    private ServerConfig serverConfig; // 服务器配置
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
        initEvent();
        initNetwork();
        initService();
        registerToZookeeper();
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
        NetServer server = new NetServer(serverConfig.getServerPort(), ServerType.TP_WEBSOCKET, SERVER_CTX);
        server.addListener(new WorldServerListener());
        server.launch();
    }


    private void initService() {
    }

    private void registerToZookeeper(){
        String zkpAddr = serverConfig.getZookeeperAddr();
        String zkpRootPath = serverConfig.getZkpRootPath();
        String zkpDataPath = serverConfig.getServerId() + "_m";
        ZkpRegistry registry = new ZkpRegistry(zkpAddr, zkpRootPath, zkpDataPath);

        // 发送数据格式 192.168.0.192:8001
        String data = serverConfig.getOpenAddr();
        registry.register(data);
    }

}
