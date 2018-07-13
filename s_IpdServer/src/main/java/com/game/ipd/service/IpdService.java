package com.game.ipd.service;


import com.game.ipd.entity.Account;
import com.game.ipd.entity.Server;

public interface IpdService {
    /**
     * 发送服务列表
     * @param account
     * @return
     */
    String sendServerList(Account account);

    /**
     *
     * @param serverId
     * @return
     */
    Server getServer(int serverId);

    /**
     * 推荐服
     * @param serverId
     * @return
     */
    Server getRecommendServer(int serverId);

    /**
     * 获取服务状态
     * @param server
     * @return
     */
    int getServerStatus(Server server);
}
