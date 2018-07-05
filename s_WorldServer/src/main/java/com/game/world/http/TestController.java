package com.game.world.http;

import com.game.cache.mysql.BaseEntity;
import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.world.AppConfig;
import com.game.world.bean.TestEntityA;
import com.game.world.game.service.MessageService;
import com.game.world.net.IHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@HandleHttp(url = "/abc")
@Component
public class TestController extends BaseHttpController{
    private static Logger log = LogManager.getLogger("controller");

    @Autowired
    ApplicationContext context;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        response.getWriter().write(" thi si  sabc ");
        System.out.println(context);
        TestEntityA a = new TestEntityA();
        System.out.println(a instanceof BaseEntity);
        log.info("==== etst === test == ");
        int i = 9/0;
        System.out.println(i);
    }
}


