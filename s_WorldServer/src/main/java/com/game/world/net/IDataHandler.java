package com.game.world.net;

import com.game.world.bean.WorldPlayer;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class IDataHandler {
    public static Map<Integer, Channel> playerIdChannelMap = new ConcurrentHashMap<>();
    public static Map<Channel, Integer> channelPlayerIdMap = new ConcurrentHashMap<>();

    public abstract void handle(WorldPlayer worldPlayer, IData data);
}
