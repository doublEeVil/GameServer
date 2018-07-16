package com.game.pay.http;

import com.game.common.util.PrintUtils;
import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.pay.entity.Payment;
import com.game.pay.entity.PaymentTemp;
import com.game.pay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@Component
@HandleHttp(url = "/test_notify")
public class TestNotifyController extends BaseHttpController {

    @Autowired
    private OrderService orderService;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        String token = request.getParameter("token");
        PaymentTemp temp = orderService.getPaymentTempByToken(token);

        if (null == temp) {
            return;
        }

        orderService.savePayment(temp, "pay_1234", "ali", 12.3f, "123457898");
    }
}
