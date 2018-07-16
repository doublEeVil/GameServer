package com.game.cache.mysql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 主键自增类型的实体
 * 和BaseEntity唯一的区别就是主键生成策略不同
 */
@MappedSuperclass
public class BaseIdAutoIncEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    protected int id;
    protected long createDate;
    protected long updateDate;
    @Transient
    private int updateFlag; //记录更新次数

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "create_date", nullable = false)
    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Column(name = "update_date", nullable = false)
    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    @Transient
    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    @Transient
    public void addUpdateFlag() {
        this.updateFlag++;
    }
}
