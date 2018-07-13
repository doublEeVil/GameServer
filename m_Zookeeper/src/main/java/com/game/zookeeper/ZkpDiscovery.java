package com.game.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class ZkpDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(ZkpDiscovery.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String zookeeperAddress;
    private String rootPath; //根目录地址, 例如 world

    private ZooKeeper zookeeper;

    public ZkpDiscovery(String zookeeperAddress, String rootPath) {
        this.zookeeperAddress = zookeeperAddress;
        this.rootPath = rootPath;
        zookeeper = connectServer();
    }

    public void addListener(ZkpListener listener) {
        watchNode(zookeeper, listener);
    }

    private void watchNode(ZooKeeper zk, ZkpListener listener) {
        try {
            List<String> nodeList = zk.getChildren("/" + rootPath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    switch (event.getType()) {
                        case None:
                            break;
                        case NodeCreated:
                            listener.onCreated(event);
                            break;
                        case NodeDeleted:
                            listener.onDeleted(event);
                            break;
                        case NodeDataChanged:
                            listener.onDataChanged(event);
                            break;
                        case NodeChildrenChanged:
                            listener.onChildrenChanged(event);
                            watchNode(zk, listener);
                            break;
                    }
                }
            });

            Map<String, String> map = new HashMap<>();
            for (String node : nodeList) {
                System.err.println("---> find nodes ---- " + node);
                byte[] bytes = zk.getData( "/" + rootPath + "/" + node, false, null);
                map.put(node, new String(bytes));
            }

            logger.debug("node data: {}", map);
            System.out.println("node data: " +  map);

            listener.onFinal(map);
            logger.debug("Service discovery triggered updating connected server node.");
        } catch (KeeperException | InterruptedException e) {
            logger.error("", e);
            e.printStackTrace();
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(zookeeperAddress, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException | InterruptedException e) {
            logger.error("", e);
        }
        return zk;
    }
}
