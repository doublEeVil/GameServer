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
        try {
            System.out.println("rcv:" + data.getClass());
            ProtocolFactory.getDataHandler(data).handle(data);
        } catch (Exception e) {
            System.err.println("处理消息" + data + " 发生异常");
            e.printStackTrace();
        }

        data.setHandleOver(true);
    }
}
