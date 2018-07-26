package com.game.protocol.data.battle;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class RcvPlace extends IData{
    private int playerId;
    private int x;
    private int y;

    public RcvPlace() {
        super(Protocol.Battle_RcvPlace);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
