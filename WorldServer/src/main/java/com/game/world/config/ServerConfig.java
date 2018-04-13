package com.game.world.config;

import org.apache.commons.configuration.PropertiesConfiguration;

public class ServerConfig {
    PropertiesConfiguration config;
    private int serverPort;
    private int httpPort;

    public ServerConfig(PropertiesConfiguration config) {
        this.config = config;
    }

    public int getServerPort() {
        return config.getInt("world_port");
    }

    public int getHttpPort() {
        return config.getInt("http_port");
    }
}
