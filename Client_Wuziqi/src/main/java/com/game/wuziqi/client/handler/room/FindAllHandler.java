package com.game.wuziqi.client.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.room.CreateRoom;
import com.game.protocol.data.room.FindAllRoomOk;
import com.game.protocol.data.room.JoinRoom;
import com.game.wuziqi.client.service.ServiceManager;

@IHandler(handData = FindAllRoomOk.class)
public class FindAllHandler extends IDataHandler {
    @Override
    public void handle(IData data) {
        FindAllRoomOk ok = (FindAllRoomOk) data;
        int[] roomId = ok.getRoomId();
        int[] roomStatus = ok.getRoomStatus();

        boolean canJoin = false;
        for (int i = 0, len = roomStatus.length; i < len; i++) {
            if (roomStatus[i] == 1) {
                System.out.println("===房间" + roomId[i] + " 可加入");

                // 加入一个房间
                JoinRoom joinRoom = new JoinRoom();
                joinRoom.setRoomId(roomId[i]);
                ServiceManager.getInstance().getClient().sendData(joinRoom);

                return;
            }
        }

        if (!canJoin) {
            System.out.println("===没有房间可以加入， 新建一个房间");
            CreateRoom createRoom = new CreateRoom();
            ServiceManager.getInstance().getClient().sendData(createRoom);
        }
    }
}
