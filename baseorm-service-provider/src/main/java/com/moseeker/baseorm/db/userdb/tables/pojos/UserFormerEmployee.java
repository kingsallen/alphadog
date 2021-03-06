/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


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
public class UserFormerEmployee implements Serializable {

    private static final long serialVersionUID = -1887416486;

    private Integer   id;
    private String    name;
    private Integer   companyId;
    private String    email;
    private Long      mobile;
    private Integer   userId;
    private Timestamp lastTmplmsgSentTime;
    private Timestamp lastSmsSentTime;
    private Timestamp lastMailSentTime;

    public UserFormerEmployee() {}

    public UserFormerEmployee(UserFormerEmployee value) {
        this.id = value.id;
        this.name = value.name;
        this.companyId = value.companyId;
        this.email = value.email;
        this.mobile = value.mobile;
        this.userId = value.userId;
        this.lastTmplmsgSentTime = value.lastTmplmsgSentTime;
        this.lastSmsSentTime = value.lastSmsSentTime;
        this.lastMailSentTime = value.lastMailSentTime;
    }

    public UserFormerEmployee(
        Integer   id,
        String    name,
        Integer   companyId,
        String    email,
        Long      mobile,
        Integer   userId,
        Timestamp lastTmplmsgSentTime,
        Timestamp lastSmsSentTime,
        Timestamp lastMailSentTime
    ) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.email = email;
        this.mobile = mobile;
        this.userId = userId;
        this.lastTmplmsgSentTime = lastTmplmsgSentTime;
        this.lastSmsSentTime = lastSmsSentTime;
        this.lastMailSentTime = lastMailSentTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobile() {
        return this.mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Timestamp getLastTmplmsgSentTime() {
        return this.lastTmplmsgSentTime;
    }

    public void setLastTmplmsgSentTime(Timestamp lastTmplmsgSentTime) {
        this.lastTmplmsgSentTime = lastTmplmsgSentTime;
    }

    public Timestamp getLastSmsSentTime() {
        return this.lastSmsSentTime;
    }

    public void setLastSmsSentTime(Timestamp lastSmsSentTime) {
        this.lastSmsSentTime = lastSmsSentTime;
    }

    public Timestamp getLastMailSentTime() {
        return this.lastMailSentTime;
    }

    public void setLastMailSentTime(Timestamp lastMailSentTime) {
        this.lastMailSentTime = lastMailSentTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserFormerEmployee (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(companyId);
        sb.append(", ").append(email);
        sb.append(", ").append(mobile);
        sb.append(", ").append(userId);
        sb.append(", ").append(lastTmplmsgSentTime);
        sb.append(", ").append(lastSmsSentTime);
        sb.append(", ").append(lastMailSentTime);

        sb.append(")");
        return sb.toString();
    }
}
