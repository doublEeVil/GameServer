package com.game.world.game.service;

import com.game.cache.redis.RedisService;
import com.game.cache.redis.impl.SingleNodeRedisServiceImpl;
import com.game.world.WorldServer;
import com.game.world.game.service.base.PlayerService;
import com.game.world.game.service.base.impl.PlayerServiceImpl;

public class ServiceManager {
    private static final ServiceManager instance = new ServiceManager();

    private ServiceManager () {

    }

    public static ServiceManager getInstance() {
        return instance;
    }

    public RedisService getRedisService() {
        return WorldServer.getContext().getBean(SingleNodeRedisServiceImpl.class);
    }

    public PlayerService getPlayerService() {
        return WorldServer.getContext().getBean(PlayerServiceImpl.class);
    }

}
