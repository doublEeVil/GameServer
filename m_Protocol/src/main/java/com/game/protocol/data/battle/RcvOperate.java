package com.game.protocol.data.battle;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class RcvOperate extends IData {
    private int action;

    public RcvOperate() {
        super(Protocol.Battle_RcvOperate);
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
