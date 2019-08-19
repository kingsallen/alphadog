/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 简历池表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfilePool implements Serializable {

    private static final long serialVersionUID = 1210932808;

    private Integer   id;
    private Integer   companyId;
    private Integer   parentId;
    private String    profilePoolName;
    private Timestamp createTime;
    private Timestamp updateTime;

    public TalentpoolProfilePool() {}

    public TalentpoolProfilePool(TalentpoolProfilePool value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.parentId = value.parentId;
        this.profilePoolName = value.profilePoolName;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public TalentpoolProfilePool(
        Integer   id,
        Integer   companyId,
        Integer   parentId,
        String    profilePoolName,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.parentId = parentId;
        this.profilePoolName = profilePoolName;
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

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getProfilePoolName() {
        return this.profilePoolName;
    }

    public void setProfilePoolName(String profilePoolName) {
        this.profilePoolName = profilePoolName;
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
        StringBuilder sb = new StringBuilder("TalentpoolProfilePool (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(parentId);
        sb.append(", ").append(profilePoolName);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
