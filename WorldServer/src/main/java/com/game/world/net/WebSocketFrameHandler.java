package com.game.world.net;

import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.CacheService;
import com.game.world.procol.account.Login;
import com.game.world.procol.ProcolFactory;
import com.game.world.procol.test.Test;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static Logger log = LogManager.getLogger(WebSocketFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame tf = (TextWebSocketFrame) frame;
            //System.out.println(tf.text());
            //ctx.writeAndFlush(new TextWebSocketFrame(tf.text() + " "));
            JSONObject json = JSONObject.fromObject(tf.text());
            int playerId = IDataHandler.channelPlayerIdMap.getOrDefault(ctx.channel(), 0);
            WorldPlayer player = (WorldPlayer)CacheService.getObj(WorldPlayer.class, playerId);
            IData data = decode(json);
            data.setChannel(ctx.channel());
            ProcolFactory.getDataHandler(json.getInt("id")).handle(player, data);
        }
    }

    private IData decode(JSONObject json) {
        int id = json.getInt("id");
        if (id == 10001) {
            Login login = new Login();
            login.setPlayerId(json.getInt("data"));
            return login;
        } else if (id == 20001) {
            Test test = new Test();
            test.setName(json.getString("data"));
            return test;
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
