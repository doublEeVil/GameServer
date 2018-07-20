package com.game.net.coder;

import com.game.net.handler.IData;
import com.game.net.handler.Msg2Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class IDataToNetMsgEncoder extends MessageToByteEncoder<IData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, IData msg, ByteBuf out) throws Exception {
        System.out.println("====");
        ByteBuf buf = Msg2Msg.encodeFromIData(msg);
        out.writeBytes(buf);
    }
}
