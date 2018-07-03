package com.game.world.game.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testServer")
public class TestServer {
    @Autowired
    private AbcService abcService;


    public void say() {
        System.out.println("====say===");
        System.out.println(abcService);
        abcService.say();
    }
}
