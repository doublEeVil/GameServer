package com.game.cache.mysql.service;

import com.game.cache.mysql.BaseEntity;

public interface IGenericMySqlService {
    /**
     * 保存
     * @param t
     */
    void save(BaseEntity t);

    /**
     * 查找
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T get(Class<T> clazz, Long id);
}
