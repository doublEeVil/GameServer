package com.game.world.game.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.room.FindAllRoom;
import com.game.protocol.data.room.FindAllRoomOk;
import com.game.world.bean.Room;
import com.game.world.game.service.base.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@IHandler(handData = FindAllRoom.class)
public class FindAllHandler extends IDataHandler {
    @Autowired
    RoomService roomService;

    @Override
    public void handle(IData data) {
        System.out.println("----find all room");
        Collection<Room> cols = roomService.getAllRoom();
        int len = cols.size();
        int[] roomId = new int[len];
        int[] roomStatus = new int[len];

        int i = 0;
        for (Room room : cols) {
            roomId[i] = room.getRoomId();
            roomStatus[i] = room.getStatus();
        }

        FindAllRoomOk ok = new FindAllRoomOk();
        ok.setRoomId(roomId);
        ok.setRoomStatus(roomStatus);
        data.getChannel().writeAndFlush(ok);
    }
}
