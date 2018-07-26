package com.game.protocol.data.room;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class CreateRoomOk extends IData {
    private int roomId;

    public CreateRoomOk() {
        super(Protocol.Room_CreateRoomOk);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
