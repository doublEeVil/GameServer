package com.game.net.client;

import com.game.net.ProtocolFactory;
import com.game.net.handler.IData;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;

public class NetClient {
    private EventLoopGroup group = new NioEventLoopGroup();

    private String hostIp;
    private int hostPort;
    private ClientType clientType;
    private Channel channel;
    private ApplicationContext context;

    public NetClient(String hostIp, int hostPort, ClientType clientType, ApplicationContext context) {
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        this.clientType = clientType;
        this.context = context;
    }

    public void launch() throws Exception {
        // 注册协议
        ProtocolFactory.registerAll(context);

        Bootstrap b = new Bootstrap();
        ChannelInitializer handler = new CommonSocketInitializer(null);
        Class channelClass;
        if (clientType == ClientType.TP_TCP){
            channelClass = NioSocketChannel.class;
        } else if (clientType == ClientType.TP_UDP) {
            channelClass = NioDatagramChannel.class;
        } else {
            throw new RuntimeException("不允许其他选项");
        }

        b.group(group)
                .channel(channelClass)
                .remoteAddress(hostIp, hostPort)
                .handler(handler);

        try {
            channel = b.connect().sync().channel();
        } catch (InterruptedException e) {
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
        channel.writeAndFlush(data);
    }
}
