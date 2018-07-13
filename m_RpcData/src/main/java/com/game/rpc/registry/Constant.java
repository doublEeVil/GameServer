package com.game.rpc.registry;

public interface Constant {
    int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/world_server";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/s_";
}
