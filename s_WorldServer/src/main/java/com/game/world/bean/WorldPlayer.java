package com.game.world.bean;

import com.game.cache.mysql.BaseEntity;
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

    public void sendData(Object msg) {
        IDataHandler.playerIdChannelMap.get(playerId).writeAndFlush(new TextWebSocketFrame(msg.toString()));
    }
}
