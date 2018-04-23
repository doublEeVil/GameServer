package com.game.world.net;

import com.game.world.net.coder.CustomProtobufDecoder;
import com.game.world.net.coder.CustomProtobufEncoder;
import com.game.world.net.coder.ProtobufToWebSocketEncoder;
import com.game.world.net.coder.WebSocketToProtobufDecoder;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
        //pip.addLast(new WebSocketFrameHandler());
        pip.addLast(new WebSocketToProtobufDecoder()); // 收到消息时，先将websocket解码成自定义包
        pip.addLast(new CustomProtobufDecoder()); //将自定义包解码成具体的protobuf
        pip.addLast(new ProtobufToWebSocketEncoder()); // 发送消息时将自定义包编码成websocket
        pip.addLast(new CustomProtobufEncoder()); // 将protobuf编码成自定义包
        pip.addLast(new ServerHandler());
    }
}
