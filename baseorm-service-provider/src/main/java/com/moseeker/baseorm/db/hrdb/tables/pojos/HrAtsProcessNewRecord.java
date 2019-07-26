/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import javax.annotation.Generated;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * ats招聘流程进度流水表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsProcessNewRecord implements Serializable {

    private static final long serialVersionUID = -536565959;

    private Integer   id;
    private Integer   appId;
    private Integer   processId;
    private Integer   companyId;
    private Integer   hrId;
    private Integer   type;
    private String    content;
    private Integer   lastProcessId;
    private Integer   eventId;
    private Timestamp createTime;

    public HrAtsProcessNewRecord() {}

    public HrAtsProcessNewRecord(HrAtsProcessNewRecord value) {
        this.id = value.id;
        this.appId = value.appId;
        this.processId = value.processId;
        this.companyId = value.companyId;
        this.hrId = value.hrId;
        this.type = value.type;
        this.content = value.content;
        this.lastProcessId = value.lastProcessId;
        this.eventId = value.eventId;
        this.createTime = value.createTime;
    }

    public HrAtsProcessNewRecord(
        Integer   id,
        Integer   appId,
        Integer   processId,
        Integer   companyId,
        Integer   hrId,
        Integer   type,
        String    content,
        Integer   lastProcessId,
        Integer   eventId,
        Timestamp createTime
    ) {
        this.id = id;
        this.appId = appId;
        this.processId = processId;
        this.companyId = companyId;
        this.hrId = hrId;
        this.type = type;
        this.content = content;
        this.lastProcessId = lastProcessId;
        this.eventId = eventId;
        this.createTime = createTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppId() {
        return this.appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getProcessId() {
        return this.processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getHrId() {
        return this.hrId;
    }

    public void setHrId(Integer hrId) {
        this.hrId = hrId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLastProcessId() {
        return this.lastProcessId;
    }

    public void setLastProcessId(Integer lastProcessId) {
        this.lastProcessId = lastProcessId;
    }

    public Integer getEventId() {
        return this.eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrAtsProcessNewRecord (");

        sb.append(id);
        sb.append(", ").append(appId);
        sb.append(", ").append(processId);
        sb.append(", ").append(companyId);
        sb.append(", ").append(hrId);
        sb.append(", ").append(type);
        sb.append(", ").append(content);
        sb.append(", ").append(lastProcessId);
        sb.append(", ").append(eventId);
        sb.append(", ").append(createTime);

        sb.append(")");
        return sb.toString();
    }
}
