package com.game.protocol.data.account;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class Login extends IData {
    private int playerId;

    public Login() {
        super(Protocol.Acount_Login);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
