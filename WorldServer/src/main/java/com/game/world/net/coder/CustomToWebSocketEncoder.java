package com.game.world.net.coder;

import com.game.world.net.Packet;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
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
public class CustomToWebSocketEncoder extends MessageToMessageEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msg.getBodyMsg().toByteArray().length);
        buf.writeByte(-88);
        buf.writeShort(msg.getHeadId());
        buf.writeBytes(msg.getBodyMsg().toByteArray());
        WebSocketFrame frame = new BinaryWebSocketFrame(buf);
        out.add(frame);
    }
}
