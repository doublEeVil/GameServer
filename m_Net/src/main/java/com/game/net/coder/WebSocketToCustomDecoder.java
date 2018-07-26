package com.game.net.coder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * websocket 转成 自定义包
 */
public class WebSocketToCustomDecoder extends MessageToMessageDecoder<WebSocketFrame> {
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List out) throws Exception {
        //System.out.println("==== rcv msg");
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = msg.content();
            out.add(buf);

            //
            //String rcv = ByteBufUtil.hexDump(buf);
            //System.out.println("===" + rcv);

            buf.retain();
        } else if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame tf = (TextWebSocketFrame) msg;
            System.out.println("rcv text: " + tf.text());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // cause.printStackTrace();
        //System.out.println();
    }
}
