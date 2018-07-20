package com.game.net.server;

import com.game.net.ProtocolFactory;
import com.game.net.handler.IDataHandler;
import com.game.net.handler.IHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class NetServer {

    private int port;
    private ServerType serverType;
    private ServerEventListener listener;

    private final EventLoopGroup boss = new NioEventLoopGroup(1);
    private final EventLoopGroup worker = new NioEventLoopGroup();
    private ChannelFuture channelFuture;
    private ApplicationContext serverCtx;

    public NetServer(int port, ServerType type, ApplicationContext ctx) {
        this.port = port;
        this.serverType = type;
        this.serverCtx = ctx;
    }

    public void addListener(ServerEventListener listener) {
        this.listener = listener;
    }

    public void launch() {
        // 先初始化协议
        initProtocol();
        // 启动端口监听
        try {
            ServerBootstrap b = new ServerBootstrap();

            ChannelInitializer handler;
            Class channelClass;
            if (serverType == ServerType.TP_WEBSOCKET) {
                handler = new WebSocketInitializer(listener);
                channelClass = NioServerSocketChannel.class;
            } else if (serverType == ServerType.TP_TCP){
                handler = new CommonSocketInitializer(listener);
                channelClass = NioServerSocketChannel.class;
            } else if (serverType == ServerType.TP_UDP) {
                handler = new CommonSocketInitializer(listener);
                channelClass = NioDatagramChannel.class;
            } else {
                handler = null;
                channelClass = NioServerSocketChannel.class;
            }

            b.group(boss, worker)
                    .childHandler(handler)
                    .channel(channelClass);
            channelFuture = b.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                shutdownNet();
            }));
        }
    }

    /**
     * 处理协议与Handler的对应关系
     */
    private void initProtocol() {
        ProtocolFactory.registerAll(serverCtx);
    }

    private void shutdownNet(){
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }
}
