package com.game.world.net.coder;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * protobuf 转 websocket
 */
public class ProtobufToWebSocketEncoder extends MessageToMessageEncoder<MessageOrBuilder> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageOrBuilder msg, List out) throws Exception {
        ByteBuf result = null;
        if (msg instanceof MessageLite) {
            result = wrappedBuffer(((MessageLite) msg).toByteArray());
        } else if (msg instanceof MessageLite.Builder) {
            result = wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
        }
        WebSocketFrame frame = new BinaryWebSocketFrame(result);
        out.add(frame);
    }
}
