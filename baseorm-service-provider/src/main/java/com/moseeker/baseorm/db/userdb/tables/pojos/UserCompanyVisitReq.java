/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * C端用户申请参观记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCompanyVisitReq implements Serializable {

    private static final long serialVersionUID = -238577152;

    private Integer   id;
    private Integer   companyId;
    private Integer   userId;
    private Integer   status;
    private Integer   source;
    private Timestamp createTime;
    private Timestamp updateTime;

    public UserCompanyVisitReq() {}

    public UserCompanyVisitReq(UserCompanyVisitReq value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.userId = value.userId;
        this.status = value.status;
        this.source = value.source;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public UserCompanyVisitReq(
        Integer   id,
        Integer   companyId,
        Integer   userId,
        Integer   status,
        Integer   source,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.userId = userId;
        this.status = status;
        this.source = source;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
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
        StringBuilder sb = new StringBuilder("UserCompanyVisitReq (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(userId);
        sb.append(", ").append(status);
        sb.append(", ").append(source);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
