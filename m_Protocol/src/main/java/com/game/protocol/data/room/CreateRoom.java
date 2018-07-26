package com.game.protocol.data.room;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class CreateRoom extends IData {
    public CreateRoom() {
        super(Protocol.Room_CreateRoom);
    }
}
