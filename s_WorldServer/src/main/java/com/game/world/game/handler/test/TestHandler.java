package com.game.world.game.handler.test;

import com.game.common.util.PrintUtils;
import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.test.Test;
import com.game.world.bean.WorldPlayer;
import org.springframework.stereotype.Component;

/**
 * 添加下面的注解
 * @Component表示可以被扫描到
 * @IHandler(handData = Req1Protos.Req1.class)表示需要解析的数据类型
 */
@Component
@IHandler(handData = Test.class)
public class TestHandler extends IDataHandler {

    public void handle(IData data) {
        System.out.println("*****test");
        PrintUtils.printVar(data);
        long t1 = System.currentTimeMillis();
        while (true) {
            long t2 = System.currentTimeMillis();
            if (t2 - t1 > 15000) {
                break;
            }
        }
    }
}
