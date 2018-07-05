package com.game.test;

import com.game.cache.redis.IRedisService;
import com.game.world.game.service.ServiceManager;
import com.game.world.servlet.TestServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试功能模块是否正常
 */

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class ServerModuleTest {
    private static Logger log = LogManager.getLogger(TestServlet.class);

    @Autowired()
    @Qualifier("com.game.cache.redis.impl.SingleNodeRedisService")
    IRedisService redisService;

    /**
     * 测试redis
     */
    @Test
    public void testRedis() {
        // redis
        IRedisService redisService = ServiceManager.getInstance().getRedisService();
        String server_name = redisService.getValue("server_name");
        log.info("---server name is " + server_name);
        log.info("---redis正常");
    }
}
