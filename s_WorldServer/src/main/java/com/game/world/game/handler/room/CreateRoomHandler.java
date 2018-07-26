package com.game.world.game.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.room.CreateRoom;
import com.game.protocol.data.room.CreateRoomOk;
import com.game.world.game.service.ConnectManager;
import com.game.world.game.service.base.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@IHandler(handData = CreateRoom.class)
public class CreateRoomHandler extends IDataHandler{

    @Autowired
    private RoomService roomService;

    @Override
    public void handle(IData data) {
        int playerId = ConnectManager.getInstance().getPlayerId(data.getChannel());
        int roomId = roomService.createRoom(playerId);
        CreateRoomOk ok = new CreateRoomOk();
        ok.setRoomId(roomId);
        data.getChannel().writeAndFlush(ok);
    }
}
