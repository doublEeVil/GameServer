package com.game.world.game.service;

public class ServiceManager {
    private static ServiceManager instance = new ServiceManager();

    public static ServiceManager getInstance() {
        if (null == instance) {
            instance = new ServiceManager();
        }
        return instance;
    }
}
