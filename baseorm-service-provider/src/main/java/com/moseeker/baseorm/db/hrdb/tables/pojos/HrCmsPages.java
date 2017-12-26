/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 微信端新jd配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCmsPages implements Serializable {

    private static final long serialVersionUID = 614586846;

    private Integer   id;
    private Integer   configId;
    private Integer   type;
    private Integer   disable;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrCmsPages() {}

    public HrCmsPages(HrCmsPages value) {
        this.id = value.id;
        this.configId = value.configId;
        this.type = value.type;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrCmsPages(
        Integer   id,
        Integer   configId,
        Integer   type,
        Integer   disable,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.configId = configId;
        this.type = type;
        this.disable = disable;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return this.configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrCmsPages (");

        sb.append(id);
        sb.append(", ").append(configId);
        sb.append(", ").append(type);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}