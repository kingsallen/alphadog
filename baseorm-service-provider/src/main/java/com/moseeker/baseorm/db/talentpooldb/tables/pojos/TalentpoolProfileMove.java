/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMove implements Serializable {

    private static final long serialVersionUID = 966956075;

    private Integer   id;
    private Integer   hrId;
    private Byte      channel;
    private Date      startDate;
    private Date      endDate;
    private Timestamp createTime;
    private Timestamp updateTime;

    public TalentpoolProfileMove() {}

    public TalentpoolProfileMove(TalentpoolProfileMove value) {
        this.id = value.id;
        this.hrId = value.hrId;
        this.channel = value.channel;
        this.startDate = value.startDate;
        this.endDate = value.endDate;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public TalentpoolProfileMove(
        Integer   id,
        Integer   hrId,
        Byte      channel,
        Date      startDate,
        Date      endDate,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.hrId = hrId;
        this.channel = channel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHrId() {
        return this.hrId;
    }

    public void setHrId(Integer hrId) {
        this.hrId = hrId;
    }

    public Byte getChannel() {
        return this.channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        StringBuilder sb = new StringBuilder("TalentpoolProfileMove (");

        sb.append(id);
        sb.append(", ").append(hrId);
        sb.append(", ").append(channel);
        sb.append(", ").append(startDate);
        sb.append(", ").append(endDate);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
