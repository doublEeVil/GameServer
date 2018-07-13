package com.game.ipd.listener;

import java.util.regex.Pattern;

public class ServerInfo {
    private int serverId;
    private String addr;
    // 节点都是serverid + "_m" + 随机串组成
    private static final Pattern ptn = Pattern.compile("_m.*");

    public ServerInfo(String node, String addr) {
        this.addr = addr;
        String id = ptn.matcher(node).replaceAll("");
        this.serverId = Integer.valueOf(id);
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
