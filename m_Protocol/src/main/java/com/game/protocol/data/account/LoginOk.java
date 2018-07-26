package com.game.protocol.data.account;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class LoginOk extends IData {
    private int status; //小于0等于失败

    public LoginOk() {
        super(Protocol.Acount_Login);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
