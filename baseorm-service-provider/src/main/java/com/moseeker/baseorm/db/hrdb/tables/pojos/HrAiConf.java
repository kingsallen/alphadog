/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 人工智能系统配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAiConf implements Serializable {

    private static final long serialVersionUID = -440135110;

    private Integer   id;
    private Integer   companyId;
    private Byte      followerSwitch;
    private Double    followerPercent;
    private Byte      employeeSwitch;
    private Double    employeePercent;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrAiConf() {}

    public HrAiConf(HrAiConf value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.followerSwitch = value.followerSwitch;
        this.followerPercent = value.followerPercent;
        this.employeeSwitch = value.employeeSwitch;
        this.employeePercent = value.employeePercent;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrAiConf(
        Integer   id,
        Integer   companyId,
        Byte      followerSwitch,
        Double    followerPercent,
        Byte      employeeSwitch,
        Double    employeePercent,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.followerSwitch = followerSwitch;
        this.followerPercent = followerPercent;
        this.employeeSwitch = employeeSwitch;
        this.employeePercent = employeePercent;
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

    public Byte getFollowerSwitch() {
        return this.followerSwitch;
    }

    public void setFollowerSwitch(Byte followerSwitch) {
        this.followerSwitch = followerSwitch;
    }

    public Double getFollowerPercent() {
        return this.followerPercent;
    }

    public void setFollowerPercent(Double followerPercent) {
        this.followerPercent = followerPercent;
    }

    public Byte getEmployeeSwitch() {
        return this.employeeSwitch;
    }

    public void setEmployeeSwitch(Byte employeeSwitch) {
        this.employeeSwitch = employeeSwitch;
    }

    public Double getEmployeePercent() {
        return this.employeePercent;
    }

    public void setEmployeePercent(Double employeePercent) {
        this.employeePercent = employeePercent;
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
        StringBuilder sb = new StringBuilder("HrAiConf (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(followerSwitch);
        sb.append(", ").append(followerPercent);
        sb.append(", ").append(employeeSwitch);
        sb.append(", ").append(employeePercent);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
