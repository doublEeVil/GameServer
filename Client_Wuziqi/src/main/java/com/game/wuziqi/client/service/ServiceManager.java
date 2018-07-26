package com.game.wuziqi.client.service;

import com.game.net.client.NetWebSocketClient;
import com.game.wuziqi.client.QiPan;

public class ServiceManager {
    private NetWebSocketClient client;
    private QiPan qiPan;
    private int playerId;
    private boolean canGame = false;

    private static final ServiceManager instance = new ServiceManager();

    private ServiceManager() {

    }

    public static ServiceManager getInstance() {
        return instance;
    }

    public void setClient(NetWebSocketClient client) {
        this.client = client;
    }

    public NetWebSocketClient getClient() {
        return client;
    }

    public QiPan getQiPan() {
        if (qiPan == null) {
            qiPan = new QiPan();
        }
        return qiPan;
    }

    public void setQiPan(QiPan qiPan) {
        this.qiPan = qiPan;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean canGame() {
        return canGame;
    }

    public void setCanGame(boolean canGame) {
        this.canGame = canGame;
    }
}
