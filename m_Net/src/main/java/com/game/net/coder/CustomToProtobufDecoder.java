package com.game.net.coder;//package com.game.world.net.coder;
//
//import com.game.world.protocol.ProtocolFactory;
//import com.google.protobuf.MessageLite;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//
//import java.util.List;
//
///**
// * 自定义包转protobuf
// * 协议设计为：
// * 包头 + 包体
// * 包头 = (int 包体长度) + (byte 保留位) +（short 协议类型） = 7 byte
// * 包体就是具体的protobuf部分
// */
//public class CustomToProtobufDecoder extends ByteToMessageDecoder{
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//
//    }
//
//    private MessageLite decodeBody(short dataType, byte[] array) throws Exception{
//        return null;
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//    }
//}
