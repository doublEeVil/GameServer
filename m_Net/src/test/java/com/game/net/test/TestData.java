package com.game.net.test;

import com.game.net.handler.IData;

public class TestData extends IData {

    private String name;

    public TestData() {
        super((short)-10001);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
