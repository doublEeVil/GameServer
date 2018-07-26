package com.game.world.game.service.base;

import com.game.world.bean.WorldPlayer;

public interface PlayerService {

    WorldPlayer getLoadPlayer(int playerId);

    WorldPlayer onPlayerLogin(int playerId);

    void onPlayerLogout(int playerId);
}
