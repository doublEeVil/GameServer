package com.game.cache.test;

import com.game.cache.mysql.service.GenericMySqlService;
import com.game.cache.test.entity.TestEntityA;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:jdbc.properties"})
@ContextConfiguration(locations = {"classpath:spring-hibernate.xml"})
public class TestMysql {

    @Autowired
    private GenericMySqlService genericMysqlService;

    @Test
    public void testInsert() {
        TestEntityA a = new TestEntityA();
        a.setId(10001);
        a.setCreateDate(System.currentTimeMillis());
        a.setUpdateDate(System.currentTimeMillis());
        a.setAge(25);
        genericMysqlService.save(a);
        System.out.println("==== test insert pass ===");
    }

    @Test
    public void testFind() {
        TestEntityA a = genericMysqlService.get(TestEntityA.class, 10001);
        Assert.assertEquals(a.getAge(), 12);
        List<TestEntityA> list = genericMysqlService.getAll(TestEntityA.class);
        Assert.assertNotNull(list);
        System.out.println("==== test find pass ===");
    }

    @Test
    public void testUpdate() {
        TestEntityA a = genericMysqlService.get(TestEntityA.class, 10001);
        a.setAge(28);
        genericMysqlService.update(a);
        a = genericMysqlService.get(TestEntityA.class, 10001);
        Assert.assertEquals(a.getAge(), 28);
        System.out.println("==== test update pass ===");
    }

    @Test
    public void testDelete() {
        TestEntityA a = genericMysqlService.get(TestEntityA.class, 10001);
        genericMysqlService.delete(a);
        System.out.println("==== test delete pass ===");
    }

    @Test
    public void testByHql() {
        TestEntityA a = genericMysqlService.getByHql(" from TestEntityA a where a.age = ?", 23);
        System.out.println(a.getId());
    }
}
