/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 员工和候选人的聊天室
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserChatRoom implements Serializable {

    private static final long serialVersionUID = 2146188467;

    private Integer   id;
    private Integer   companyId;
    private Integer   userId;
    private Integer   employeeId;
    private Timestamp latestChatTime;
    private Timestamp userEnterTime;
    private Timestamp employeeEnterTime;
    private Timestamp userLeaveTime;
    private Timestamp employeeLeaveTime;
    private Byte      userDel;
    private Byte      employeeDel;
    private Integer   employeeUnreadCount;
    private Integer   userUnreadCount;
    private Integer   employeeHintSchedule;
    private Integer   userHintSchedule;
    private Integer   userSendForMsgId;
    private Integer   employeeSendForMsgId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public UserChatRoom() {}

    public UserChatRoom(UserChatRoom value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.userId = value.userId;
        this.employeeId = value.employeeId;
        this.latestChatTime = value.latestChatTime;
        this.userEnterTime = value.userEnterTime;
        this.employeeEnterTime = value.employeeEnterTime;
        this.userLeaveTime = value.userLeaveTime;
        this.employeeLeaveTime = value.employeeLeaveTime;
        this.userDel = value.userDel;
        this.employeeDel = value.employeeDel;
        this.employeeUnreadCount = value.employeeUnreadCount;
        this.userUnreadCount = value.userUnreadCount;
        this.employeeHintSchedule = value.employeeHintSchedule;
        this.userHintSchedule = value.userHintSchedule;
        this.userSendForMsgId = value.userSendForMsgId;
        this.employeeSendForMsgId = value.employeeSendForMsgId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public UserChatRoom(
        Integer   id,
        Integer   companyId,
        Integer   userId,
        Integer   employeeId,
        Timestamp latestChatTime,
        Timestamp userEnterTime,
        Timestamp employeeEnterTime,
        Timestamp userLeaveTime,
        Timestamp employeeLeaveTime,
        Byte      userDel,
        Byte      employeeDel,
        Integer   employeeUnreadCount,
        Integer   userUnreadCount,
        Integer   employeeHintSchedule,
        Integer   userHintSchedule,
        Integer   userSendForMsgId,
        Integer   employeeSendForMsgId,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.latestChatTime = latestChatTime;
        this.userEnterTime = userEnterTime;
        this.employeeEnterTime = employeeEnterTime;
        this.userLeaveTime = userLeaveTime;
        this.employeeLeaveTime = employeeLeaveTime;
        this.userDel = userDel;
        this.employeeDel = employeeDel;
        this.employeeUnreadCount = employeeUnreadCount;
        this.userUnreadCount = userUnreadCount;
        this.employeeHintSchedule = employeeHintSchedule;
        this.userHintSchedule = userHintSchedule;
        this.userSendForMsgId = userSendForMsgId;
        this.employeeSendForMsgId = employeeSendForMsgId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Timestamp getLatestChatTime() {
        return this.latestChatTime;
    }

    public void setLatestChatTime(Timestamp latestChatTime) {
        this.latestChatTime = latestChatTime;
    }

    public Timestamp getUserEnterTime() {
        return this.userEnterTime;
    }

    public void setUserEnterTime(Timestamp userEnterTime) {
        this.userEnterTime = userEnterTime;
    }

    public Timestamp getEmployeeEnterTime() {
        return this.employeeEnterTime;
    }

    public void setEmployeeEnterTime(Timestamp employeeEnterTime) {
        this.employeeEnterTime = employeeEnterTime;
    }

    public Timestamp getUserLeaveTime() {
        return this.userLeaveTime;
    }

    public void setUserLeaveTime(Timestamp userLeaveTime) {
        this.userLeaveTime = userLeaveTime;
    }

    public Timestamp getEmployeeLeaveTime() {
        return this.employeeLeaveTime;
    }

    public void setEmployeeLeaveTime(Timestamp employeeLeaveTime) {
        this.employeeLeaveTime = employeeLeaveTime;
    }

    public Byte getUserDel() {
        return this.userDel;
    }

    public void setUserDel(Byte userDel) {
        this.userDel = userDel;
    }

    public Byte getEmployeeDel() {
        return this.employeeDel;
    }

    public void setEmployeeDel(Byte employeeDel) {
        this.employeeDel = employeeDel;
    }

    public Integer getEmployeeUnreadCount() {
        return this.employeeUnreadCount;
    }

    public void setEmployeeUnreadCount(Integer employeeUnreadCount) {
        this.employeeUnreadCount = employeeUnreadCount;
    }

    public Integer getUserUnreadCount() {
        return this.userUnreadCount;
    }

    public void setUserUnreadCount(Integer userUnreadCount) {
        this.userUnreadCount = userUnreadCount;
    }

    public Integer getEmployeeHintSchedule() {
        return this.employeeHintSchedule;
    }

    public void setEmployeeHintSchedule(Integer employeeHintSchedule) {
        this.employeeHintSchedule = employeeHintSchedule;
    }

    public Integer getUserHintSchedule() {
        return this.userHintSchedule;
    }

    public void setUserHintSchedule(Integer userHintSchedule) {
        this.userHintSchedule = userHintSchedule;
    }

    public Integer getUserSendForMsgId() {
        return this.userSendForMsgId;
    }

    public void setUserSendForMsgId(Integer userSendForMsgId) {
        this.userSendForMsgId = userSendForMsgId;
    }

    public Integer getEmployeeSendForMsgId() {
        return this.employeeSendForMsgId;
    }

    public void setEmployeeSendForMsgId(Integer employeeSendForMsgId) {
        this.employeeSendForMsgId = employeeSendForMsgId;
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
        StringBuilder sb = new StringBuilder("UserChatRoom (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(userId);
        sb.append(", ").append(employeeId);
        sb.append(", ").append(latestChatTime);
        sb.append(", ").append(userEnterTime);
        sb.append(", ").append(employeeEnterTime);
        sb.append(", ").append(userLeaveTime);
        sb.append(", ").append(employeeLeaveTime);
        sb.append(", ").append(userDel);
        sb.append(", ").append(employeeDel);
        sb.append(", ").append(employeeUnreadCount);
        sb.append(", ").append(userUnreadCount);
        sb.append(", ").append(employeeHintSchedule);
        sb.append(", ").append(userHintSchedule);
        sb.append(", ").append(userSendForMsgId);
        sb.append(", ").append(employeeSendForMsgId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
