package com.game.cache.test;

import com.game.cache.ehcache.CacheException;
import com.game.cache.ehcache.service.impl.CacheServiceImpl;
import com.game.cache.test.entity.TestConfigEntityA;
import com.game.cache.test.entity.TestEntityA;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.core.EhcacheBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:jdbc.properties"})
@ContextConfiguration(locations = {"classpath:spring-hibernate.xml"})
public class TestCache {

    @Autowired
    private CacheServiceImpl cacheServiceImpl;

    @Test
    public void testBasicCache() throws CacheException{
//        MyCacheManager.buildCache("com.game.cache.test.entity");
//
//        for (int i = 0; i < 222222; i++) {
//            TestEntityA a = new TestEntityA();
//            a.setId(i);
//            a.setAge(i);
//            MyCacheManager.add(a, a.getId());
//        }
//
//        long t1 = System.currentTimeMillis();
//        TestEntityA a = new TestEntityA();
//        a.setId(123);
//        a.setAge(14500);
//        MyCacheManager.add(a, a.getId());
//
//        TestEntityA a1 = MyCacheManager.get(TestEntityA.class, 123);
//        System.out.println(a1.getAge());
//        long t2 = System.currentTimeMillis();
//
//        System.out.println("time: " + (t2 - t1));
    }


    @Test
    public void testConfigEntity() {
        cacheServiceImpl.buildCache("com.game.cache.test.entity");


        // 玩家信息
        TestEntityA a = cacheServiceImpl.get(TestEntityA.class, 123);
        if (null == a) {
            a = new TestEntityA();
            a.setId(123);
            a.setAge(145);
            a = cacheServiceImpl.saveToDB(a);
            System.out.println("a is null  age: " + a.getAge());
        }

        TestEntityA a1 = cacheServiceImpl.get(TestEntityA.class, 123);
        a1.setAge(12346788);
        cacheServiceImpl.updateToDB(a1);

        // 配置表数据
        cacheServiceImpl.loadAllConfigEntity();
        Map<Integer, TestConfigEntityA> map = cacheServiceImpl.getAll(TestConfigEntityA.class);
        System.out.println("map size: " + map.size());
    }

    @Test
    public void testKV() {
        UserManagedCache<Integer, TestEntityA> cache2 =
                UserManagedCacheBuilder.newUserManagedCacheBuilder(Integer.class, TestEntityA.class)
                        .build(false);
        cache2.init();

        for (int i=0;i<=200000;i++){
            //写
            TestEntityA a = new TestEntityA();
            a.setId(i);
            a.setAge(1);
            a.setCreateDate(0);
            cache2.put(a.getId(), a);
        }
        AtomicInteger val = new AtomicInteger();
        long t5 = System.currentTimeMillis();
        TestEntityA a = cache2.get(123456);
        System.out.println("-----" + a.getId());

        List<TestEntityA> list = new ArrayList<>();

        class TT implements Consumer<EhcacheBase.Entry<Integer, TestEntityA>> {
            @Override
            public void accept(EhcacheBase.Entry<Integer, TestEntityA> o) {
                //list.add(o.getValue());
                //val.addAndGet(o.getValue().getAge());
            }
        }


        TT t = new TT();

        //cache2.forEach(t);

//        Set<Integer> set = new HashSet<>(200001);
//        for (int i = 0; i < 20000; i++) {
//            set.add(i);
//        }
//
//        Map<Integer, TestEntityA> map = cache2.getAll(set);
//        System.out.println("+++" + map.size());

        TestEntityA ta;
        for (int i = 0; i < 200000; i++) {
            ta = cache2.get(i);
            val.addAndGet(ta.getAge());
        }

        long t6 = System.currentTimeMillis();
        System.out.println("++time +" + (t6 - t5));
        System.out.println(val.get());
        System.out.println(list.size());
    }


    @Test
    public void testMyMap() {
        UserManagedCache<Integer, HashMap> cache2 =
                UserManagedCacheBuilder.newUserManagedCacheBuilder(Integer.class, HashMap.class)
                        .build(false);
        cache2.init();

        UserManagedCacheBuilder builder = UserManagedCacheBuilder.newUserManagedCacheBuilder(String.class, HashMap.class);
        HashMap<Integer, TestEntityA> map = new HashMap<>(200000);
        for (int i=0;i<=200000;i++){
            //写
            TestEntityA a = new TestEntityA();
            a.setId(i);
            a.setAge(1);
            map.put(a.getId(), a);
        }
        cache2.put(12, map);
        long t5 = System.currentTimeMillis();
        HashMap<Long, TestEntityA> map2 = cache2.get(12);
        System.out.println("====" + map2.size());
        long t6 = System.currentTimeMillis();
        for (TestEntityA s : map2.values()) {
            if (s.getId() == 123456) {
                System.out.println("----");
                break;
            }
        }
        System.out.println("++00+" + (t6 - t5));
    }
}
