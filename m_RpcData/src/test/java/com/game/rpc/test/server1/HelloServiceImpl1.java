package com.game.rpc.test.server1;

import com.game.rpc.annotation.RpcService;
import com.game.rpc.api.HelloService;
import com.game.rpc.api.Person;

@RpcService(value = HelloService.class)
public class HelloServiceImpl1 implements HelloService {
    @Override
    public String hello(String name) {
        System.out.println("server 1111");
        return "this is hello 1 " + name;
    }

    @Override
    public String hello(Person person) {
        System.out.println("server 1111");
        return "this is hello 1" + person.getFirstName() + " " + person.getLastName();
    }
}
