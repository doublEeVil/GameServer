package com.game.world.bean;

import com.game.cache.mysql.BaseEntity;
import com.game.world.net.ConnectManager;
import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WorldPlayer extends BaseEntity {
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void sendData(IData data) {
        ConnectManager.getInstance().sendData(playerId, data);
    }
}
