package com.game.cache.ehcache.service.impl;

import com.game.cache.ehcache.CacheException;
import com.game.cache.ehcache.MyCacheManager;
import com.game.cache.ehcache.service.CacheService;
import com.game.cache.mysql.BaseConfigEntity;
import com.game.cache.mysql.BaseEntity;
import com.game.cache.mysql.service.GenericMySqlService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存相关操作
 * @author
 */
@Component("com.game.cache.ehcache.service.impl.CacheServiceImpl")
public class CacheServiceImpl implements CacheService {
    private static Logger log = LogManager.getLogger(CacheServiceImpl.class);
    @Autowired
    private GenericMySqlService genericMysqlService;

    public void buildCache(String basePackage) {
        MyCacheManager.buildCache(basePackage);
    }

    /**
     * 加载全部表配置数据
     */
    public void loadAllConfigEntity() {
        Set<Class<?>> allClass = MyCacheManager.getAllCachedClass();
        for (Class<?> clazz : allClass) {
            if (BaseConfigEntity.class.isAssignableFrom(clazz)) {
                List<BaseConfigEntity> list = (List<BaseConfigEntity>) genericMysqlService.getAll(clazz);
                try {
                    for (BaseConfigEntity entity : list) {
                        MyCacheManager.add(entity, entity.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
     }

    /**
     * 查单个，如果是表数据，没有则从数据库查
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> clazz, int id) {
        T t = MyCacheManager.get(clazz, id);
        if (null == t && BaseEntity.class.isAssignableFrom(clazz)) {
            t = genericMysqlService.get(clazz, id);
            if (t != null) {
                try {
                    t = MyCacheManager.add(t, ((BaseEntity) t).getId());
                    return t;
                } catch (CacheException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    /**
     * 查全部
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Map<Integer, T> getAll(Class<T> clazz) {
        return MyCacheManager.getAll(clazz);
    }

    public void update(BaseEntity entity) {
        entity.addUpdateFlag();
    }

    public <T extends BaseEntity> T saveToDB(T entity) {
        genericMysqlService.save(entity);
        try {
            return MyCacheManager.add(entity, entity.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateToDB(BaseEntity entity) {
        entity.setUpdateFlag(0);
        genericMysqlService.update(entity);
    }
}
