package com.game.ipd.service;

import com.game.ipd.entity.Account;

public interface AccountService {
    /**
     * 根据用户名查找账号
     * @param userName
     * @return
     */
    Account getAccountByName(String userName);

    /**
     * 新建账户
     * @param account
     */
    void createAccount(Account account);

    /**
     * 得到唯一用户账号id
     * @return
     */
    int getUniqueAcountId();

    /**
     * 创建token
     * @param account
     * @return
     */
    String createToken(Account account);

    /**
     * 根据token查找账号信息
     * @param token
     * @return
     */
    Account getAccountByToken(String token);

    /**
     * 保存账户信息
     * @param account
     */
    void updateAccountInfo(Account account);
}
