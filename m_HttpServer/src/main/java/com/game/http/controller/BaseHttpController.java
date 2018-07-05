package com.game.http.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseHttpController {
    private static Logger log = LogManager.getLogger("controller");

    public abstract void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception;

    public void doService(MockHttpServletRequest request, MockHttpServletResponse response) {
        try {
            service(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //log.error(e.ge);
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
