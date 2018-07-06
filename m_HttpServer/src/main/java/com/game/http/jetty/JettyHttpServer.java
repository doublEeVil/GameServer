package com.game.http.jetty;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServlet;
import java.util.Map;

public class JettyHttpServer {
    private final int port;

    public JettyHttpServer(int port) {
        this.port = port;
    }

    public void launch(ApplicationContext context) throws Exception{
        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.addConnector(connector);
        Context root = new Context(server, "/", 1);

        Map<String, Object> map = context.getBeansWithAnnotation(HandleHttp.class);
        for (Object obj : map.values()) {
            HandleHttp handler = obj.getClass().getAnnotation(HandleHttp.class);
            String url = handler.url();
            root.addServlet(new ServletHolder((HttpServlet)obj), url);
        }

        server.start();
        System.out.println("---- jetty http server started port: " + port);
    }

}
