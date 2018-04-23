package com.game.world.net.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.util.List;

/**
 * websocket 转成 protobuf
 */
public class WebSocketToProtobufDecoder extends MessageToMessageDecoder<WebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List out) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = msg.content();
            out.add(buf);
            buf.retain();
        }
    }
}
