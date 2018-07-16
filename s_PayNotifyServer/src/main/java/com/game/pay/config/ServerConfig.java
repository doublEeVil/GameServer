package com.game.pay.config;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ServerConfig {
    PropertiesConfiguration config;

    public ServerConfig(PropertiesConfiguration config) {
        this.config = config;
    }

    public int getHttpPort() {
        return config.getInt("http_port");
    }

}
