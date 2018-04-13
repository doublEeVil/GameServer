package com.game.world.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerInitializer extends ChannelInitializer<Channel> {
    private static String WEBSOCKET_PATH = "/ws";
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        pip.addLast(new IdleStateHandler(0, 0 , 5, TimeUnit.SECONDS));
        pip.addLast(new HttpServerCodec());
        pip.addLast(new HttpObjectAggregator(65535));
        pip.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
        pip.addLast(new WebSocketFrameHandler());
        pip.addLast(new ServerHandler());
    }
}
