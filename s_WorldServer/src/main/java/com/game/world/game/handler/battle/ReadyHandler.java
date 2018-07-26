package com.game.world.game.handler.battle;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.battle.Ready;
import com.game.world.game.service.ConnectManager;
import com.game.world.game.service.base.BattleService;
import org.springframework.beans.factory.annotation.Autowired;

@IHandler(handData = Ready.class)
public class ReadyHandler extends IDataHandler {
    @Autowired
    private BattleService battleService;

    @Override
    public void handle(IData data) {
        int playerId = ConnectManager.getInstance().getPlayerId(data.getChannel());
        battleService.gameReady(playerId);
    }
}
