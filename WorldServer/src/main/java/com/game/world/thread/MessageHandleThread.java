package com.game.world.thread;

import com.game.world.game.service.HandlerMonitorService;
import com.game.world.net.IData;
import com.game.world.procol.ProtocolFactory;

public class MessageHandleThread implements Runnable {
    private IData data;

    public MessageHandleThread(IData data) {
        this.data = data;
    }

    @Override
    public void run() {
        HandlerMonitorService.addMsg(data);
        ProtocolFactory.getDataHandler(data.getMsg()).handle(null, data);
        data.setHandleOver(true);
    }
}
