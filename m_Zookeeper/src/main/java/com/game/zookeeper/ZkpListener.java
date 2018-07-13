package com.game.zookeeper;

import org.apache.zookeeper.WatchedEvent;

import java.util.List;
import java.util.Map;

public interface ZkpListener {
    /**
     * 根节点创建
     */
    void onCreated(WatchedEvent event);

    /**
     * 根节点删除
     */
    void onDeleted(WatchedEvent event);

    /**
     * 数据更新
     */
    void onDataChanged(WatchedEvent event);

    /**
     * 子节点更新
     */
    void onChildrenChanged(WatchedEvent event);

    /**
     * 最终结果
     */
    void onFinal(Map<String, String> dataMap);
}
