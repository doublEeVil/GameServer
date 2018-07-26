package com.game.protocol.data.room;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class JoinRoom extends IData {
    private int roomId;

    public JoinRoom() {
        super(Protocol.Room_JoinRoom);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
