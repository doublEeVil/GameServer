package com.game.ipd.http;

import com.game.common.util.CryptionUtils;
import com.game.common.util.Md5Utils;
import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.ipd.IpdServer;
import com.game.ipd.entity.Account;
import com.game.ipd.entity.Server;
import com.game.ipd.service.AccountService;
import com.game.ipd.service.IpdService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;

/**
 * 用户登录
 * 判断是否有效用户
 */
@Component
@HandleHttp(url = "/load")
public class LoadController extends BaseHttpController{
    private static final Logger LOGGER = Logger.getLogger(LoadController.class);

    @Autowired
    @Qualifier("com.game.ipd.service.impl.AccountServiceImpl")
    private AccountService accountService;
    @Autowired
    @Qualifier("com.game.ipd.service.impl.IpdServiceImpl")
    private IpdService ipdService;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        System.out.println("=====ip: " + request.getRemoteHost());
        String data = request.getParameter("data");
        if (!StringUtils.isEmpty(data)) {
            //解码data
            byte[] decode = CryptionUtils.getByteFromHexString(data);
            decode = CryptionUtils.Decrypt(decode, IpdServer.getServerConfig().getDecKey());
            data = new String(decode);
            System.out.println("----- " + data);
            if (validate(data, response)) {
                JSONObject paramJson = JSONObject.fromObject(data);
                String username = paramJson.getString("username");
                String password = paramJson.getString("password");
                String version = paramJson.getString("version");
                String isChannel = "0";
                if (paramJson.has("isChannel")) {
                    isChannel = paramJson.getString("isChannel");
                }
                String channel = paramJson.getString("channel");
                String sign = paramJson.getString("sign");
                int channelId = 0;
                if (channel != null) {
                    channelId = Integer.parseInt(channel);
                }
                boolean signRight = checkSign(sign, username, password, version, channel, isChannel);
                if (!signRight) {
                    System.out.println("=====验证签名失败====");
                    return;
                }
                String ip = request.getRemoteHost();
                String result = action(username, password, version, isChannel, channelId, ip);
                response.getWriter().write(result);
                response.setCharacterEncoding("utf-8");
                response.setHeader("Access-Control-Allow-Origin", "*");
            }
        }
    }

    /**
     * 判断版本
     * @param data
     * @param response
     * @return
     */
    private boolean validate(String data, MockHttpServletResponse response) {
        return true;
    }

    /**
     * 验证签名
     * @param sign
     * @param username
     * @param password
     * @param version
     * @param channel
     * @param isChannel
     * @return
     */
    private boolean checkSign(String sign, String username, String password, String version, String channel, String isChannel) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password) || !StringUtils.hasText(version)) {
            return false;
        }
        String md5key = IpdServer.getServerConfig().getMd5Key();
        String test = username + password + version + channel + isChannel + md5key;
        String check = Md5Utils.md5(test);
        return check.equalsIgnoreCase(sign);
    }

    private String action(String username, String password, String version, String isChannel, int channel, String ip) {
        System.err.println("===ip: " + ip);
        JSONObject result = new JSONObject();

        Account account = accountService.getAccountByName(username);
        if (null == account) {
            //新建账户
            account = new Account();
            account.setId(accountService.getUniqueAcountId());
            account.setChannel(channel);
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(null);
            account.setTel(null);
            account.setLoginTimes("{}");
            account.setCreateDate(System.currentTimeMillis());
            account.setUpdateDate(System.currentTimeMillis());
            accountService.createAccount(account);
        }

        if (null == account) {
            result.put("state", 400);// 帐号不存在
            return result.toString();
        }

        if (!account.getPassword().equals(password)) {
            result.put("state", 500);// 密码错误
            return result.toString();
        }

        String token = accountService.createToken(account);
        result.put("state", 1);
        result.put("version", "200");
        result.put("bullitenList", new String[]{"新服内测1", "新服公告2"});
        result.put("token", token);

        // 找出最近登录的服，如果新注册账号，去推荐服
        JSONObject loginTimes = JSONObject.fromObject(account.getLoginTimes());
        Set<String> keySet = loginTimes.keySet();
        long t = 0;
        long t1;
        int recommandId = 0;
        for (String serverId : keySet) {
            t1 = loginTimes.getLong(serverId);
            if (t1 > t) {
                t = t1;
                recommandId = Integer.valueOf(serverId);
            }
        }

        Server server = ipdService.getRecommendServer(recommandId);

        JSONObject serverInfo = new JSONObject();
        serverInfo.put("name", server.getServerName());
        serverInfo.put("serverId", server.getServerId());
        serverInfo.put("tips", server.getTips());
        serverInfo.put("bulletin", "开启中");
        serverInfo.put("status", ipdService.getServerStatus(server));

        result.put("serverInfo", serverInfo);

        return result.toString();
    }
}
