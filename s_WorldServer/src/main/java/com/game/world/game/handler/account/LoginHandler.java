package com.game.world.game.handler.account;

import com.game.common.util.PrintUtils;
import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.account.Login;
import com.game.world.WorldServer;
import com.game.world.bean.WorldPlayer;
import org.springframework.stereotype.Component;

@Component
@IHandler(handData = Login.class)
public class LoginHandler extends IDataHandler {
    @Override
    public void handle(IData data) {
        System.out.println("++++ login");
        PrintUtils.printVar(data);
        Login login = (Login) data;

        WorldPlayer player = new WorldPlayer();
        player.setPlayerId(login.getPlayerId());

        player.sendData(data);
        data.getChannel().writeAndFlush(data);
    }
}
