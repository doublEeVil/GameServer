package com.game.rpc.api;

public interface HelloService {
    String hello(String name);

    String hello(Person person);
}
