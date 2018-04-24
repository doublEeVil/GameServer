package com.game.world.net.coder;

import com.game.world.net.Packet;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * protobuf 转自定义包
 */
public class ProtobufToCustomEncoder extends MessageToMessageEncoder<MessageLite> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLite msg, List<Object> out) throws Exception {
        Packet packet = new Packet((short) 1, msg);
        out.add(packet);
    }
}
