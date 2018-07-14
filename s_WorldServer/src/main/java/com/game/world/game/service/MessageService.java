package com.game.world.game.service;

import com.game.world.net.IData;
import com.game.world.thread.MessageHandleThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class MessageService {
    private static Logger log = LogManager.getLogger(MessageService.class);

    private static ExecutorService workPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 15);

    public static void addMessage(IData data) {
        if (null == data) {
            return;
        }
        workPool.execute(new MessageHandleThread(data));
    }

    static {
        new Thread(()-> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    System.out.println("正在运行的线程：" + ((ThreadPoolExecutor)workPool).getActiveCount());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
