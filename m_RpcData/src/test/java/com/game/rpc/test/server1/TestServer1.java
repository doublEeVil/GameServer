package com.game.rpc.test.server1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rpc-s1.xml")
public class TestServer1 {

    @Test
    public void server1() {
        //new ClassPathXmlApplicationContext("server-spring.xml");
    }
}
