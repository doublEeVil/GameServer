package com.game.rpc.test;

import com.game.rpc.api.HelloService;
import com.game.rpc.client.RpcClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rpc-client.xml")
public class TestSimple {

    @Autowired
    private RpcClient rpcClient;

    @Test
    public void testSimple() {
        HelloService helloService = rpcClient.create(HelloService.class);
        String result = helloService.hello("World");
        System.out.println(result);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            String result2 = helloService.hello("World");
        }
        long t2 = System.currentTimeMillis();

        System.out.println("time: " + (t2 - t1));
        try {
            Thread.sleep(1506);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
