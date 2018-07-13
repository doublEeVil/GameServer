package com.game.ipd;

import com.game.http.netty.MyHttpServer;
import com.game.ipd.config.ServerConfig;
import com.game.ipd.listener.ServerNodeListener;
import com.game.zookeeper.ZkpDiscovery;
import com.game.zookeeper.ZkpListener;
import com.game.zookeeper.ZkpRegistry;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IpdServer {
    private static ApplicationContext CTX;
    private static ServerConfig serverConfig;
    private static ServerNodeListener listener;

    public static void main(String[] args) throws Exception{
        System.out.println("====try start ipd server");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        CTX = ctx;

        new IpdServer().launch();
    }

    public static ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void launch() throws Exception{
        // 基本配置，端口等
        initConfig();
        // 监听zookeeper已有的节点
        watchNodes();
        // http服务
        initHttp();
    }

    private void initConfig() throws Exception{
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.setEncoding("utf-8");
        config.load("config.properties");
        serverConfig = new ServerConfig(config);
    }

    private void watchNodes() {
        String zkpAddr = serverConfig.getZookeeperAddr();
        String zkpRootPath = serverConfig.getZkpRootPath();
        ZkpDiscovery discovery = new ZkpDiscovery(zkpAddr, zkpRootPath);
        listener = new ServerNodeListener();
        discovery.addListener(listener);
    }

    private void initHttp() {
        new Thread(()->{
            try {
                new MyHttpServer(serverConfig.getHttpPort()).launch(CTX);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static ServerNodeListener getListener() {
        return listener;
    }
}
