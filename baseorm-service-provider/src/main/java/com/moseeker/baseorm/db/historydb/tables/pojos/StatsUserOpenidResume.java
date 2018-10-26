/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;
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
public class StatsUserOpenidResume implements Serializable {

    private static final long serialVersionUID = -574032097;

    private Integer   id;
    private Timestamp createTime;
    private Integer   openid;
    private Integer   user;
    private Integer   resume;

    public StatsUserOpenidResume() {}

    public StatsUserOpenidResume(StatsUserOpenidResume value) {
        this.id = value.id;
        this.createTime = value.createTime;
        this.openid = value.openid;
        this.user = value.user;
        this.resume = value.resume;
    }

    public StatsUserOpenidResume(
        Integer   id,
        Timestamp createTime,
        Integer   openid,
        Integer   user,
        Integer   resume
    ) {
        this.id = id;
        this.createTime = createTime;
        this.openid = openid;
        this.user = user;
        this.resume = resume;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getOpenid() {
        return this.openid;
    }

    public void setOpenid(Integer openid) {
        this.openid = openid;
    }

    public Integer getUser() {
        return this.user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getResume() {
        return this.resume;
    }

    public void setResume(Integer resume) {
        this.resume = resume;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StatsUserOpenidResume (");

        sb.append(id);
        sb.append(", ").append(createTime);
        sb.append(", ").append(openid);
        sb.append(", ").append(user);
        sb.append(", ").append(resume);

        sb.append(")");
        return sb.toString();
    }
}
