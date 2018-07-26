package com.game.protocol.data.room;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class JoinRoomOk extends IData {
    private int roomId; //大于0 正常加入

    public JoinRoomOk() {
        super(Protocol.Room_JoinRoomOk);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
