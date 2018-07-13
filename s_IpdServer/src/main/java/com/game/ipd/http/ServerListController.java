package com.game.ipd.http;

import com.game.http.controller.BaseHttpController;
import com.game.http.controller.HandleHttp;
import com.game.ipd.entity.Account;
import com.game.ipd.service.AccountService;
import com.game.ipd.service.IpdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@HandleHttp(url = "/serverList")
public class ServerListController extends BaseHttpController{
    private static final Logger LOGGER = Logger.getLogger(ServerListController.class);

    @Autowired
    @Qualifier("com.game.ipd.service.impl.AccountServiceImpl")
    private AccountService accountService;

    @Autowired
    @Qualifier("com.game.ipd.service.impl.IpdServiceImpl")
    private IpdService ipdService;

    @Override
    public void service(MockHttpServletRequest request, MockHttpServletResponse response) throws Exception {
        String token = request.getParameter("token");
        if (!StringUtils.hasText(token)) {
            return;
        }
        Account account = accountService.getAccountByToken(token);
        if (null == account) {
            LOGGER.error("===找不到该账号");
            System.err.println("===找不到该账号");
            return;
        }
        String result = ipdService.sendServerList(account);
        response.getWriter().write(result);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}
