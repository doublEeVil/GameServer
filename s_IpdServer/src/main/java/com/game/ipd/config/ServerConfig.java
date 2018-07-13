package com.game.ipd.config;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ServerConfig {
    PropertiesConfiguration config;

    public ServerConfig(PropertiesConfiguration config) {
        this.config = config;
    }

    public int getHttpPort() {
        return config.getInt("http_port");
    }

    public String getMd5Key() {
        return config.getString("md5key");
    }

    public String getDecKey() {
        return config.getString("deckey");
    }

    public String getZookeeperAddr() {
        return config.getString("zookeeper_addr");
    }

    public String getZkpRootPath() {
        return config.getString("zkp_root_path");
    }
}
