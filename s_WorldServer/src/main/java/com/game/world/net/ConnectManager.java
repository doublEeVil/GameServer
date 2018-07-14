package com.game.world.net;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectManager {
    private static ConnectManager instance;
    private Map<Integer, Channel> playerIdChannelMap = new ConcurrentHashMap<>();
    private Map<Channel, Integer> channelPlayerIdMap = new ConcurrentHashMap<>();

    private ConnectManager() {
    }

    public static ConnectManager getInstance() {
        if (null == instance) {
            synchronized (ConnectManager.class) {
                if (null == instance) {
                    instance = new ConnectManager();
                }
            }
        }
        return instance;
    }

    public void playerLogin(int playerId, Channel channel) {
        playerIdChannelMap.put(playerId, channel);
        channelPlayerIdMap.put(channel, playerId);
    }

    public void playerLogout(Channel channel) {
        int id = channelPlayerIdMap.get(channel);
        playerIdChannelMap.remove(id);
        channelPlayerIdMap.remove(channel);
    }

    public void sendData(int playerId, IData data) {
        playerIdChannelMap.get(playerId).writeAndFlush(data);
    }
}
