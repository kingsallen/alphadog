/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 简历模板库
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysCvTpl implements Serializable {

    private static final long serialVersionUID = 1183551365;

    private Integer   id;
    private String    fieldName;
    private String    fieldTitle;
    private Integer   fieldType;
    private String    fieldValue;
    private Integer   priority;
    private Integer   isBasic;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   disable;
    private Integer   companyId;
    private Integer   required;
    private String    fieldDescription;
    private String    map;

    public ConfigSysCvTpl() {}

    public ConfigSysCvTpl(ConfigSysCvTpl value) {
        this.id = value.id;
        this.fieldName = value.fieldName;
        this.fieldTitle = value.fieldTitle;
        this.fieldType = value.fieldType;
        this.fieldValue = value.fieldValue;
        this.priority = value.priority;
        this.isBasic = value.isBasic;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.disable = value.disable;
        this.companyId = value.companyId;
        this.required = value.required;
        this.fieldDescription = value.fieldDescription;
        this.map = value.map;
    }

    public ConfigSysCvTpl(
        Integer   id,
        String    fieldName,
        String    fieldTitle,
        Integer   fieldType,
        String    fieldValue,
        Integer   priority,
        Integer   isBasic,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   disable,
        Integer   companyId,
        Integer   required,
        String    fieldDescription,
        String    map
    ) {
        this.id = id;
        this.fieldName = fieldName;
        this.fieldTitle = fieldTitle;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
        this.priority = priority;
        this.isBasic = isBasic;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.disable = disable;
        this.companyId = companyId;
        this.required = required;
        this.fieldDescription = fieldDescription;
        this.map = map;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public Integer getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getIsBasic() {
        return this.isBasic;
    }

    public void setIsBasic(Integer isBasic) {
        this.isBasic = isBasic;
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

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRequired() {
        return this.required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public String getFieldDescription() {
        return this.fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getMap() {
        return this.map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ConfigSysCvTpl (");

        sb.append(id);
        sb.append(", ").append(fieldName);
        sb.append(", ").append(fieldTitle);
        sb.append(", ").append(fieldType);
        sb.append(", ").append(fieldValue);
        sb.append(", ").append(priority);
        sb.append(", ").append(isBasic);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(disable);
        sb.append(", ").append(companyId);
        sb.append(", ").append(required);
        sb.append(", ").append(fieldDescription);
        sb.append(", ").append(map);

        sb.append(")");
        return sb.toString();
    }
}
