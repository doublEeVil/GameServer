package com.game.wuziqi.client.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.battle.Ready;
import com.game.protocol.data.room.CreateRoomOk;
import com.game.protocol.data.room.JoinRoomOk;
import com.game.wuziqi.client.service.ServiceManager;

@IHandler(handData = JoinRoomOk.class)
public class JoinRoomHandler extends IDataHandler {
    @Override
    public void handle(IData data) {
        JoinRoomOk ok = (JoinRoomOk)data;
        if (ok.getRoomId() > 0) {
            System.out.println("===加入房间" + ok.getRoomId() + " 成功");

            // 准备游戏
            ServiceManager.getInstance().getClient().sendData(new Ready());
        } else {
            System.out.println("===加入房间失败");
        }
    }
}
