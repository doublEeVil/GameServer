package com.game.net.test.server;

import com.game.common.util.PrintUtils;
import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.net.test.TestData;

@IHandler(handData = TestData.class)
public class TestDataHandler extends IDataHandler{
    @Override
    public void handle(IData data) {
        System.out.println("-=====--rvc----");
        TestData rcv = (TestData) data;
        PrintUtils.printVar(rcv);
        data.getChannel().writeAndFlush(data);
    }
}
