package com.game.wuziqi.client.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.battle.RcvPlace;
import com.game.wuziqi.client.QiPan;
import com.game.wuziqi.client.service.ServiceManager;

@IHandler(handData = RcvPlace.class)
public class RcvPlaceHandler extends IDataHandler {
    @Override
    public void handle(IData data) {
        RcvPlace rcvPlace = (RcvPlace) data;
        int playerId = ServiceManager.getInstance().getPlayerId();
        int flag = rcvPlace.getPlayerId() == playerId ? QiPan.BLACK : QiPan.WHITE;
        ServiceManager.getInstance().getQiPan().setPlace(rcvPlace.getX(), rcvPlace.getY(), flag);
        ServiceManager.getInstance().getQiPan().print();
    }
}
