package com.game.world.procol.account;

import com.game.world.net.IData;

public class Login extends IData {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
