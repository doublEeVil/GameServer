package com.game.wuziqi.client.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.room.CreateRoomOk;

@IHandler(handData = CreateRoomOk.class)
public class CreateRoomHandler extends IDataHandler{
    @Override
    public void handle(IData data) {
        CreateRoomOk ok = (CreateRoomOk)data;
        if (ok.getRoomId() > 0) {
            System.out.println("===创建房间" + ok.getRoomId() + " 成功");
        } else {
            System.out.println("===创建房间失败");
        }
    }
}
