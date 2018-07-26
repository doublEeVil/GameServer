package com.game.protocol.data.battle;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

/**
 * 都准备了自动开始
 */
public class Ready extends IData {
    public Ready() {
        super(Protocol.Battle_Ready);
    }
}
