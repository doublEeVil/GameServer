package com.game.net.handler.client;

import com.game.net.coder.CustomToIDataDecoder;
import com.game.net.coder.IDataToCustomEncoder;
import com.game.net.handler.ServerHandler;
import com.game.net.server.ServerEventListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class CommonSocketInitializer extends ChannelInitializer<Channel> {

    private ServerEventListener listener;

    public CommonSocketInitializer(ServerEventListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        pip.addLast(new IdleStateHandler(0, 0 , 10, TimeUnit.SECONDS));
        pip.addLast(new CustomToIDataDecoder());
        pip.addLast(new IDataToCustomEncoder());
        pip.addLast(new ServerHandler(listener));
    }
}
