package com.game.world.http;

import com.game.cache.mysql.BaseEntity;
import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@HandleHttp(url = "/abc")
@Component
public class TestController extends BaseHttpController{
    private static Logger log = LogManager.getLogger("controller");

    @Autowired
    ApplicationContext context;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        response.getWriter().write(" thi si  sabc ");
        System.out.println("ctx is " + context);
    }
}


