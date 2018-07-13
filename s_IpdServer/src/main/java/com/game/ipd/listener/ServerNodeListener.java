package com.game.ipd.listener;

import com.game.ipd.entity.Server;
import com.game.zookeeper.ZkpListener;
import org.apache.zookeeper.WatchedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ServerNodeListener implements ZkpListener {
    private Map<Integer, ServerInfo> nodeMap = new HashMap<>();

    @Override
    public void onCreated(WatchedEvent event) {

    }

    @Override
    public void onDeleted(WatchedEvent event) {

    }

    @Override
    public void onDataChanged(WatchedEvent event) {

    }

    @Override
    public void onChildrenChanged(WatchedEvent event) {

    }

    @Override
    public void onFinal(Map<String, String> dataMap) {
        Map<Integer, ServerInfo> map = new HashMap<>();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            ServerInfo serverInfo = new ServerInfo(entry.getKey(), entry.getValue());
            map.put(serverInfo.getServerId(), serverInfo);
        }
        this.nodeMap = map;
        System.err.println("=====" + this.nodeMap);
    }

    public ServerInfo getServerInfo(int serverId) {
        return nodeMap.get(serverId);
    }
}
