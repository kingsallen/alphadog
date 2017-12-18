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
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccount implements Serializable {

    private static final long serialVersionUID = 1810656153;

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
        String    ext
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

        sb.append(")");
        return sb.toString();
    }
}
