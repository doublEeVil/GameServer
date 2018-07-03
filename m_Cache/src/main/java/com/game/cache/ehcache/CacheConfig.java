package com.game.cache.ehcache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheConfig {
    String name() default ""; // 缓存名
    int heapNum() default 2000; //在对空间的最大数量
    int offHeapSize() default 10; //对外空间最大容量 单位MB
    int expire() default Integer.MAX_VALUE; //过期时间
}
