package com.game.world.http;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.world.AppConfig;
import com.game.world.net.IHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@HandleHttp(url = "/abc")
public class TestController extends BaseHttpController{
    @Autowired
    ApplicationContext context;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        response.getWriter().write(" thi si  sabc ");
        System.out.println(context);
    }
}


