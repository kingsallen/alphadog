/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 用户求职意向-已弃用，老微信中的账号设置-》我的兴趣。
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserIntention implements Serializable {

    private static final long serialVersionUID = 360128484;

    private Integer   sysuserId;
    private String    intention;
    private Timestamp createTime;
    private Timestamp updateTime;

    public UserIntention() {}

    public UserIntention(UserIntention value) {
        this.sysuserId = value.sysuserId;
        this.intention = value.intention;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public UserIntention(
        Integer   sysuserId,
        String    intention,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.sysuserId = sysuserId;
        this.intention = intention;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getSysuserId() {
        return this.sysuserId;
    }

    public void setSysuserId(Integer sysuserId) {
        this.sysuserId = sysuserId;
    }

    public String getIntention() {
        return this.intention;
    }

    public void setIntention(String intention) {
        this.intention = intention;
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
        StringBuilder sb = new StringBuilder("UserIntention (");

        sb.append(sysuserId);
        sb.append(", ").append(intention);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
