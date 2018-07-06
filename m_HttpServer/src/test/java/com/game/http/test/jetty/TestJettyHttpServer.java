package com.game.http.test.jetty;

import com.game.http.controller.HandleHttp;
import com.game.http.jetty.JettyHttpServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:http-jetty.xml"})
public class TestJettyHttpServer {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testJettyHttpServer() throws Exception{
        new JettyHttpServer(1234).launch(context);
        Thread.sleep(10000);
        System.out.println("--- test jetty http server pass");
    }
}

@HandleHttp(url = "/test")
@Component
class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--- rcv request---");
        resp.getWriter().write(" test pass");
    }
}
