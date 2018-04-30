package com.game.world.game.service;

import com.game.world.net.IData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监控消息处理时间
 * 如果handle某个消息超过10s，说明服务器运行不正常
 */
public class HandlerMonitorService implements Runnable{
    private static Logger log = LogManager.getLogger(HandlerMonitorService.class);

    private static Map<IData, Long> timeMap = new ConcurrentHashMap<>();

    public static void addMsg(IData data) {
        timeMap.put(data, System.currentTimeMillis());
    }

    private static void delMsg(IData data) {
        timeMap.remove(data);
    }

    static {
        new Thread(new HandlerMonitorService()).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                timeMap.forEach((k, v) -> {
                    long t = System.currentTimeMillis();
                    if (!k.isHandleOver() && t - v > 10000) {
                        log.error("消息处理时间过长: " + k);
                    }
                    if (k.isHandleOver() || t - v > 15000) {
                        delMsg(k);
                    }
                });
            }
        } catch (InterruptedException e) {

        }

    }
}
