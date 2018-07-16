package com.game.order.test;

import com.game.common.util.HttpClientUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestOrder {

    @Test
    public void TestOrder() throws Exception {
        String url = "http://127.0.0.1:8082/orderNum";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new NameValuePair("playerChannel", "1060"));
        data.add(new NameValuePair("playerId", "123456"));
        data.add(new NameValuePair("callbackUrl", "http://127.0.0.1:8080/test"));
        data.add(new NameValuePair("shouldPay", "2.56"));
        String ret = HttpClientUtils.PostData(url, data);
        System.out.println("ret: " + ret);
    }
}
