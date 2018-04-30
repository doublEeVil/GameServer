package com.game.world.net;

import com.google.protobuf.MessageLite;

public class IData {
    private MessageLite msg;
    private boolean handleOver = false;

    public IData(MessageLite msg) {
        this.msg = msg;
    }

    public MessageLite getMsg() {
        return msg;
    }

    public void setMsg(MessageLite msg) {
        this.msg = msg;
    }

    public boolean isHandleOver() {
        return handleOver;
    }

    public void setHandleOver(boolean handleOver) {
        this.handleOver = handleOver;
    }
}
