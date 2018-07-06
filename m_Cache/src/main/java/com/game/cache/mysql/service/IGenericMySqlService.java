package com.game.cache.mysql.service;

import com.game.cache.mysql.BaseEntity;

import java.util.List;

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

    /**
     * 查找全部
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> getAll(Class<T> clazz);

    /**
     * update
     * @param t
     */
    void update(BaseEntity t);

    /**
     * 删除
     * @param t
     */
    void delete(BaseEntity t);
}
