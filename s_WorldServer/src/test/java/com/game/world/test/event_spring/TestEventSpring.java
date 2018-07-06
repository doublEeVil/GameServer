package com.game.world.test.event_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试spring内部自定义事件
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:redis.properties", "classpath:jdbc.properties"})
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestEventSpring {

    @Test
    public void testEventSpring() {
        EventPublisher.getPublisher().publishEvent(new MyEvent(this, -102, new Object[]{"paa", 123}));
        System.out.println("--- test event spring pass ---");
    }
}
