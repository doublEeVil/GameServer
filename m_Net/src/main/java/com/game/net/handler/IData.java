package com.game.net.handler;

import io.netty.channel.Channel;

public abstract class IData implements Cloneable{
    private short protocolId;           //协议号
    private Channel channel = null;
    private boolean handleOver = false;

    public IData(short protocolId) {
        this.protocolId = protocolId;
    }

    public short getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(short protocolId) {
        this.protocolId = protocolId;
    }

    public boolean isHandleOver() {
        return handleOver;
    }

    public void setHandleOver(boolean handleOver) {
        this.handleOver = handleOver;
    }

    @Override
    public IData clone() throws CloneNotSupportedException {
        return (IData) super.clone();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
