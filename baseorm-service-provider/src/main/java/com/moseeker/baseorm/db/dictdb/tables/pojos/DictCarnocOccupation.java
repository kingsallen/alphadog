/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 民航招聘的职位职能表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictCarnocOccupation implements Serializable {

    private static final long serialVersionUID = 1445609748;

    private Integer   code;
    private Integer   parentId;
    private String    name;
    private String    codeOther;
    private Short     level;
    private Short     status;
    private Timestamp createTime;

    public DictCarnocOccupation() {}

    public DictCarnocOccupation(DictCarnocOccupation value) {
        this.code = value.code;
        this.parentId = value.parentId;
        this.name = value.name;
        this.codeOther = value.codeOther;
        this.level = value.level;
        this.status = value.status;
        this.createTime = value.createTime;
    }

    public DictCarnocOccupation(
        Integer   code,
        Integer   parentId,
        String    name,
        String    codeOther,
        Short     level,
        Short     status,
        Timestamp createTime
    ) {
        this.code = code;
        this.parentId = parentId;
        this.name = name;
        this.codeOther = codeOther;
        this.level = level;
        this.status = status;
        this.createTime = createTime;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeOther() {
        return this.codeOther;
    }

    public void setCodeOther(String codeOther) {
        this.codeOther = codeOther;
    }

    public Short getLevel() {
        return this.level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DictCarnocOccupation (");

        sb.append(code);
        sb.append(", ").append(parentId);
        sb.append(", ").append(name);
        sb.append(", ").append(codeOther);
        sb.append(", ").append(level);
        sb.append(", ").append(status);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
