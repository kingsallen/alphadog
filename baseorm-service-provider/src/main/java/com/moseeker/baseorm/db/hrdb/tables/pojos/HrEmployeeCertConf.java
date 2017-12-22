/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 部门员工配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCertConf implements Serializable {

    private static final long serialVersionUID = -1235493827;

    private Integer   id;
    private Integer   companyId;
    private Byte      isStrict;
    private String    emailSuffix;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte      disable;
    private Byte      bdAddGroup;
    private Integer   bdUseGroupId;
    private Byte      authMode;
    private String    authCode;
    private String    custom;
    private String    questions;
    private String    customHint;

    public HrEmployeeCertConf() {}

    public HrEmployeeCertConf(HrEmployeeCertConf value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.isStrict = value.isStrict;
        this.emailSuffix = value.emailSuffix;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.disable = value.disable;
        this.bdAddGroup = value.bdAddGroup;
        this.bdUseGroupId = value.bdUseGroupId;
        this.authMode = value.authMode;
        this.authCode = value.authCode;
        this.custom = value.custom;
        this.questions = value.questions;
        this.customHint = value.customHint;
    }

    public HrEmployeeCertConf(
        Integer   id,
        Integer   companyId,
        Byte      isStrict,
        String    emailSuffix,
        Timestamp createTime,
        Timestamp updateTime,
        Byte      disable,
        Byte      bdAddGroup,
        Integer   bdUseGroupId,
        Byte      authMode,
        String    authCode,
        String    custom,
        String    questions,
        String    customHint
    ) {
        this.id = id;
        this.companyId = companyId;
        this.isStrict = isStrict;
        this.emailSuffix = emailSuffix;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.disable = disable;
        this.bdAddGroup = bdAddGroup;
        this.bdUseGroupId = bdUseGroupId;
        this.authMode = authMode;
        this.authCode = authCode;
        this.custom = custom;
        this.questions = questions;
        this.customHint = customHint;
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

    public Byte getIsStrict() {
        return this.isStrict;
    }

    public void setIsStrict(Byte isStrict) {
        this.isStrict = isStrict;
    }

    public String getEmailSuffix() {
        return this.emailSuffix;
    }

    public void setEmailSuffix(String emailSuffix) {
        this.emailSuffix = emailSuffix;
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

    public Byte getDisable() {
        return this.disable;
    }

    public void setDisable(Byte disable) {
        this.disable = disable;
    }

    public Byte getBdAddGroup() {
        return this.bdAddGroup;
    }

    public void setBdAddGroup(Byte bdAddGroup) {
        this.bdAddGroup = bdAddGroup;
    }

    public Integer getBdUseGroupId() {
        return this.bdUseGroupId;
    }

    public void setBdUseGroupId(Integer bdUseGroupId) {
        this.bdUseGroupId = bdUseGroupId;
    }

    public Byte getAuthMode() {
        return this.authMode;
    }

    public void setAuthMode(Byte authMode) {
        this.authMode = authMode;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCustom() {
        return this.custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getQuestions() {
        return this.questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getCustomHint() {
        return this.customHint;
    }

    public void setCustomHint(String customHint) {
        this.customHint = customHint;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrEmployeeCertConf (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(isStrict);
        sb.append(", ").append(emailSuffix);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(disable);
        sb.append(", ").append(bdAddGroup);
        sb.append(", ").append(bdUseGroupId);
        sb.append(", ").append(authMode);
        sb.append(", ").append(authCode);
        sb.append(", ").append(custom);
        sb.append(", ").append(questions);
        sb.append(", ").append(customHint);

        sb.append(")");
        return sb.toString();
    }
}
