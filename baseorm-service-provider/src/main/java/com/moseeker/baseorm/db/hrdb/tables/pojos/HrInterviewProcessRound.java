/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 面试阶段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewProcessRound implements Serializable {

    private static final long serialVersionUID = 1398210372;

    private Integer   id;
    private String    roundName;
    private Integer   interviewProcessId;
    private Integer   disabled;
    private Integer   deleted;
    private Integer   roundOrder;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrInterviewProcessRound() {}

    public HrInterviewProcessRound(HrInterviewProcessRound value) {
        this.id = value.id;
        this.roundName = value.roundName;
        this.interviewProcessId = value.interviewProcessId;
        this.disabled = value.disabled;
        this.deleted = value.deleted;
        this.roundOrder = value.roundOrder;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrInterviewProcessRound(
        Integer   id,
        String    roundName,
        Integer   interviewProcessId,
        Integer   disabled,
        Integer   deleted,
        Integer   roundOrder,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.roundName = roundName;
        this.interviewProcessId = interviewProcessId;
        this.disabled = disabled;
        this.deleted = deleted;
        this.roundOrder = roundOrder;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoundName() {
        return this.roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public Integer getInterviewProcessId() {
        return this.interviewProcessId;
    }

    public void setInterviewProcessId(Integer interviewProcessId) {
        this.interviewProcessId = interviewProcessId;
    }

    public Integer getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getRoundOrder() {
        return this.roundOrder;
    }

    public void setRoundOrder(Integer roundOrder) {
        this.roundOrder = roundOrder;
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
        StringBuilder sb = new StringBuilder("HrInterviewProcessRound (");

        sb.append(id);
        sb.append(", ").append(roundName);
        sb.append(", ").append(interviewProcessId);
        sb.append(", ").append(disabled);
        sb.append(", ").append(deleted);
        sb.append(", ").append(roundOrder);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
