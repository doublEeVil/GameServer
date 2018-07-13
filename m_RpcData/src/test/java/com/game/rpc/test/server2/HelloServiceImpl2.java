package com.game.rpc.test.server2;

import com.game.rpc.annotation.RpcService;
import com.game.rpc.api.HelloService;
import com.game.rpc.api.Person;

@RpcService(value = HelloService.class)
public class HelloServiceImpl2 implements HelloService {
    @Override
    public String hello(String name) {
        return "this is hello 2 " + name;
    }

    @Override
    public String hello(Person person) {
        return "this is hello 2" + person.getFirstName() + " " + person.getLastName();
    }
}
