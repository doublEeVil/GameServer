package com.game.http.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@HandleHttp(url = "/test")
@Component("testController1")
public class TestController extends BaseHttpController {
    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception{
        response.getWriter().write("this is test");
    }
}
