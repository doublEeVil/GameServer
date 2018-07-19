package com.game.net.handler;

import com.game.net.server.ServerEventListener;
import com.game.net.service.MessageService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 处理网络连接以及心跳检测
 */
public class ServerHandler extends SimpleChannelInboundHandler<IData> {
    private static Logger log = LogManager.getLogger(ServerHandler.class);
    private ServerEventListener listener;

    public ServerHandler(ServerEventListener listener) {
        this.listener = listener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IData msg) throws Exception {
        msg.setChannel(ctx.channel());
        MessageService.addMessage(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 链接");
        if (listener != null) {
            listener.onConnect(ctx.channel());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + "断开链接");
        if (listener != null) {
            listener.onDisconnect(ctx.channel());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 处理心跳部分
        if (evt instanceof IdleStateEvent) {
            //ctx.writeAndFlush(Unpooled.copiedBuffer("1", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
        if (listener != null) {
            listener.onUserEventTriggered(ctx.channel(), evt);
        }

    }
}
