package com.game.world.game.service.base;

import com.game.cache.mysql.service.impl.GenericMysqlService;
import com.game.world.bean.TestEntityA;
import com.game.world.bean.TestEntityB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testServer")
public class TestServer {
    @Autowired
    private AbcService abcService;
    @Autowired
    GenericMysqlService genericMysqlService;

    public void say() {
        System.out.println("====say===");
        System.out.println(abcService);
        abcService.say();
    }

    public void testMysql() {
        TestEntityA a = genericMysqlService.get(TestEntityA.class, 1L);
        if (null == a) {
            System.out.println("a == null");
            a = new TestEntityA();
            a.setId(1);
            a.setAge(23);
            genericMysqlService.save(a);
        } else {
            System.out.println("a ===" + a.getAge());
        }

        TestEntityB b = genericMysqlService.get(TestEntityB.class, 1L);
        if (null == b) {
            System.out.println("a == null");
            b = new TestEntityB();
            b.setId(1);
            b.setName("445");
            genericMysqlService.save(b);
        } else {
            System.out.println("b ===" + b.getName());
        }
    }

    public void testException() {
        int n = 9/0;
        System.out.println(n);
    }
}
