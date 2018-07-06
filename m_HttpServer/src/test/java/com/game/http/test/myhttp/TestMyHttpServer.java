package com.game.http.test.myhttp;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.http.netty.MyHttpServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:http-myhttp.xml"})

public class TestMyHttpServer {
    @Autowired
    private ApplicationContext context;

    @Test
    public void testHttp() throws Exception{
        new Thread(() -> {
            try {
                new MyHttpServer(1234).launch(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(10000);
        System.out.println("--- test http --- pass");
    }
}

@HandleHttp(url = "/test")
@Component
class TestController extends BaseHttpController{

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        System.out.println("--- rcv ----");
        response.getWriter().write("--- test passs---");
    }
}


