package com.game.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZkpRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ZkpRegistry.class);
    private CountDownLatch latch = new CountDownLatch(1);
    private String zookeeperAddress; //zookeeper地址
    private String rootPath; //根目录地址, 例如  world
    private String dataPath; //注册目录地址 例如 world_s1

    public ZkpRegistry(String zookeeperAddress, String rootPath, String dataPath) {
        this.zookeeperAddress = zookeeperAddress;
        this.rootPath = rootPath;
        this.dataPath = dataPath;
    }

    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                addRootNode(zk); // Add root node if not exist
                createNode(zk, data);
            }
        }
    }

    private void createNode(ZooKeeper zk, String data) {
        byte[] bytes = data.getBytes();
        String path = null;
        try {
            path = zk.create("/" + rootPath + "/" + dataPath, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.err.println(path);
            logger.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void addRootNode(ZooKeeper zk) {
        try {
            Stat s = zk.exists("/" + rootPath, false);
            if (null == s) {
                zk.create("/" + rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(zookeeperAddress, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    latch.countDown();
                }
            });
            latch.await();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return zk;
    }
}
