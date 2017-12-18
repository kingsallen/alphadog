/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * IM聊天人关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatList implements Serializable {

    private static final long serialVersionUID = -5175403;

    private Integer   id;
    private Integer   sysuserId;
    private Integer   hraccountId;
    private Timestamp createTime;
    private Timestamp wxChatTime;
    private Timestamp hrChatTime;
    private Timestamp updateTime;
    private Integer   hrUnreadCount;
    private Integer   userUnreadCount;

    public HrWxHrChatList() {}

    public HrWxHrChatList(HrWxHrChatList value) {
        this.id = value.id;
        this.sysuserId = value.sysuserId;
        this.hraccountId = value.hraccountId;
        this.createTime = value.createTime;
        this.wxChatTime = value.wxChatTime;
        this.hrChatTime = value.hrChatTime;
        this.updateTime = value.updateTime;
        this.hrUnreadCount = value.hrUnreadCount;
        this.userUnreadCount = value.userUnreadCount;
    }

    public HrWxHrChatList(
        Integer   id,
        Integer   sysuserId,
        Integer   hraccountId,
        Timestamp createTime,
        Timestamp wxChatTime,
        Timestamp hrChatTime,
        Timestamp updateTime,
        Integer   hrUnreadCount,
        Integer   userUnreadCount
    ) {
        this.id = id;
        this.sysuserId = sysuserId;
        this.hraccountId = hraccountId;
        this.createTime = createTime;
        this.wxChatTime = wxChatTime;
        this.hrChatTime = hrChatTime;
        this.updateTime = updateTime;
        this.hrUnreadCount = hrUnreadCount;
        this.userUnreadCount = userUnreadCount;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysuserId() {
        return this.sysuserId;
    }

    public void setSysuserId(Integer sysuserId) {
        this.sysuserId = sysuserId;
    }

    public Integer getHraccountId() {
        return this.hraccountId;
    }

    public void setHraccountId(Integer hraccountId) {
        this.hraccountId = hraccountId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getWxChatTime() {
        return this.wxChatTime;
    }

    public void setWxChatTime(Timestamp wxChatTime) {
        this.wxChatTime = wxChatTime;
    }

    public Timestamp getHrChatTime() {
        return this.hrChatTime;
    }

    public void setHrChatTime(Timestamp hrChatTime) {
        this.hrChatTime = hrChatTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getHrUnreadCount() {
        return this.hrUnreadCount;
    }

    public void setHrUnreadCount(Integer hrUnreadCount) {
        this.hrUnreadCount = hrUnreadCount;
    }

    public Integer getUserUnreadCount() {
        return this.userUnreadCount;
    }

    public void setUserUnreadCount(Integer userUnreadCount) {
        this.userUnreadCount = userUnreadCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrWxHrChatList (");

        sb.append(id);
        sb.append(", ").append(sysuserId);
        sb.append(", ").append(hraccountId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(wxChatTime);
        sb.append(", ").append(hrChatTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(hrUnreadCount);
        sb.append(", ").append(userUnreadCount);

        sb.append(")");
        return sb.toString();
    }
}
