package com.game.world.game.service;

import com.game.net.handler.IData;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectManager {
    private Map<Integer, Channel> channelMap = new ConcurrentHashMap<>();
    private Map<Channel, Integer> playerIdMap = new ConcurrentHashMap<>();
    private static ConnectManager instance = new ConnectManager();

    private ConnectManager() {

    }

    public static ConnectManager getInstance() {
        return instance;
    }

    public void onPlayerLogin(int playerId, Channel channel) {
        channelMap.put(playerId, channel);
        playerIdMap.put(channel, playerId);
    }

    public void onPlayerLogout(Channel channel) {
        int playerId = playerIdMap.getOrDefault(channel, -1);

        if (playerId > 0) {
            channelMap.remove(playerId);
            playerIdMap.remove(channel);
            ServiceManager.getInstance().getPlayerService().onPlayerLogout(playerId);
        }

    }

    public int getPlayerId(Channel channel) {
        return playerIdMap.getOrDefault(channel, -1);
    }

    public boolean isOnline(int playerId) {
        return channelMap.containsKey(playerId);
    }

    public void sendData(int playerId, IData data) {
        channelMap.get(playerId).writeAndFlush(data);
    }
 }
