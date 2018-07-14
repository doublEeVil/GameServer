package com.game.common.util.test;

public class Ta implements Cloneable{
    private int age;
    private boolean boy;
    private String name;
    private String[] addrs;

    public Ta() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getBoy() {
        return boy;
    }

    public void setBoy(boolean boy) {
        this.boy = boy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAddrs() {
        return addrs;
    }

    public void setAddrs(String[] addrs) {
        this.addrs = addrs;
    }

    @Override
    public Ta clone() throws CloneNotSupportedException {
        return (Ta) super.clone();
    }
}