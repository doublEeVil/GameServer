package com.game.net.thread;

import com.game.net.ProtocolFactory;
import com.game.net.handler.IData;
import com.game.net.service.HandlerMonitorService;

public class MessageHandleThread implements Runnable {
    private IData data;

    public MessageHandleThread(IData data) {
        this.data = data;
    }

    @Override
    public void run() {
        HandlerMonitorService.addMsg(data);
        ProtocolFactory.getDataHandler(data).handle(data);
        data.setHandleOver(true);
    }
}
