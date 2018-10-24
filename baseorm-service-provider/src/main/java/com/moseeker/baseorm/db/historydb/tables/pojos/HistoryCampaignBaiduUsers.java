/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 百度用户关联表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryCampaignBaiduUsers implements Serializable {

    private static final long serialVersionUID = -1928806101;

    private Integer   id;
    private Integer   userId;
    private Long      uid;
    private Timestamp createTime;

    public HistoryCampaignBaiduUsers() {}

    public HistoryCampaignBaiduUsers(HistoryCampaignBaiduUsers value) {
        this.id = value.id;
        this.userId = value.userId;
        this.uid = value.uid;
        this.createTime = value.createTime;
    }

    public HistoryCampaignBaiduUsers(
        Integer   id,
        Integer   userId,
        Long      uid,
        Timestamp createTime
    ) {
        this.id = id;
        this.userId = userId;
        this.uid = uid;
        this.createTime = createTime;
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

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HistoryCampaignBaiduUsers (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(uid);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
