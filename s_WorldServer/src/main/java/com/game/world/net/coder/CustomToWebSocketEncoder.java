package com.game.world.net.coder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * 自定义包 转 websocket
 */
public class CustomToWebSocketEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        WebSocketFrame frame = new BinaryWebSocketFrame(msg);
        out.add(frame);
    }
}
