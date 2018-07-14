package com.game.world.test;

import com.game.world.net.Msg2Msg;
import com.game.world.protocol.account.Login;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class TestProtoCanRun {

    @Test
    public void testProtocolCanRun() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();

        final TestHandler handler =
                new TestHandler(
                        WebSocketClientHandshakerFactory.newHandshaker(
                                new URI("ws://192.168.0.192/ws"), WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(8192));
                        p.addLast(handler);
                    }
                });

        try {
            Channel ch = b.connect("192.168.0.192", 8001).sync().channel();
            handler.handshakeFuture().sync();

            // 发送文本
            WebSocketFrame text = new TextWebSocketFrame("this si");
            ch.writeAndFlush(text);

            // 发送自定义协议1
            Login login = new Login();
            login.setPlayerId(13456);
            ByteBuf buf = Msg2Msg.encodeFromIData(login);
            WebSocketFrame frame = new BinaryWebSocketFrame(buf);
            ch.writeAndFlush(frame);

            // 发送自定义协议2
            com.game.world.protocol.test.Test test = new com.game.world.protocol.test.Test();
            test.setName("zjs");
            buf = Msg2Msg.encodeFromIData(test);
            frame = new BinaryWebSocketFrame(buf);
            ch.writeAndFlush(frame);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class TestHandler extends SimpleChannelInboundHandler<Object> {
    private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public TestHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.err.println("ctx " + ctx);
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("WebSocket Client disconnected!");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            System.out.println("WebSocket Client connected!");
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.getStatus() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            System.out.println("WebSocket Client received message: " + textFrame.text());
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }

}
