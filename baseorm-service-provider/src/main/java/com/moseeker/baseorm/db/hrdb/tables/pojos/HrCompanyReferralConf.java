/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 公司内推配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyReferralConf implements Serializable {

    private static final long serialVersionUID = 766359694;

    private Integer   id;
    private Integer   companyId;
    private String    link;
    private String    text;
    private Byte      priority;
    private Timestamp textUpdateTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrCompanyReferralConf() {}

    public HrCompanyReferralConf(HrCompanyReferralConf value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.link = value.link;
        this.text = value.text;
        this.priority = value.priority;
        this.textUpdateTime = value.textUpdateTime;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrCompanyReferralConf(
        Integer   id,
        Integer   companyId,
        String    link,
        String    text,
        Byte      priority,
        Timestamp textUpdateTime,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.link = link;
        this.text = text;
        this.priority = priority;
        this.textUpdateTime = textUpdateTime;
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

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Byte getPriority() {
        return this.priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Timestamp getTextUpdateTime() {
        return this.textUpdateTime;
    }

    public void setTextUpdateTime(Timestamp textUpdateTime) {
        this.textUpdateTime = textUpdateTime;
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
        StringBuilder sb = new StringBuilder("HrCompanyReferralConf (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(link);
        sb.append(", ").append(text);
        sb.append(", ").append(priority);
        sb.append(", ").append(textUpdateTime);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
