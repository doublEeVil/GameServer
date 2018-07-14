package com.game.world.net;

import com.game.world.bean.WorldPlayer;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class IDataHandler {
    public abstract void handle(WorldPlayer worldPlayer, IData data);
}
