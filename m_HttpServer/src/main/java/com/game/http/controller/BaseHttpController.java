package com.game.http.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseHttpController {
    public abstract void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception;

    public void doService(MockHttpServletRequest request, MockHttpServletResponse response) {
        try {
            service(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
