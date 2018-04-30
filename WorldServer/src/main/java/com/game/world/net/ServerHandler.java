package com.game.world.net;

import com.game.world.bean.WorldPlayer;
import com.game.world.game.service.MessageService;
import com.game.world.procol.ProtocolFactory;
import com.game.world.procol.Req1Protos;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 处理网络连接以及心跳检测
 */
public class ServerHandler extends SimpleChannelInboundHandler<MessageLite> {
    private static Logger log = LogManager.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageLite msg) throws Exception {
        MessageService.addMessage(new IData(msg));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + " 链接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress() + "断开链接");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 处理心跳部分
        if (evt instanceof IdleStateEvent) {
            //ctx.writeAndFlush(Unpooled.copiedBuffer("1", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
