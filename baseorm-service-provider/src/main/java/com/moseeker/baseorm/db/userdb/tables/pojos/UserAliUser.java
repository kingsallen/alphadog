/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 阿里用户信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAliUser implements Serializable {

    private static final long serialVersionUID = 1079444792;

    private Integer   id;
    private Integer   userId;
    private String    uid;
    private Timestamp createTime;
    private Timestamp updateTime;

    public UserAliUser() {}

    public UserAliUser(UserAliUser value) {
        this.id = value.id;
        this.userId = value.userId;
        this.uid = value.uid;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public UserAliUser(
        Integer   id,
        Integer   userId,
        String    uid,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.userId = userId;
        this.uid = uid;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        StringBuilder sb = new StringBuilder("UserAliUser (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(uid);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
