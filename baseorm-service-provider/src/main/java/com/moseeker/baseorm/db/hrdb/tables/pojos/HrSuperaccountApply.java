/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 升级超级账号申请表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrSuperaccountApply implements Serializable {

    private static final long serialVersionUID = 1254136851;

    private Integer   id;
    private Integer   companyId;
    private byte[]    licence;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   operate;
    private Integer   status;
    private String    message;
    private String    childCompanyId;
    private Timestamp migrateTime;
    private Integer   accountLimit;

    public HrSuperaccountApply() {}

    public HrSuperaccountApply(HrSuperaccountApply value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.licence = value.licence;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.operate = value.operate;
        this.status = value.status;
        this.message = value.message;
        this.childCompanyId = value.childCompanyId;
        this.migrateTime = value.migrateTime;
        this.accountLimit = value.accountLimit;
    }

    public HrSuperaccountApply(
        Integer   id,
        Integer   companyId,
        byte[]    licence,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   operate,
        Integer   status,
        String    message,
        String    childCompanyId,
        Timestamp migrateTime,
        Integer   accountLimit
    ) {
        this.id = id;
        this.companyId = companyId;
        this.licence = licence;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.operate = operate;
        this.status = status;
        this.message = message;
        this.childCompanyId = childCompanyId;
        this.migrateTime = migrateTime;
        this.accountLimit = accountLimit;
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

    public byte[] getLicence() {
        return this.licence;
    }

    public void setLicence(byte... licence) {
        this.licence = licence;
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

    public Integer getOperate() {
        return this.operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChildCompanyId() {
        return this.childCompanyId;
    }

    public void setChildCompanyId(String childCompanyId) {
        this.childCompanyId = childCompanyId;
    }

    public Timestamp getMigrateTime() {
        return this.migrateTime;
    }

    public void setMigrateTime(Timestamp migrateTime) {
        this.migrateTime = migrateTime;
    }

    public Integer getAccountLimit() {
        return this.accountLimit;
    }

    public void setAccountLimit(Integer accountLimit) {
        this.accountLimit = accountLimit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrSuperaccountApply (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append("[binary...]");
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(operate);
        sb.append(", ").append(status);
        sb.append(", ").append(message);
        sb.append(", ").append(childCompanyId);
        sb.append(", ").append(migrateTime);
        sb.append(", ").append(accountLimit);

        sb.append(")");
        return sb.toString();
    }
}
