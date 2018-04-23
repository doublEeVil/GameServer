package com.game.world.net.coder;

import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 协议设计为：
 * 包头 + 包体
 * 包头 = (int 包体长度) + (byte 保留位) +（short 协议类型） = 7 byte
 * 包体就是具体的protobuf部分
 */
public class CustomProtobufDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > 7) { // 可读部分小于7，直接退出
            in.markReaderIndex();

            int length = in.readInt(); // protobuf长度
            in.readByte(); // 保留位
            short dataType = in.readShort();

            if (in.readableBytes() < length) { //可读部分小于body，恢复读指针
                in.resetReaderIndex();
                return;
            }

            ByteBuf bodyBuf = in.readBytes(length);
            out.add(decodeBody(dataType, bodyBuf.array()));
        }
    }

    private MessageLite decodeBody(short dataType, byte[] array) {
        return null;
    }
}
