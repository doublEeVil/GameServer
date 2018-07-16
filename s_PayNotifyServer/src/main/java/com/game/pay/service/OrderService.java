package com.game.pay.service;

import com.game.pay.entity.Payment;
import com.game.pay.entity.PaymentTemp;

public interface OrderService {
    /**
     *
     * @param token
     * @return
     */
    PaymentTemp getPaymentTempByToken(String token);


    void savePayment(PaymentTemp temp, String payChannel, String payAgent, float realAmt, String remark) throws Exception;
}
