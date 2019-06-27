/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 第三方渠道帐号
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccount implements Serializable {

    private static final long serialVersionUID = -648210934;

    private Integer   id;
    private Short     channel;
    private String    username;
    private String    password;
    private Short     binding;
    private Integer   companyId;
    private Integer   remainNum;
    private Timestamp syncTime;
    private Timestamp updateTime;
    private Timestamp createTime;
    private Integer   remainProfileNum;
    private String    errorMessage;
    private String    ext;
    private String    ext2;
    private Byte      syncRequireCompany;
    private Byte      syncRequireDepartment;
    private Integer   templateSender;

    public HrThirdPartyAccount() {}

    public HrThirdPartyAccount(HrThirdPartyAccount value) {
        this.id = value.id;
        this.channel = value.channel;
        this.username = value.username;
        this.password = value.password;
        this.binding = value.binding;
        this.companyId = value.companyId;
        this.remainNum = value.remainNum;
        this.syncTime = value.syncTime;
        this.updateTime = value.updateTime;
        this.createTime = value.createTime;
        this.remainProfileNum = value.remainProfileNum;
        this.errorMessage = value.errorMessage;
        this.ext = value.ext;
        this.ext2 = value.ext2;
        this.syncRequireCompany = value.syncRequireCompany;
        this.syncRequireDepartment = value.syncRequireDepartment;
        this.templateSender = value.templateSender;
    }

    public HrThirdPartyAccount(
        Integer   id,
        Short     channel,
        String    username,
        String    password,
        Short     binding,
        Integer   companyId,
        Integer   remainNum,
        Timestamp syncTime,
        Timestamp updateTime,
        Timestamp createTime,
        Integer   remainProfileNum,
        String    errorMessage,
        String    ext,
        String    ext2,
        Byte      syncRequireCompany,
        Byte      syncRequireDepartment,
        Integer   templateSender
    ) {
        this.id = id;
        this.channel = channel;
        this.username = username;
        this.password = password;
        this.binding = binding;
        this.companyId = companyId;
        this.remainNum = remainNum;
        this.syncTime = syncTime;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.remainProfileNum = remainProfileNum;
        this.errorMessage = errorMessage;
        this.ext = ext;
        this.ext2 = ext2;
        this.syncRequireCompany = syncRequireCompany;
        this.syncRequireDepartment = syncRequireDepartment;
        this.templateSender = templateSender;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getChannel() {
        return this.channel;
    }

    public void setChannel(Short channel) {
        this.channel = channel;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getBinding() {
        return this.binding;
    }

    public void setBinding(Short binding) {
        this.binding = binding;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRemainNum() {
        return this.remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    public Timestamp getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(Timestamp syncTime) {
        this.syncTime = syncTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getRemainProfileNum() {
        return this.remainProfileNum;
    }

    public void setRemainProfileNum(Integer remainProfileNum) {
        this.remainProfileNum = remainProfileNum;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExt2() {
        return this.ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public Byte getSyncRequireCompany() {
        return this.syncRequireCompany;
    }

    public void setSyncRequireCompany(Byte syncRequireCompany) {
        this.syncRequireCompany = syncRequireCompany;
    }

    public Byte getSyncRequireDepartment() {
        return this.syncRequireDepartment;
    }

    public void setSyncRequireDepartment(Byte syncRequireDepartment) {
        this.syncRequireDepartment = syncRequireDepartment;
    }

    public Integer getTemplateSender() {
        return this.templateSender;
    }

    public void setTemplateSender(Integer templateSender) {
        this.templateSender = templateSender;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrThirdPartyAccount (");

        sb.append(id);
        sb.append(", ").append(channel);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(binding);
        sb.append(", ").append(companyId);
        sb.append(", ").append(remainNum);
        sb.append(", ").append(syncTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(createTime);
        sb.append(", ").append(remainProfileNum);
        sb.append(", ").append(errorMessage);
        sb.append(", ").append(ext);
        sb.append(", ").append(ext2);
        sb.append(", ").append(syncRequireCompany);
        sb.append(", ").append(syncRequireDepartment);
        sb.append(", ").append(templateSender);

        sb.append(")");
        return sb.toString();
    }
}
