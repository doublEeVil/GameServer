package com.game.world.config;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ServerConfig {
    PropertiesConfiguration config;

    public ServerConfig(PropertiesConfiguration config) {
        this.config = config;
    }

    public int getServerPort() {
        return config.getInt("world_port");
    }

    public int getHttpPort() {
        return config.getInt("http_port");
    }

    public String getServerName() {
        return config.getString("server_name");
    }

    public int getServerId() {
        return config.getInt("server_id");
    }

    public String getOpenAddr() {
        return config.getString("server_open_addr");
    }

    public String getZookeeperAddr() {
        return config.getString("zookeeper_addr");
    }

    public String getZkpRootPath() {
        return config.getString("zkp_root_path");
    }
}
