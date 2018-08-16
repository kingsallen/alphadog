/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 消息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConsistencyMessage implements Serializable {

    private static final long serialVersionUID = -2055702961;

    private String    messageId;
    private String    name;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   version;
    private Byte      retried;
    private Timestamp lastRetryTime;
    private String    param;
    private Byte      finish;

    public ConsistencyMessage() {}

    public ConsistencyMessage(ConsistencyMessage value) {
        this.messageId = value.messageId;
        this.name = value.name;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.version = value.version;
        this.retried = value.retried;
        this.lastRetryTime = value.lastRetryTime;
        this.param = value.param;
        this.finish = value.finish;
    }

    public ConsistencyMessage(
        String    messageId,
        String    name,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   version,
        Byte      retried,
        Timestamp lastRetryTime,
        String    param,
        Byte      finish
    ) {
        this.messageId = messageId;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.version = version;
        this.retried = retried;
        this.lastRetryTime = lastRetryTime;
        this.param = param;
        this.finish = finish;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Byte getRetried() {
        return this.retried;
    }

    public void setRetried(Byte retried) {
        this.retried = retried;
    }

    public Timestamp getLastRetryTime() {
        return this.lastRetryTime;
    }

    public void setLastRetryTime(Timestamp lastRetryTime) {
        this.lastRetryTime = lastRetryTime;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Byte getFinish() {
        return this.finish;
    }

    public void setFinish(Byte finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ConsistencyMessage (");

        sb.append(messageId);
        sb.append(", ").append(name);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(version);
        sb.append(", ").append(retried);
        sb.append(", ").append(lastRetryTime);
        sb.append(", ").append(param);
        sb.append(", ").append(finish);

        sb.append(")");
        return sb.toString();
    }
}
