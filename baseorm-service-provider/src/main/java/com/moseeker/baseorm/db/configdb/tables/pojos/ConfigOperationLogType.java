/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 操作记录类型配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigOperationLogType implements Serializable {

    private static final long serialVersionUID = -583005716;

    private Integer   id;
    private String    typeName;
    private Integer   parentId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public ConfigOperationLogType() {}

    public ConfigOperationLogType(ConfigOperationLogType value) {
        this.id = value.id;
        this.typeName = value.typeName;
        this.parentId = value.parentId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public ConfigOperationLogType(
        Integer   id,
        String    typeName,
        Integer   parentId,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.typeName = typeName;
        this.parentId = parentId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
        StringBuilder sb = new StringBuilder("ConfigOperationLogType (");

        sb.append(id);
        sb.append(", ").append(typeName);
        sb.append(", ").append(parentId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}