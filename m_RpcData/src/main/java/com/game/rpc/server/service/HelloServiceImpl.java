package com.game.rpc.server.service;

import com.game.rpc.annotation.RpcService;
import com.game.rpc.api.HelloService;
import com.game.rpc.api.Person;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello name is " + name;
    }

    @Override
    public String hello(Person person) {
        return "hello name is " + person.getFirstName() + " . " + person.getLastName();
    }
}
