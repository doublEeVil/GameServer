package com.game.http.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@HandleHttp(url = "/test")
public class TestController extends BaseHttpController {
    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception{
        response.getWriter().write("this is test");
    }
}
