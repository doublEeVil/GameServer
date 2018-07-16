package com.game.pay;

import com.game.http.netty.MyHttpServer;
import com.game.pay.config.ServerConfig;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PayNotifyServer {
    private static ApplicationContext CTX;
    private ServerConfig serverConfig;

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        CTX = ctx;

        new PayNotifyServer().launch();
    }

    private void launch() throws Exception {
        System.out.println("准备启动支付回调服...");
        initConfig();
        initToRetryFailPayment();
        initHttp();
        System.out.println("支付回调服启动成功...");
    }

    private void initConfig() throws Exception{
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.setEncoding("utf-8");
        config.load("config.properties");
        serverConfig = new ServerConfig(config);
    }

    private void initToRetryFailPayment() {

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
