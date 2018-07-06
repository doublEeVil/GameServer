package com.game.cache.ehcache;

import com.game.common.util.ClassUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import java.util.HashMap;
import java.util.Map;

public class MyCacheManager {

    private static Map<String, Cache<Long, ?>> DATA = new HashMap<>();
    private static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();

    public static void initCache(String basePackage) {
        cacheManager.init();

        // 开始构建
        Map<String, Class<?>> classMap = ClassUtils.getClassMapByAnnounce(basePackage, CacheConfig.class);
        for (Class<?> clazz : classMap.values()) {
            try {
                CacheConfig cacheConfig = clazz.getAnnotation(CacheConfig.class);
                String cacheName = cacheConfig.name();
                cacheName = cacheName.equals("") ? clazz.getName() : cacheName;
                int heapNum = cacheConfig.heapNum();
                int offHeapSize = cacheConfig.offHeapSize();
                // int expire = cacheConfig.expire();

                Cache<Long, ?>  cache = cacheManager.createCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class, clazz, ResourcePoolsBuilder
                                        .heap(heapNum)
                                        .offheap(offHeapSize, MemoryUnit.MB)

                        ).withExpiry(ExpiryPolicy.NO_EXPIRY));
                DATA.put(clazz.getName(), cache);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("初始化 cache 发生异常：" + e.getMessage());
            }
        }
    }

    public static <T> Cache<Long, T> getCache(Class<T> clazz) {
        return (Cache<Long, T>) DATA.get(clazz.getName());
    }

    public static <T> T getCacheEntity(Class<T> clazz, long id) {
        return getCache(clazz).get(id);
    }
}
