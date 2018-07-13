package com.game.ipd.http;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.ipd.IpdServer;
import com.game.ipd.entity.Account;
import com.game.ipd.entity.Server;
import com.game.ipd.listener.ServerInfo;
import com.game.ipd.listener.ServerNodeListener;
import com.game.ipd.service.AccountService;
import com.game.ipd.service.IpdService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * 用户登录， 获取最新的分区信息
 */
@Component
@HandleHttp(url = "/ipd")
public class IpdController extends BaseHttpController{
    private static final Logger LOGGER = Logger.getLogger(IpdController.class);

    @Autowired
    @Qualifier("com.game.ipd.service.impl.IpdServiceImpl")
    private IpdService ipdService;
    @Autowired
    @Qualifier("com.game.ipd.service.impl.AccountServiceImpl")
    private AccountService accountService;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        String token = request.getParameter("token");
        String serverId = request.getParameter("serverId");
        Account account = accountService.getAccountByToken(token);
        Server server = ipdService.getServer(Integer.valueOf(serverId));
        ServerInfo serverInfo = IpdServer.getListener().getServerInfo(Integer.valueOf(serverId));
        JSONObject ret = new JSONObject();
        if (account != null && server != null && serverInfo != null) {
            ret.put("state", 1);
            ret.put("name", server.getServerName());
            ret.put("token", token);
            ret.put("ip", serverInfo.getAddr());
            // 记录这次登录信息
            JSONObject loginTimes = JSONObject.fromObject(account.getLoginTimes());
            loginTimes.put(serverId, System.currentTimeMillis());
            account.setLoginTimes(loginTimes.toString());
            accountService.updateAccountInfo(account);
        } else {
            ret.put("state" , -1);
        }
        response.getWriter().write(ret.toString());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}
