package com.game.world.bean;

import com.game.cache.mysql.BaseEntity;
import com.game.net.handler.IData;
import com.game.world.game.service.ConnectManager;

public class WorldPlayer extends BaseEntity {
    private int playerId;
    private int roomId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void sendData(IData data) {
        ConnectManager.getInstance().sendData(playerId, data);
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
