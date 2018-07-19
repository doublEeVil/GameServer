package com.game.world.http;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@HandleHttp(url = "/abc")
@Component
public class TestController extends BaseHttpController{
    private static Logger log = LogManager.getLogger("mongologger");

    @Autowired
    ApplicationContext context;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        System.out.println("-----/abc");
        response.getWriter().write("success");
        System.out.println("ctx is " + context);
        log.info("test" + System.currentTimeMillis());
        log.debug("hafhskhfhasidfhjiki");
        JSONObject json = new JSONObject();
        json.put("name", "zjs");
        json.put("age", 12);

        log.info(new MarkerManager.Log4jMarker("abcController"), json.toString()); //定义一个marker
    }
}

