package com.game.order.service;

public interface SerialNumService {

    /**
     * 申请订单
     * @param playerChannel
     * @param playerId
     * @param callbackUrl
     * @param shouldPay
     * @return
     */
    String generateOrderNum(String playerChannel, int playerId, String callbackUrl, float shouldPay);
}
