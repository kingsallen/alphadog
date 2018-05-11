/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * hr每日登陆/使用统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogHrloginRecord implements Serializable {

    private static final long serialVersionUID = 553633559;

    private Integer   id;
    private Integer   hrId;
    private Date      loginDate;
    private Timestamp createTime;
    private Timestamp updateTime;

    public LogHrloginRecord() {}

    public LogHrloginRecord(LogHrloginRecord value) {
        this.id = value.id;
        this.hrId = value.hrId;
        this.loginDate = value.loginDate;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public LogHrloginRecord(
        Integer   id,
        Integer   hrId,
        Date      loginDate,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.hrId = hrId;
        this.loginDate = loginDate;
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

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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
        StringBuilder sb = new StringBuilder("LogHrloginRecord (");

        sb.append(id);
        sb.append(", ").append(hrId);
        sb.append(", ").append(loginDate);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
