package com.game.protocol.data.chat;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class RcvMsg extends IData {
    private int chatType;
    private String msg;

    public RcvMsg() {
        super(Protocol.Chat_RcvMsg);
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
