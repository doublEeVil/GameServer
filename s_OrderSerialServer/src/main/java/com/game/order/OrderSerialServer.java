package com.game.order;

import com.game.http.netty.MyHttpServer;
import com.game.order.config.ServerConfig;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderSerialServer {
    private static ApplicationContext CTX;
    private ServerConfig serverConfig;

    public static void main(String[] args) throws Exception{
        System.out.println("====try start server");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        CTX = ctx;

        new OrderSerialServer().launch();
    }

    private void launch() throws Exception {
        System.out.println("准备启动订单服...");
        initConfig();
        initHttp();
        System.out.println("订单服启动成功...");
    }

    private void initConfig() throws Exception{
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.setEncoding("utf-8");
        config.load("config.properties");
        serverConfig = new ServerConfig(config);
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
}
