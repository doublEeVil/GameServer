package com.game.world.bean;

import com.game.cache.mysql.BaseEntity;
import com.game.net.handler.IData;

public class WorldPlayer extends BaseEntity {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void sendData(IData data) {
        //ConnectManager.getInstance().sendData(playerId, data);
    }
}
