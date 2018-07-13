package com.game.ipd.service.impl;

import com.game.cache.mysql.service.GenericMySqlService;
import com.game.cache.redis.RedisService;
import com.game.ipd.entity.Account;
import com.game.ipd.service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("com.game.ipd.service.impl.AccountServiceImpl")
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class);
    private final String       TOKEN_ACCOUNT_ID = "TOKEN:ACCOUNT:ID:";
    private final String       ACCOUNT_ID_TOKEN = "ACCOUNT:ID:TOKEN:";

    @Autowired
    @Qualifier("com.game.cache.mysql.service.impl.GenericMysqlServiceImpl")
    private GenericMySqlService genericMysqlService;
    @Autowired
    @Qualifier("com.game.cache.redis.impl.SingleNodeRedisService")
    private RedisService redisService;

    @Override
    public Account getAccountByName(String userName) {
        return genericMysqlService.getByHql(" from Account a where a.username = ?", userName);
    }

    @Override
    public void createAccount(Account account) {
        genericMysqlService.save(account);
    }

    @Override
    public int getUniqueAcountId() {
        return redisService.getTableID(Account.class);
    }

    @Override
    public String createToken(Account account) {
        // String old_token = redisService.getValue(TOKEN_ACCOUNT_ID + account.getId());
        String token = UUID.randomUUID().toString();
        redisService.setVal(ACCOUNT_ID_TOKEN + account.getId(), token);
        redisService.expire(ACCOUNT_ID_TOKEN + account.getId(), 60 * 5);
        redisService.setVal(TOKEN_ACCOUNT_ID + token, account.getId() + "");
        redisService.expire(TOKEN_ACCOUNT_ID + token, 60 * 5);
        return token;
    }

    @Override
    public Account getAccountByToken(String token) {
        int accountId = redisService.getInt(TOKEN_ACCOUNT_ID + token);
        return genericMysqlService.get(Account.class, accountId);
    }

    @Override
    public void updateAccountInfo(Account account) {
        genericMysqlService.update(account);
    }
}
