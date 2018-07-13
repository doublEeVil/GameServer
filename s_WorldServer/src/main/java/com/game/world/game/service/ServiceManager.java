package com.game.world.game.service;

import com.game.cache.redis.RedisService;
import com.game.cache.redis.impl.SingleNodeRedisServiceImpl;
import com.game.world.WorldServer;

public class ServiceManager {
    private static final ServiceManager instance = new ServiceManager();

    public static ServiceManager getInstance() {
        return instance;
    }

    public RedisService getRedisService() {
        return WorldServer.getContext().getBean(SingleNodeRedisServiceImpl.class);
    }

}
