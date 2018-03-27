/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictLiepinOccupation implements Serializable {

    private static final long serialVersionUID = 2028058517;

    private Integer   id;
    private Integer   code;
    private Integer   parentId;
    private String    otherCode;
    private Integer   level;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   status;
    private String    name;

    public DictLiepinOccupation() {}

    public DictLiepinOccupation(DictLiepinOccupation value) {
        this.id = value.id;
        this.code = value.code;
        this.parentId = value.parentId;
        this.otherCode = value.otherCode;
        this.level = value.level;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.status = value.status;
        this.name = value.name;
    }

    public DictLiepinOccupation(
        Integer   id,
        Integer   code,
        Integer   parentId,
        String    otherCode,
        Integer   level,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   status,
        String    name
    ) {
        this.id = id;
        this.code = code;
        this.parentId = parentId;
        this.otherCode = otherCode;
        this.level = level;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOtherCode() {
        return this.otherCode;
    }

    public void setOtherCode(String otherCode) {
        this.otherCode = otherCode;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DictLiepinOccupation (");

        sb.append(id);
        sb.append(", ").append(code);
        sb.append(", ").append(parentId);
        sb.append(", ").append(otherCode);
        sb.append(", ").append(level);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(status);
        sb.append(", ").append(name);

        sb.append(")");
        return sb.toString();
    }
}
