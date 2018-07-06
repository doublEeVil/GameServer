package com.game.world.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkThreadPool {
    private static ExecutorService POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 20);

    public static void initWorkThreadPool() {

    }

    public static void execute(Runnable run) {
        POOL.submit(run);
    }
}
