package com.game.cache.test;

import com.game.cache.redis.IRedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:redis.properties"})
@ContextConfiguration(locations = {"classpath:redis-config.xml"})
public class TestRedis {
    private static Logger log = LogManager.getLogger(TestRedis.class);

    @Autowired()
    @Qualifier("com.game.cache.redis.impl.SingleNodeRedisService")
    IRedisService redisService;

    @Test
    public void testRedis() {
        System.out.println("---test redis---");
        String server_name = redisService.getValue("server_name");
        log.info("---server name is " + server_name);
        log.info("---redis正常");
        Assert.assertTrue(server_name == null);
        Assert.assertFalse(server_name != null);
        System.out.println("---test redis pass---");
    }
}
