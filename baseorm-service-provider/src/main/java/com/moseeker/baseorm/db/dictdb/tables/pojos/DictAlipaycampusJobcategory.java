/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * dict_alipaycampus_jobcategory[alipay校园招聘-职位类别]
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictAlipaycampusJobcategory implements Serializable {

    private static final long serialVersionUID = -1060115190;

    private Integer   id;
    private String    code;
    private String    parentCode;
    private String    name;
    private Integer   level;
    private Integer   isHot;
    private Integer   sort;
    private Integer   status;
    private Timestamp createTime;
    private Timestamp updateTime;

    public DictAlipaycampusJobcategory() {}

    public DictAlipaycampusJobcategory(DictAlipaycampusJobcategory value) {
        this.id = value.id;
        this.code = value.code;
        this.parentCode = value.parentCode;
        this.name = value.name;
        this.level = value.level;
        this.isHot = value.isHot;
        this.sort = value.sort;
        this.status = value.status;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public DictAlipaycampusJobcategory(
        Integer   id,
        String    code,
        String    parentCode,
        String    name,
        Integer   level,
        Integer   isHot,
        Integer   sort,
        Integer   status,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.code = code;
        this.parentCode = parentCode;
        this.name = name;
        this.level = level;
        this.isHot = isHot;
        this.sort = sort;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsHot() {
        return this.isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        StringBuilder sb = new StringBuilder("DictAlipaycampusJobcategory (");

        sb.append(id);
        sb.append(", ").append(code);
        sb.append(", ").append(parentCode);
        sb.append(", ").append(name);
        sb.append(", ").append(level);
        sb.append(", ").append(isHot);
        sb.append(", ").append(sort);
        sb.append(", ").append(status);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
