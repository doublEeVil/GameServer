package com.game.world.net.coder;

import com.game.world.procol.ProtocolFactory;
import com.game.world.procol.Req1Protos;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义包转protobuf
 * 协议设计为：
 * 包头 + 包体
 * 包头 = (int 包体长度) + (byte 保留位) +（short 协议类型） = 7 byte
 * 包体就是具体的protobuf部分
 */
public class CustomToProtobufDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > 7) { // 可读部分小于7，直接退出
            in.markReaderIndex();
            int length = in.readInt(); // protobuf长度
            byte flag = in.readByte(); // 保留位
            if (flag != -88) {
                //整个包是有问题的，废弃这个包
                in.skipBytes(in.readableBytes());
                return;
            }
            short dataType = in.readShort();
            if (in.readableBytes() < length) { //可读部分小于body，恢复读指针
                in.resetReaderIndex();
                return;
            }
            ByteBuf bodyBuf = in.readBytes(length);
            byte[] array;
            if (bodyBuf.hasArray()) {
                array = bodyBuf.array();
            } else {
                array = new byte[length];
                bodyBuf.getBytes(bodyBuf.readerIndex(), array, 0, length);
            }
            MessageLite msg = decodeBody(dataType, array);
            out.add(msg);
        }
    }

    private MessageLite decodeBody(short dataType, byte[] array) throws Exception{
        return ProtocolFactory.getMsg(dataType).getParserForType().parseFrom(array);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
