/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 资源集合表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrResource implements Serializable {

    private static final long serialVersionUID = 542158293;

    private Integer   id;
    private String    resUrl;
    private Integer   resType;
    private String    remark;
    private Integer   companyId;
    private String    title;
    private Integer   disable;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String    cover;

    public HrResource() {}

    public HrResource(HrResource value) {
        this.id = value.id;
        this.resUrl = value.resUrl;
        this.resType = value.resType;
        this.remark = value.remark;
        this.companyId = value.companyId;
        this.title = value.title;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.cover = value.cover;
    }

    public HrResource(
        Integer   id,
        String    resUrl,
        Integer   resType,
        String    remark,
        Integer   companyId,
        String    title,
        Integer   disable,
        Timestamp createTime,
        Timestamp updateTime,
        String    cover
    ) {
        this.id = id;
        this.resUrl = resUrl;
        this.resType = resType;
        this.remark = remark;
        this.companyId = companyId;
        this.title = title;
        this.disable = disable;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.cover = cover;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResUrl() {
        return this.resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public Integer getResType() {
        return this.resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
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

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrResource (");

        sb.append(id);
        sb.append(", ").append(resUrl);
        sb.append(", ").append(resType);
        sb.append(", ").append(remark);
        sb.append(", ").append(companyId);
        sb.append(", ").append(title);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(cover);

        sb.append(")");
        return sb.toString();
    }
}
