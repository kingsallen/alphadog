/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 智能画像日志表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogAiRecom implements Serializable {

    private static final long serialVersionUID = -32873689;

    private Integer   id;
    private Integer   userId;
    private Integer   companyId;
    private String    action;
    private Byte      type;
    private Timestamp sendTime;
    private String    mdCode;
    private String    algorithmName;
    private Integer   wxId;

    public LogAiRecom() {}

    public LogAiRecom(LogAiRecom value) {
        this.id = value.id;
        this.userId = value.userId;
        this.companyId = value.companyId;
        this.action = value.action;
        this.type = value.type;
        this.sendTime = value.sendTime;
        this.mdCode = value.mdCode;
        this.algorithmName = value.algorithmName;
        this.wxId = value.wxId;
    }

    public LogAiRecom(
        Integer   id,
        Integer   userId,
        Integer   companyId,
        String    action,
        Byte      type,
        Timestamp sendTime,
        String    mdCode,
        String    algorithmName,
        Integer   wxId
    ) {
        this.id = id;
        this.userId = userId;
        this.companyId = companyId;
        this.action = action;
        this.type = type;
        this.sendTime = sendTime;
        this.mdCode = mdCode;
        this.algorithmName = algorithmName;
        this.wxId = wxId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Timestamp getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getAlgorithmName() {
        return this.algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public Integer getWxId() {
        return this.wxId;
    }

    public void setWxId(Integer wxId) {
        this.wxId = wxId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("LogAiRecom (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(companyId);
        sb.append(", ").append(action);
        sb.append(", ").append(type);
        sb.append(", ").append(sendTime);
        sb.append(", ").append(mdCode);
        sb.append(", ").append(algorithmName);
        sb.append(", ").append(wxId);

        sb.append(")");
        return sb.toString();
    }
}
