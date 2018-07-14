package com.game.world.net.coder;

import com.game.world.net.IData;
import com.game.world.net.Msg2Msg;
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
}
