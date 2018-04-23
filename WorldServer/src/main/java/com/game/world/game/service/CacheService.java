package com.game.world.game.service;

import com.game.world.bean.BaseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheService {
    private static Map<String, Map<Integer, BaseEntity>> data = new ConcurrentHashMap<>();

    public static <T extends BaseEntity> T addObj(T object) {
        if (!data.containsKey(object.getClass().getSimpleName())) {
            data.putIfAbsent(object.getClass().getSimpleName(), new ConcurrentHashMap<>());
        }
        data.get(object.getClass().getSimpleName()).put(object.getId(), object);
        return object;
    }

    public static <T extends BaseEntity> T getObj(Class<T> clazz, int id) {
        Map<Integer, BaseEntity> map = data.get(clazz.getSimpleName());
        if (map != null) {
            return (T)map.get(id);
        }
        return null;
    }

}
