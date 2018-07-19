package com.game.protocol.data.test;

import com.game.net.handler.IData;
import com.game.protocol.Protocol;

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
