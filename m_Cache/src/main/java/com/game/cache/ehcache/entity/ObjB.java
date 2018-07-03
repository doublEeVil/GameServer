package com.game.cache.ehcache.entity;

import com.game.cache.ehcache.CacheConfig;

import java.io.Serializable;

@CacheConfig
public class ObjB implements Serializable{
    private long id;
    private String param;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
