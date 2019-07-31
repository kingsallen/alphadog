/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 企业手动标签
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolCompanyManualTag implements Serializable {

    private static final long serialVersionUID = -341827734;

    private Integer   id;
    private Integer   companyId;
    private String    name;
    private String    color;
    private Integer   talentNum;
    private Timestamp createTime;
    private Timestamp updateTime;

    public TalentpoolCompanyManualTag() {}

    public TalentpoolCompanyManualTag(TalentpoolCompanyManualTag value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.name = value.name;
        this.color = value.color;
        this.talentNum = value.talentNum;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public TalentpoolCompanyManualTag(
        Integer   id,
        Integer   companyId,
        String    name,
        String    color,
        Integer   talentNum,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.color = color;
        this.talentNum = talentNum;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getTalentNum() {
        return this.talentNum;
    }

    public void setTalentNum(Integer talentNum) {
        this.talentNum = talentNum;
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
        StringBuilder sb = new StringBuilder("TalentpoolCompanyManualTag (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(name);
        sb.append(", ").append(color);
        sb.append(", ").append(talentNum);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
