package com.game.cache.ehcache;

import com.game.cache.ehcache.entity.ObjA;
import com.game.cache.ehcache.entity.ObjB;
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

    public static void initCache() {
        cacheManager.init();

        // 开始构建
        Map<String, Class<?>> classMap = ClassUtils.getClassMapByAnnounce("com.game.cache", CacheConfig.class);
        for (Class<?> clazz : classMap.values()) {
            try {
                CacheConfig cache = clazz.getAnnotation(CacheConfig.class);
                String cacheName = cache.name();
                cacheName = cacheName.equals("") ? clazz.getName() : cacheName;
                int heapNum = cache.heapNum();
                int offHeapSize = cache.offHeapSize();
                int expire = cache.expire();

                //
                Cache<Long, ?>  rcache = cacheManager.createCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class, clazz, ResourcePoolsBuilder
                                        .heap(heapNum)
                                        .offheap(offHeapSize, MemoryUnit.MB)

                        ).withExpiry(ExpiryPolicy.NO_EXPIRY));
                DATA.put(clazz.getName(), rcache);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("发生异常：" + e.getMessage());
            }
        }

        // 测试
        for (Cache cache : DATA.values()) {
            System.out.println("----" + cache);
        }

        Cache cache1 = DATA.get(ObjA.class.getName());
        Cache cache2 = DATA.get(ObjB.class.getName());
        for (int i = 0; i <= 300000; i++) {
            ObjA a = new ObjA();
            a.setId(i);
            a.setParam("" + i+"浔阳江头夜送客，枫叶荻花秋瑟瑟，主人在马克在床");
            ObjB b = new ObjB();
            b.setId(i * 1000);
            b.setParam("++");
            cache1.put(a.getId(), a);
            cache2.put(b.getId(), b);
        }
        //

        System.out.println(cache1.get((long)1));
        System.out.println(cache2.get((long)2));

    }
}
