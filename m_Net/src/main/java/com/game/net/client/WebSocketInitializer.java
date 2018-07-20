package com.game.net.client;


import com.game.net.coder.CustomToIDataDecoder;
import com.game.net.coder.NetMsgToWebSocket;
import com.game.net.coder.WebSocketToCustomDecoder;
import com.game.net.handler.ServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class WebSocketInitializer extends ChannelInitializer<Channel> {
    private final WebSocketClientHandshaker handshaker ;
    private final String wsUrl;

    public WebSocketInitializer(String wsUrl) throws URISyntaxException {
        this.wsUrl = wsUrl;
        this.handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                new URI(wsUrl), WebSocketVersion.V13, null, false, new DefaultHttpHeaders());

    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        pip.addLast(new IdleStateHandler(0, 0 , 5, TimeUnit.SECONDS));
        pip.addLast(new HttpClientCodec());
        pip.addLast(new HttpObjectAggregator(65535));
        pip.addLast(new WebSocketClientProtocolHandler(handshaker, false));
        pip.addLast(new NetMsgToWebSocket(handshaker));
        pip.addLast(new WebSocketToCustomDecoder());
        pip.addLast(new CustomToIDataDecoder());
        pip.addLast(new ServerHandler(null));
    }
}
