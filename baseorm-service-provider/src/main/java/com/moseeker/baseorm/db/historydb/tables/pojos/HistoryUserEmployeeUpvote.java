/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 员工点赞历史记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryUserEmployeeUpvote implements Serializable {

    private static final long serialVersionUID = 1626950803;

    private Integer   id;
    private Integer   sender;
    private Integer   receiver;
    private Integer   companyId;
    private Timestamp upvoteTime;
    private Byte      cancel;
    private Timestamp cancelTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HistoryUserEmployeeUpvote() {}

    public HistoryUserEmployeeUpvote(HistoryUserEmployeeUpvote value) {
        this.id = value.id;
        this.sender = value.sender;
        this.receiver = value.receiver;
        this.companyId = value.companyId;
        this.upvoteTime = value.upvoteTime;
        this.cancel = value.cancel;
        this.cancelTime = value.cancelTime;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HistoryUserEmployeeUpvote(
        Integer   id,
        Integer   sender,
        Integer   receiver,
        Integer   companyId,
        Timestamp upvoteTime,
        Byte      cancel,
        Timestamp cancelTime,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.companyId = companyId;
        this.upvoteTime = upvoteTime;
        this.cancel = cancel;
        this.cancelTime = cancelTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSender() {
        return this.sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Timestamp getUpvoteTime() {
        return this.upvoteTime;
    }

    public void setUpvoteTime(Timestamp upvoteTime) {
        this.upvoteTime = upvoteTime;
    }

    public Byte getCancel() {
        return this.cancel;
    }

    public void setCancel(Byte cancel) {
        this.cancel = cancel;
    }

    public Timestamp getCancelTime() {
        return this.cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
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
        StringBuilder sb = new StringBuilder("HistoryUserEmployeeUpvote (");

        sb.append(id);
        sb.append(", ").append(sender);
        sb.append(", ").append(receiver);
        sb.append(", ").append(companyId);
        sb.append(", ").append(upvoteTime);
        sb.append(", ").append(cancel);
        sb.append(", ").append(cancelTime);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
