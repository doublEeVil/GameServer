package com.game.world.thread;

public class ShutdownThread implements Runnable {
    @Override
    public void run() {
        System.out.println("----world server 关闭中------");
    }
}
