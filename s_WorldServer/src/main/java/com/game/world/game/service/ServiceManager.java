package com.game.world.game.service;

import com.game.cache.redis.IRedisService;
import com.game.cache.redis.impl.SingleNodeRedisService;
import com.game.world.WorldServer;

public class ServiceManager {
    private static final ServiceManager instance = new ServiceManager();

    public static ServiceManager getInstance() {
        return instance;
    }

    public IRedisService getRedisService() {
        return WorldServer.getContext().getBean(SingleNodeRedisService.class);
    }

}
