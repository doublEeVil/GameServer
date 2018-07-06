package com.game.cache.test.entity;

import com.game.cache.ehcache.CacheConfig;
import com.game.cache.mysql.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tab_test_entity_b")
@CacheConfig
public class TestEntityB extends BaseEntity{
    private String name;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
