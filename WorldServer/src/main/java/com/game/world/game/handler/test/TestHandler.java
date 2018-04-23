package com.game.world.game.handler.test;

import com.game.world.bean.WorldPlayer;
import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import com.game.world.procol.test.Test;

public class TestHandler extends IDataHandler{

    public void handle(WorldPlayer worldPlayer, IData data) {
        System.out.println("*****test");
        Test test = (Test)data;
        System.out.println(test.getName());
        System.out.println(worldPlayer.getPlayerId());
        worldPlayer.sendData("hahhahah");

    }
}
