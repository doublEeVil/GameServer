package com.game.world.net.coder;

import io.netty.buffer.ByteBuf;
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
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = msg.content();
            out.add(buf);
            buf.retain();
        } else if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame tf = (TextWebSocketFrame) msg;
            System.out.println("rcv text: " + tf.text());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //System.out.println();
    }
}