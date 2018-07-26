package com.game.wuziqi.client.handler.account;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.account.LoginOk;
import com.game.protocol.data.room.FindAllRoom;
import com.game.wuziqi.client.service.ServiceManager;

@IHandler(handData = LoginOk.class)
public class LoginHandler extends IDataHandler {
    @Override
    public void handle(IData data) {
        LoginOk ok = (LoginOk)data;
        if (ok.getStatus() > 0) {
            System.out.println("===登录成功");
        }

        // 查找房间
        FindAllRoom findAllRoom = new FindAllRoom();
        //data.getChannel().writeAndFlush(findAllRoom);
        //
        System.out.println("===准备查找所有房间");

        //
        ServiceManager.getInstance().getClient().sendData(findAllRoom);
        //ServiceManager.getInstance().getClient().sendData(findAllRoom);
//        ServiceManager.getInstance().getClient().sendData(findAllRoom);

    }
}
