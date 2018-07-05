package com.game.world.game.handler.test;

import com.game.world.bean.WorldPlayer;
import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import com.game.world.net.IHandler;
import com.game.world.procol.Req1Protos;
import org.springframework.stereotype.Component;

/**
 * 添加下面的注解
 * @Component表示可以被扫描到
 * @IHandler(handData = Req1Protos.Req1.class)表示需要解析的数据类型
 */
@Component
@IHandler(handData = Req1Protos.Req1.class)
public class TestHandler extends IDataHandler{

    public void handle(WorldPlayer worldPlayer, IData data) {
        System.out.println("*****test");
        Req1Protos.Req1 req1 = (Req1Protos.Req1)data.getMsg();
        System.out.println(req1.getEmail());
        long t1 = System.currentTimeMillis();
        while (true) {
            long t2 = System.currentTimeMillis();
            if (t2 - t1 > 15000) {
                break;
            }
        }
    }
}