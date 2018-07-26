package com.game.protocol.data.error;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class ErrorMsg extends IData {
    private String msg;

    public ErrorMsg() {
        super(Protocol.Error_ErrorMsg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
