package com.game.protocol.data.room;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class FindAllRoom extends IData {
    public FindAllRoom() {
        super(Protocol.Room_FindAll);
    }
}
