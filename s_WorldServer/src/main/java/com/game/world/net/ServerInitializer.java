package com.game.world.net;

import com.game.world.net.coder.CustomToProtobufDecoder;
import com.game.world.net.coder.ProtobufToCustomEncoder;
import com.game.world.net.coder.CustomToWebSocketEncoder;
import com.game.world.net.coder.WebSocketToCustomDecoder;
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
        pip.addLast(new WebSocketToCustomDecoder()); // 收到消息时，先将websocket解码成自定义包
        pip.addLast(new CustomToProtobufDecoder()); //将自定义包解码成具体的protobuf
        pip.addLast(new CustomToWebSocketEncoder()); // 发送消息时将自定义包编码成websocket
        pip.addLast(new ProtobufToCustomEncoder()); // 将protobuf编码成自定义包
        pip.addLast(new ServerHandler());
    }
}
