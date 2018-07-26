package com.game.world.game.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.room.JoinRoom;
import com.game.protocol.data.room.JoinRoomOk;
import com.game.world.game.service.ConnectManager;
import com.game.world.game.service.base.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

@IHandler(handData = JoinRoom.class)
public class JoinRoomHandler extends IDataHandler {

    @Autowired
    private RoomService roomService;

    @Override
    public void handle(IData data) {
        System.out.println("===join room");
        JoinRoom room =  (JoinRoom) data;
        int playerId = ConnectManager.getInstance().getPlayerId(data.getChannel());
        int roomId = roomService.joinRoom(playerId, room.getRoomId());
        JoinRoomOk ok = new JoinRoomOk();
        ok.setRoomId(roomId);
        data.getChannel().writeAndFlush(ok);
    }
}
