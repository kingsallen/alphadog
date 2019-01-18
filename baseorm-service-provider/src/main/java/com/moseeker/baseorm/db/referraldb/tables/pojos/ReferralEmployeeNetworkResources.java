/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 员工的雷达人脉top
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralEmployeeNetworkResources implements Serializable {

    private static final long serialVersionUID = -146032775;

    private Integer   id;
    private Integer   postUserId;
    private Integer   presenteeUserId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte      disable;

    public ReferralEmployeeNetworkResources() {}

    public ReferralEmployeeNetworkResources(ReferralEmployeeNetworkResources value) {
        this.id = value.id;
        this.postUserId = value.postUserId;
        this.presenteeUserId = value.presenteeUserId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.disable = value.disable;
    }

    public ReferralEmployeeNetworkResources(
        Integer   id,
        Integer   postUserId,
        Integer   presenteeUserId,
        Timestamp createTime,
        Timestamp updateTime,
        Byte      disable
    ) {
        this.id = id;
        this.postUserId = postUserId;
        this.presenteeUserId = presenteeUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.disable = disable;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostUserId() {
        return this.postUserId;
    }

    public void setPostUserId(Integer postUserId) {
        this.postUserId = postUserId;
    }

    public Integer getPresenteeUserId() {
        return this.presenteeUserId;
    }

    public void setPresenteeUserId(Integer presenteeUserId) {
        this.presenteeUserId = presenteeUserId;
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

    public Byte getDisable() {
        return this.disable;
    }

    public void setDisable(Byte disable) {
        this.disable = disable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ReferralEmployeeNetworkResources (");

        sb.append(id);
        sb.append(", ").append(postUserId);
        sb.append(", ").append(presenteeUserId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(disable);

        sb.append(")");
        return sb.toString();
    }
}
