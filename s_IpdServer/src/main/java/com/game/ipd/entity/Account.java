package com.game.ipd.entity;

import com.game.cache.mysql.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_account")
public class Account extends BaseEntity{
    public static final int ACCOUNT_TYPE_NORMAL = 0; //常规用户
    public static final int ACCOUNT_TYPE_TEST = 1; //测试用户
    private String username;
    private String password;
    private int channel;
    private String loginTimes;// 各服务登陆时间
    private String email;//
    private String tel;
    private int type; // 账号类型 0:常规用户 1:测试用户

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "channel", nullable = false)
    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Column(name = "login_times", nullable = false)
    public String getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(String loginTimes) {
        this.loginTimes = loginTimes;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "type", columnDefinition = "INT DEFAULT 0")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
