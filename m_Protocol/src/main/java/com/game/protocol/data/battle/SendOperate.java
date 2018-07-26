package com.game.protocol.data.battle;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

public class SendOperate extends IData {
    private int action;

    public SendOperate() {
        super(Protocol.Battle_SendOperate);
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
