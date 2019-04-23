/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 账户变更记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketConsumingRecords implements Serializable {

    private static final long serialVersionUID = 2042713988;

    private Integer   id;
    private Integer   balanceId;
    private Integer   amount;
    private Byte      scene;
    private Integer   businessId;
    private String    billNo;
    private String    description;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   operationId;

    public RedpacketConsumingRecords() {}

    public RedpacketConsumingRecords(RedpacketConsumingRecords value) {
        this.id = value.id;
        this.balanceId = value.balanceId;
        this.amount = value.amount;
        this.scene = value.scene;
        this.businessId = value.businessId;
        this.billNo = value.billNo;
        this.description = value.description;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.operationId = value.operationId;
    }

    public RedpacketConsumingRecords(
        Integer   id,
        Integer   balanceId,
        Integer   amount,
        Byte      scene,
        Integer   businessId,
        String    billNo,
        String    description,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   operationId
    ) {
        this.id = id;
        this.balanceId = balanceId;
        this.amount = amount;
        this.scene = scene;
        this.businessId = businessId;
        this.billNo = billNo;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.operationId = operationId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalanceId() {
        return this.balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Byte getScene() {
        return this.scene;
    }

    public void setScene(Byte scene) {
        this.scene = scene;
    }

    public Integer getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBillNo() {
        return this.billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getOperationId() {
        return this.operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RedpacketConsumingRecords (");

        sb.append(id);
        sb.append(", ").append(balanceId);
        sb.append(", ").append(amount);
        sb.append(", ").append(scene);
        sb.append(", ").append(businessId);
        sb.append(", ").append(billNo);
        sb.append(", ").append(description);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(operationId);

        sb.append(")");
        return sb.toString();
    }
}