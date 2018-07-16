package com.game.pay.service;

import com.game.pay.entity.Payment;

public interface NotifyService {

    /**
     *
     * @param payment
     */
    void addPayment(Payment payment);
}
