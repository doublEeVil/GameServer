package com.game.world.net.coder;

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
//        byte[] data = null;
//        int len = in.readableBytes();
//        ByteBuf bodyByteBuf = in.readBytes(len);
//        if (bodyByteBuf.hasArray()) {
//            data = in.array();
//        } else {
//            data = new byte[len];
//            bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), data, 0, len);
//        }
//        long t1 = System.nanoTime();
//        Req1Protos.Req1 req1 = Req1Protos.Req1.getDefaultInstance().getParserForType().parseFrom(data);
//        System.out.println(req1.getEmail()
//                + " " + req1.getName()
//                + " " + req1.getId());
//
//        long t2 = System.nanoTime();
//        System.out.println(t2 - t1);// 大概0.1到0.3毫秒的样子
//        // 已完成解码
//
//        // 测试发送
//        ctx.channel().writeAndFlush(req1);
//
//        if (true) {
//            return;
//        }
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
            byte[] array;
            if (bodyBuf.hasArray()) {
                array = bodyBuf.array();
            } else {
                array = new byte[length];
                bodyBuf.getBytes(bodyBuf.readerIndex(), array, 0, length);
            }
            out.add(decodeBody(dataType, array));
        }
    }

    private MessageLite decodeBody(short dataType, byte[] array) throws Exception{
        MessageLite msg = null;
        if (dataType == 101) {
            msg = Req1Protos.Req1.getDefaultInstance().getParserForType().parseFrom(array);
        }
        return msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
