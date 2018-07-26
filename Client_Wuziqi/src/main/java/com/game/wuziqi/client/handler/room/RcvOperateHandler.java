package com.game.wuziqi.client.handler.room;

import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.protocol.data.battle.RcvOperate;
import com.game.wuziqi.client.service.ServiceManager;

@IHandler(handData = RcvOperate.class)
public class RcvOperateHandler extends IDataHandler {
    private static final int GAME_START = 1;
    private static final int GAME_OVER = 2;
    public static final int CONTINUE = 3; //我方回合
    public static final int WAITING = 4; //等待对方回合

    @Override
    public void handle(IData data) {
        RcvOperate rcvOperate = (RcvOperate) data;
        switch (rcvOperate.getAction()) {
            case GAME_START:
                System.err.println("===游戏正式开始");
                ServiceManager.getInstance().getQiPan().print();
                break;
            case GAME_OVER:
                System.err.println("===游戏结束。。。。");
                ServiceManager.getInstance().setCanGame(false);
                break;
            case CONTINUE:
                System.err.println("===我方回合");
                ServiceManager.getInstance().setCanGame(true);
                break;
            case WAITING:
                System.err.println("===对方回合");
                ServiceManager.getInstance().setCanGame(false);
                break;
        }
    }
}
