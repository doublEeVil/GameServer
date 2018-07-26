package com.game.protocol.data.battle;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class SendPlace extends IData{
    private int x;
    private int y;

    public SendPlace() {
        super(Protocol.Battle_SendPlace);
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
