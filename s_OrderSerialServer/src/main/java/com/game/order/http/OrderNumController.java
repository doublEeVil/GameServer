package com.game.order.http;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.order.service.SerialNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@Component
@HandleHttp(url = "/orderNum")
public class OrderNumController extends BaseHttpController {

    @Autowired
    private SerialNumService serialNumService;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        String playerChannel = request.getParameter("playerChannel");
        String playerId = request.getParameter("playerId");
        String callbackUrl = request.getParameter("callbackUrl");
        String shouldPay = request.getParameter("shouldPay");

        String token = serialNumService.generateOrderNum(playerChannel, Integer.valueOf(playerId), callbackUrl, Float.valueOf(shouldPay));
        response.getWriter().write(token);
    }
}
