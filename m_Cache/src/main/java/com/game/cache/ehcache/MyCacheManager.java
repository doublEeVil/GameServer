package com.game.cache.ehcache;

import com.game.common.util.ClassUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.UserManagedCache;
import org.ehcache.config.ResourceUnit;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyCacheManager {

    //缓存数据
    private static final Map<String, UserManagedCache<Integer, HashMap>> DATA = new HashMap<>();
    //缓存的类
    private static final Set<Class<?>> KEY = new HashSet<>();

    private static boolean isBuilded = false;
    /**
     * 构建缓存
     * @param basePackage
     */
    public static void buildCache(String basePackage) {
        if (isBuilded) {
            return;
        }
        isBuilded = true;
        UserManagedCacheBuilder builder = UserManagedCacheBuilder.newUserManagedCacheBuilder(Integer.class, HashMap.class);

        // 开始构建
        Map<String, Class<?>> classMap = ClassUtils.getClassMapByAnnounce(basePackage, CacheConfig.class);
        for (Class<?> clazz : classMap.values()) {
            try {
                CacheConfig cacheConfig = clazz.getAnnotation(CacheConfig.class);
                String cacheName = cacheConfig.name();
                cacheName = cacheName.equals("") ? clazz.getName() : cacheName;

                if (DATA.containsKey(cacheName)) {
                    continue;
                }

                int heapNum = cacheConfig.heapNum();
                int offHeapSize = cacheConfig.offHeapSize();
                int expire = cacheConfig.expire();

                builder.withExpiry(ExpiryPolicy.NO_EXPIRY)
                        .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder()
                        //.heap(heapNum)
                        .offheap(offHeapSize, MemoryUnit.MB));

                UserManagedCache<Integer, HashMap> userManagedCache = builder.build();
                userManagedCache.init();
                userManagedCache.put(0, new HashMap<>(heapNum));
                DATA.put(cacheName, userManagedCache);
                KEY.add(clazz);

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("初始化 cache 发生异常：" + e.getMessage());
                System.exit(0);
            }
        }
    }

    public static Set<Class<?>> getAllCachedClass() {
        return KEY;
    }

    /**
     * 得到单个
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    public static <T> T get(Class<T> clazz, int id) {
        UserManagedCache<Integer, ?> cache = DATA.get(clazz.getName());
        if (null == cache) {
            return null;
        }
        Map<Integer, T> map = (Map<Integer, T>) cache.get(0);
        return map.get(id);
    }

    /**
     * 得到全部
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, T>  getAll(Class<T> clazz) {
        UserManagedCache<Integer, ?> cache = DATA.get(clazz.getName());
        if (null == cache) {
            return null;
        }
        Map<Integer, T> map = (Map<Integer, T>) cache.get(0);
        return map;
    }

    /**
     * 添加单个
     * @param obj
     * @param id
     * @param <T>
     * @return
     * @throws CacheException
     */
    public static <T> T add(Object obj, int id) throws CacheException{
        UserManagedCache<Integer, HashMap> cache = DATA.get(obj.getClass().getName());
        if (null == cache) {
            throw new CacheException("未注解的缓存，不能添加 ... " + obj.getClass());
        }
        HashMap<Integer, T> map = (HashMap<Integer, T>) cache.get(0);
        map.put(id, (T)obj);
        return map.get(id);
    }

}
