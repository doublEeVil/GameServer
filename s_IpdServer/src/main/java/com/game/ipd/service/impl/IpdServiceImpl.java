package com.game.ipd.service.impl;

import com.game.cache.mysql.service.GenericMySqlService;
import com.game.cache.redis.RedisService;
import com.game.ipd.IpdServer;
import com.game.ipd.entity.Account;
import com.game.ipd.entity.Server;
import com.game.ipd.service.IpdService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Component("com.game.ipd.service.impl.IpdServiceImpl")
public class IpdServiceImpl implements IpdService {
    private static final Logger LOGGER = Logger.getLogger(IpdServiceImpl.class);

    @Autowired
    @Qualifier("com.game.cache.mysql.service.impl.GenericMysqlServiceImpl")
    private GenericMySqlService genericMysqlService;

    @Override
    public String sendServerList(Account account) {
        JSONObject ret = new JSONObject();
        ret.put("state", 1);
        List<JSONObject> serverList = new ArrayList<>();

        String loginTimesStr = account.getLoginTimes();
        if (null == loginTimesStr || loginTimesStr.equals("")) {
            loginTimesStr = "{}";
        }
        JSONObject loginTimes = JSONObject.fromObject(loginTimesStr);

        List<Server> allServer = genericMysqlService.getAll(Server.class);
        for (Server server : allServer) {
            if (server.getStatus() == Server.SERVER_STATUS_NO_SHOW) {
                continue;
            }
            JSONObject serverInfo = new JSONObject();
            serverInfo.put("name", server.getServerName());
            serverInfo.put("serverId", server.getServerId());
            serverInfo.put("loginTime", loginTimes.getOrDefault(server.getServerId(), 0));
            serverInfo.put("groupname", server.getArea());
            serverInfo.put("bulletin", "正常");
            serverInfo.put("tips", server.getTips());
            serverInfo.put("status", getServerStatus(server));
            serverList.add(serverInfo);
        }
        ret.put("serverList", serverList);
        return ret.toString();
    }

    @Override
    public Server getServer(int serverId) {
        return genericMysqlService.get(Server.class, serverId);
    }

    @Override
    public Server getRecommendServer(int serverId) {
        if (0 != serverId) {
            return genericMysqlService.get(Server.class, serverId);
        }
        List<Server> allServer = genericMysqlService.getAll(Server.class);
        for (Server server : allServer) {
            if (server.getStatus() == Server.SERVER_STATUS_OPEN) {
                return server;
            }
        }
        return null;
    }

    @Override
    public int getServerStatus(Server server) {
        if (null == IpdServer.getListener().getServerInfo(server.getServerId())) {
            // 服务未启动，认为是维护状态
            return Server.SERVER_STATUS_FIX;
        }
        return server.getStatus();
    }
}
