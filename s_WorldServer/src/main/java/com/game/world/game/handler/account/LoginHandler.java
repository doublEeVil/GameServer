package com.game.world.game.handler.account;

import com.game.common.util.PrintUtils;
import com.game.world.WorldServer;
import com.game.world.bean.WorldPlayer;
import com.game.world.net.ConnectManager;
import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import com.game.world.net.IHandler;
import com.game.world.protocol.account.Login;
import org.springframework.stereotype.Component;

@Component
@IHandler(handData = Login.class)
public class LoginHandler extends IDataHandler {
    @Override
    public void handle(WorldPlayer worldPlayer, IData data) {
        System.out.println("++++ login");
        PrintUtils.printVar(data);
        Login login = (Login) data;
        ConnectManager.getInstance().playerLogin(login.getPlayerId(), data.getChannel());

        WorldPlayer player = new WorldPlayer();
        player.setPlayerId(login.getPlayerId());

        player.sendData(data);
    }
}
