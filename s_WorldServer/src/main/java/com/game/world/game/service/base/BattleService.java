package com.game.world.game.service.base;

public interface BattleService {

    /**
     *
     * @param playerId
     */
    void gameReady(int playerId);

    /**
     *
     * @param playerId
     * @param x
     * @param y
     */
    void place(int playerId, int x, int y);
}
