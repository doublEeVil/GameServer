package com.game.world.game.handler.account;

import com.game.common.util.PrintUtils;
import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.account.Login;
import com.game.protocol.data.account.LoginOk;
import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.ConnectManager;
import com.game.world.game.service.base.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@IHandler(handData = Login.class)
public class LoginHandler extends IDataHandler {
    @Autowired
    private PlayerService playerService;

    @Override
    public void handle(IData data) {
        Login login = (Login) data;

        int playerId = login.getPlayerId();

        System.out.println("===player" + login.getPlayerId() + " login");

        ConnectManager.getInstance().onPlayerLogin(playerId, data.getChannel());

        playerService.onPlayerLogin(playerId);

        LoginOk loginOk = new LoginOk();
        loginOk.setStatus(1);
        data.getChannel().writeAndFlush(loginOk);

    }
}
