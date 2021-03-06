/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAccountApplicationNotify implements Serializable {

    private static final long serialVersionUID = -1188316778;

    private Integer   id;
    private Integer   hrAccountId;
    private Byte      flag;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrAccountApplicationNotify() {}

    public HrAccountApplicationNotify(HrAccountApplicationNotify value) {
        this.id = value.id;
        this.hrAccountId = value.hrAccountId;
        this.flag = value.flag;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrAccountApplicationNotify(
        Integer   id,
        Integer   hrAccountId,
        Byte      flag,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.hrAccountId = hrAccountId;
        this.flag = flag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHrAccountId() {
        return this.hrAccountId;
    }

    public void setHrAccountId(Integer hrAccountId) {
        this.hrAccountId = hrAccountId;
    }

    public Byte getFlag() {
        return this.flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrAccountApplicationNotify (");

        sb.append(id);
        sb.append(", ").append(hrAccountId);
        sb.append(", ").append(flag);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
