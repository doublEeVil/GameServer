package com.game.cache.ehcache.service;

import com.game.cache.mysql.BaseEntity;

import java.util.Map;

public interface CacheService {

    void buildCache(String basePackage);

    void loadAllConfigEntity();

    <T> T get(Class<T> clazz, int id);

    <T> Map<Integer, T> getAll(Class<T> clazz);

    void update(BaseEntity entity);

    <T extends BaseEntity> T saveToDB(T entity);

    void updateToDB(BaseEntity entity);
}
