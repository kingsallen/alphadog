/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * ats流程企业端配置搭配表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsProcessCompanyItem implements Serializable {

    private static final long serialVersionUID = 469372798;

    private Integer   id;
    private Integer   parentId;
    private Integer   itemId;
    private Integer   itemOrder;
    private Integer   disable;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrAtsProcessCompanyItem() {}

    public HrAtsProcessCompanyItem(HrAtsProcessCompanyItem value) {
        this.id = value.id;
        this.parentId = value.parentId;
        this.itemId = value.itemId;
        this.itemOrder = value.itemOrder;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrAtsProcessCompanyItem(
        Integer   id,
        Integer   parentId,
        Integer   itemId,
        Integer   itemOrder,
        Integer   disable,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.parentId = parentId;
        this.itemId = itemId;
        this.itemOrder = itemOrder;
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

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
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
        StringBuilder sb = new StringBuilder("HrAtsProcessCompanyItem (");

        sb.append(id);
        sb.append(", ").append(parentId);
        sb.append(", ").append(itemId);
        sb.append(", ").append(itemOrder);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
