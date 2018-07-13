package com.game.rpc.client;

public interface IAsyncObjectProxy {
    RPCFuture call(String funcName, Object... args);
}
