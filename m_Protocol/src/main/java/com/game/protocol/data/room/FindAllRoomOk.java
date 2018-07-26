package com.game.protocol.data.room;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class FindAllRoomOk extends IData {
    private int[] roomId;
    private int[] roomStatus; //0可加入 1满人
    public FindAllRoomOk() {
        super(Protocol.Room_FindAllOk);
    }

    public int[] getRoomId() {
        return roomId;
    }

    public void setRoomId(int[] roomId) {
        this.roomId = roomId;
    }

    public int[] getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int[] roomStatus) {
        this.roomStatus = roomStatus;
    }
}
