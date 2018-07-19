package com.game.net.client;

import com.game.net.handler.IData;
import com.game.net.handler.Msg2Msg;
import com.game.net.handler.client.CommonSocketInitializer;
import com.game.net.handler.client.WebSocketInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import java.net.URI;
import java.net.URISyntaxException;

public class NetClient {
    private EventLoopGroup group = new NioEventLoopGroup();

    private String hostIp;
    private int hostPort;
    private ClientType clientType;
    private String websocketUrl;

    private Channel channel;

    public NetClient(String hostIp, int hostPort, ClientType clientType) {
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        this.clientType = clientType;
    }

    public NetClient(String hostIp, int hostPort, ClientType clientType, String websocketUrl) {
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        this.clientType = clientType;
        this.websocketUrl = websocketUrl;
    }

    public void launch() {
        Bootstrap b = new Bootstrap();

        ChannelInitializer handler;
        Class channelClass;
        if (clientType == ClientType.TP_WEBSOCKET) {
            handler = new WebSocketInitializer(null);
            channelClass = NioSocketChannel.class;
        } else if (clientType == ClientType.TP_TCP){
            handler = new CommonSocketInitializer(null);
            channelClass = NioSocketChannel.class;
        } else if (clientType == ClientType.TP_UDP) {
            handler = new CommonSocketInitializer(null);
            channelClass = NioDatagramChannel.class;
        } else {
            handler = null;
            channelClass = NioServerSocketChannel.class;
        }

        b.group(group).channel(channelClass)
                .handler(handler);

        try {
            channel = b.connect(hostIp, hostPort).sync().channel();

            // 如果是websocket
            if (clientType == ClientType.TP_WEBSOCKET) {
                WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                        new URI(websocketUrl), WebSocketVersion.V13, null, false, new DefaultHttpHeaders());
                handshaker.handshake(channel).sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送数据
     * @param data
     */
    public void sendData(IData data) {
//        if (clientType == ClientType.TP_WEBSOCKET) {
//            ByteBuf buf = Msg2Msg.encodeFromIData(data);
//            WebSocketFrame frame = new BinaryWebSocketFrame(buf);
//            channel.writeAndFlush(frame);
//        } else {
//            channel.writeAndFlush(data);
//        }
        channel.writeAndFlush(data);
    }
}
