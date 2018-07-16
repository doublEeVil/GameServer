package com.game.order.service.impl;

import com.game.cache.mysql.service.GenericMySqlService;
import com.game.order.entity.PaymentTemp;
import com.game.order.service.SerialNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("com.game.order.service.impl.SerialNumServiceImpl")
public class SerialNumServiceImpl implements SerialNumService {

    @Autowired
    private GenericMySqlService genericMySqlService;

    public String generateOrderNum(String playerChannel, int playerId, String callbackUrl, float shouldPay) {
        PaymentTemp temp = new PaymentTemp();
        temp.setPlayerChannel(playerChannel);
        temp.setPlayerId(playerId);
        temp.setCallbackUrl(callbackUrl);
        temp.setShouldPayAmt(shouldPay);
        String token = UUID.randomUUID().toString();
        temp.setOrderToken(token);

        //以下是默认信息
        temp.setPayChannel("");
        temp.setPayAgent("");
        temp.setExecutionCount(0);
        temp.setRealAmt(0);
        temp.setRemark("");
        temp.setStatusCallback(0);
        temp.setStatusNotify(0);
        temp.setReturnCode(0);
        temp.setReturnMessage("");
        //
        temp.setCreateDate(System.currentTimeMillis());
        temp.setUpdateDate(System.currentTimeMillis());
        genericMySqlService.save(temp);
        return token;
    }
}
