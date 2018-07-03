package com.game.world.net;

import com.google.protobuf.MessageLite;

public class Packet {
    private short headId;  //头
    private MessageLite bodyMsg; // 实体信息

    public Packet(short headId, MessageLite bodyMsg) {
        this.headId = headId;
        this.bodyMsg = bodyMsg;
    }

    public short getHeadId() {
        return headId;
    }

    public void setHeadId(short headId) {
        this.headId = headId;
    }

    public MessageLite getBodyMsg() {
        return bodyMsg;
    }

    public void setBodyMsg(MessageLite bodyMsg) {
        this.bodyMsg = bodyMsg;
    }
}
