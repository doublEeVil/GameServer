package com.game.net.client;

import com.game.net.ProtocolFactory;
import com.game.net.handler.IData;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import com.game.net.handler.Msg2Msg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.util.Map;

/**
 * websocket 客户端
 */
public class NetWebSocketClient {
    private String host;
    private int port;
    private String wsUrl;
    private ApplicationContext context;
    private Channel channel;

    public NetWebSocketClient(String host, int port, String wsUrl, ApplicationContext ctx) {
        this.host = host;
        this.port = port;
        this.wsUrl = wsUrl;
        this.context = ctx;
    }

    public void launch() throws Exception{
        // 初始化协议
        initProtocol();

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .handler(new WebSocketInitializer(wsUrl));
        try {
            channel = b.connect(host, port).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void initProtocol() {
        ProtocolFactory.registerAll(context);
    }

    /**
     * 发送数据
     * @param data
     */
    public void sendData(Object data) {
        channel.writeAndFlush(data);
    }

    /**
     * 发送数据
     * @param data
     */
    public void sendData(IData data) {
        channel.writeAndFlush(new BinaryWebSocketFrame(Msg2Msg.encodeFromIData(data)));
    }
}
