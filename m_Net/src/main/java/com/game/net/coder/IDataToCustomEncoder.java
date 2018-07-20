package com.game.net.coder;


import com.game.net.handler.IData;
import com.game.net.handler.Msg2Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class IDataToCustomEncoder extends MessageToMessageEncoder<IData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, IData msg, List<Object> out) throws Exception {
        out.add(getMsg(msg));
    }

    private ByteBuf getMsg(IData msg) {
        ByteBuf buf = Msg2Msg.encodeFromIData(msg);
        return buf;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
