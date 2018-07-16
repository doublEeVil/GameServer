package com.game.pay.test;

import com.game.common.util.HttpClientUtils;
import org.junit.Test;

import java.io.IOException;

public class NotifyTest {

    @Test
    public void NotifyTest() throws IOException{
        String url = "http://127.0.0.1:8084/test_notify?token=e0d37d74-08d8-4b1a-9f9e-552e41782bb8";
        HttpClientUtils.GetData(url);
    }
}
