package com.game.cache.test.entity;

import com.game.cache.ehcache.CacheConfig;
import com.game.cache.mysql.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tab_test_entity_a")
@CacheConfig
public class TestEntityA extends BaseEntity{
    private int age;

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
