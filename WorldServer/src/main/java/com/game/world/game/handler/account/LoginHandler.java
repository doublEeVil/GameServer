package com.game.world.game.handler.account;

import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.CacheService;
import com.game.world.net.IData;
import com.game.world.net.IDataHandler;
import com.game.world.procol.StuProtos;
import com.game.world.procol.account.Login;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class LoginHandler extends IDataHandler {
    @Override
    public void handle(WorldPlayer worldPlayer, IData data) {
        System.out.println("++++ login");
        Login login = (Login) data;
        int playerId = login.getPlayerId();
        if (!playerIdChannelMap.containsKey(playerId)) {
            WorldPlayer player = new WorldPlayer();
            player.setPlayerId(playerId);
            player.setId(playerId);
            CacheService.addObj(player);
            System.out.println("---" + data);
            playerIdChannelMap.put(playerId, data.getChannel());
            channelPlayerIdMap.put(data.getChannel(), playerId);
        } else {
            playerIdChannelMap.put(playerId, data.getChannel());
            channelPlayerIdMap.put(data.getChannel(), playerId);
        }
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeInt(12);
        byteBuf.writeByte('s');
        StuProtos.Stu stu = StuProtos.Stu.newBuilder().setId(1233).setName("zhujinshan").build();
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(byteBuf);
        data.getChannel().writeAndFlush(frame);
    }
}
