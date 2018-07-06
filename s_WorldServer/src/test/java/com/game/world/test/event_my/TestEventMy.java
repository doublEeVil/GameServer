package com.game.world.test.event_my;

import com.game.world.event.EventManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试自定义事件
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:redis.properties", "classpath:jdbc.properties"})
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestEventMy {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testEventMy() {
        EventManager.getInstance().initEvent(context);
        EventManager.getInstance().eventExecute(-101, "param1", "param2", 10086);
        System.out.println("--- test my event pass --- ");
    }
}

