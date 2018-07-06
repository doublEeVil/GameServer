package com.game.cache.test;

import com.game.cache.ehcache.MyCacheManager;
import com.game.cache.test.entity.TestEntityA;
import com.game.common.util.PrintUtils;
import org.ehcache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:jdbc.properties"})
@ContextConfiguration(locations = {"classpath:spring-hibernate.xml"})
public class TestCache {

    @Test
    public void testBasicCache() {
        MyCacheManager.initCache("com.game.cache.test.entity");
        Cache<Long, TestEntityA> cacheA = MyCacheManager.getCache(TestEntityA.class);
        TestEntityA a = new TestEntityA();
        a.setId(10001);
        a.setAge(12);
        cacheA.put(a.getId(), a);
        TestEntityA a1 = cacheA.get(10001L);
        PrintUtils.printVar(a1);
        System.out.println(a1.getId());
    }
}
