package com.game.world.game.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceManager {
    private static ServiceManager instance = new ServiceManager();
    private ApplicationContext context  = new ClassPathXmlApplicationContext("spring.xml");

    public static ServiceManager getInstance() {
        if (null == instance) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public ApplicationContext getContext() {
        return context;
    }
}
