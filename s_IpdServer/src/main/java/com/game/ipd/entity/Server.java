package com.game.ipd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "game_server")
public class Server implements Serializable{
    public static final int SERVER_STATUS_NO_SHOW = -1; // 不可见
    public static final int SERVER_STATUS_OPEN = 0; // 开启，正常状态
    public static final int SERVER_STATUS_FIX = 1; // 维护
    public static final int SERVER_STATUS_VERTIFY = 2; // 审核
    public static final int SERVER_STATUS_FULL = 3; // 火爆
    private int serverId;
    private String serverName;
    private int status;
    private String area;
    private int tips; //新服状态表示 0无1新服2推荐3热门

    @Id
    @Column(name = "server_id", unique = true)
    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Column(name = "server_name", unique = true, nullable = false)
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "tips")
    public int getTips() {
        return tips;
    }

    public void setTips(int tips) {
        this.tips = tips;
    }
}
