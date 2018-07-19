package com.game.net.server;

import io.netty.channel.Channel;

public interface ServerEventListener {

    /**
     * 新客户端链接
     * @param channel
     */
    void onConnect(Channel channel);

    /**
     * 客户端断开
     * @param channel
     */
    void onDisconnect(Channel channel);


    /**
     * 发送心跳
     * @param channel
     * @param evt
     */
    void onUserEventTriggered(Channel channel, Object evt);
}
