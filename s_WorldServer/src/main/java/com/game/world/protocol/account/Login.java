package com.game.world.protocol.account;

import com.game.world.net.IData;
import com.game.world.protocol.Protocol;

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
