package com.game.cache.test.entity;

import com.game.cache.ehcache.CacheConfig;
import com.game.cache.mysql.BaseConfigEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
@CacheConfig
public class TestConfigEntityA extends BaseConfigEntity {
    private String addr;

    @Column(name = "addr")
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
