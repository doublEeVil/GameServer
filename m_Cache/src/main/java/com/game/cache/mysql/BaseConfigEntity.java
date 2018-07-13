package com.game.cache.mysql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 配置数据
 * 一般由策划配置
 * 里面数据不可变
 */
@MappedSuperclass
public class BaseConfigEntity implements Serializable {
    private int id;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
