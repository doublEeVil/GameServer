package com.game.world.procol;

import com.game.world.net.IDataHandler;

import java.util.HashMap;
import java.util.Map;

public class ProcolFactory {
    private static Map<Integer, IDataHandler> handlerMap = new HashMap<>();

    public static void register(Integer id, IDataHandler handler) {
        handlerMap.put(id, handler);
    }

    public static IDataHandler getDataHandler(int id) {
        return handlerMap.get(id);
    }
}
