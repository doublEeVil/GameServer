package com.game.world.game.service.base.impl;

import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.base.PlayerService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("com.game.world.game.service.base.impl.PlayerServiceImpl")
public class PlayerServiceImpl implements PlayerService {
    private Map<Integer, WorldPlayer> playerMap = new ConcurrentHashMap<>();

    @Override
    public WorldPlayer getLoadPlayer(int playerId) {
        return playerMap.get(playerId);
    }

    @Override
    public WorldPlayer onPlayerLogin(int playerId) {
        WorldPlayer player = getLoadPlayer(playerId);
        if (null == player) {
            player = new WorldPlayer();
            player.setPlayerId(playerId);
            playerMap.put(playerId, player);
        }
        return player;
    }

    @Override
    public void onPlayerLogout(int playerId) {
        System.out.println("===玩家" + playerId + " 离线");
        //playerMap.remove(playerId);
    }
}
