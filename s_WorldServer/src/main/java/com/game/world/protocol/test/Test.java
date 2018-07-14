package com.game.world.protocol.test;

import com.game.world.net.IData;
import com.game.world.protocol.Protocol;

public class Test extends IData {
    private String name;
    private int age;

    public Test() {
        super(Protocol.Test_Test);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
