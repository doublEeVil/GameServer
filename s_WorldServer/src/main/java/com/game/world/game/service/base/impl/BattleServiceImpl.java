package com.game.world.game.service.base.impl;

import com.game.protocol.data.battle.RcvOperate;
import com.game.protocol.data.battle.RcvPlace;
import com.game.world.bean.QiPan;
import com.game.world.bean.Room;
import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.ConnectManager;
import com.game.world.game.service.base.BattleService;
import com.game.world.game.service.base.PlayerService;
import com.game.world.game.service.base.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("com.game.world.game.service.base.BattleService.BattleServiceImpl")
public class BattleServiceImpl implements BattleService {

    @Autowired
    private RoomService roomService;
    @Autowired
    private PlayerService playerService;

    private static final int GAME_START = 1;
    private static final int GAME_OVER = 2;
    public static final int CONTINUE = 3; //我方回合
    public static final int WAITING = 4; //等待对方回合

    public void gameReady(int playerId) {
        WorldPlayer player =  playerService.getLoadPlayer(playerId);
        int roomId = player.getRoomId();
        if (roomId < 1) {
            return;
        }
        Room room = roomService.findRoom(roomId);
        if (null == room) {
            return;
        }
        room.getReadySet().add(playerId);
        if (room.getReadySet().size() == 2) {
            // 游戏开始
            room.getQiPan().reset();
            RcvOperate rcvOperate = new RcvOperate();
            rcvOperate.setAction(GAME_START);
            for (int pid : room.getMember()) {
                ConnectManager.getInstance().sendData(pid, rcvOperate);

                if (pid == room.getRoomOwner()) {
                    // 房主回合
                    RcvOperate op = new RcvOperate();
                    op.setAction(CONTINUE);
                    ConnectManager.getInstance().sendData(pid, op);
                }
            }
        }
    }

    public void place(int playerId, int x, int y) {
        WorldPlayer player = playerService.getLoadPlayer(playerId);
        int roomId = player.getRoomId();
        Room room = roomService.findRoom(roomId);
        int qiziFlag = room.getRoomOwner() == playerId ? QiPan.BLACK : QiPan.WHITE;
        int flag = room.getQiPan().setPlace(x, y, qiziFlag);
        if (flag == QiPan.FORBBIDEN) {

        } else if (flag == QiPan.CONTINUE) {
            RcvPlace rcvPlace = new RcvPlace();
            rcvPlace.setPlayerId(playerId);
            rcvPlace.setX(x);
            rcvPlace.setY(y);
            for (int pid : room.getMember()) {
                ConnectManager.getInstance().sendData(pid, rcvPlace);
                if (pid == playerId) {
                    // 等待对手回合
                    RcvOperate op = new RcvOperate();
                    op.setAction(WAITING);
                    ConnectManager.getInstance().sendData(pid, op);
                } else {
                    RcvOperate op = new RcvOperate();
                    op.setAction(CONTINUE);
                    ConnectManager.getInstance().sendData(pid, op);
                }
            }
        } else if (flag == QiPan.GAMEOVER) {
            RcvOperate rcvOperate = new RcvOperate();
            rcvOperate.setAction(GAME_OVER);
            for (int pid : room.getMember()) {
                ConnectManager.getInstance().sendData(pid, rcvOperate);
            }
        }
    }
}
