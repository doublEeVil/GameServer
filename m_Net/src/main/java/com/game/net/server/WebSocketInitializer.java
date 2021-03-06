package com.game.net.server;

import com.game.net.coder.*;
import com.game.net.handler.ServerHandler;
import com.game.net.server.ServerEventListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class WebSocketInitializer extends ChannelInitializer<Channel> {
    private static String WEBSOCKET_PATH = "/ws";
    private ServerEventListener listener;

    public WebSocketInitializer(ServerEventListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();

        pip.addLast(new IdleStateHandler(0, 0 , 5, TimeUnit.SECONDS));

        pip.addLast(new HttpServerCodec());
        pip.addLast(new HttpObjectAggregator(65535));
        pip.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));

        pip.addLast(new WebSocketToCustomDecoder()); // 收到消息时，先将websocket解码成自定义包
        pip.addLast(new CustomToIDataDecoder());

        pip.addLast(new CustomToWebSocketEncoder()); // 发送消息时将自定义包编码成websocket
        pip.addLast(new IDataToCustomEncoder());

        pip.addLast(new ServerHandler(listener));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
