package com.game.world.game.handler.account;

import com.game.world.bean.WorldPlayer;
import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import com.game.world.net.IHandler;
import com.game.world.procol.Req2Protos;
import org.springframework.stereotype.Component;

@Component
@IHandler(handData = Req2Protos.Req2.class)
public class LoginHandler extends IDataHandler {
    @Override
    public void handle(WorldPlayer worldPlayer, IData data) {
        System.out.println("++++ login");
    }
}
