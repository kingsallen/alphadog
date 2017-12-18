/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 聊天室未读消息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrChatUnreadCountRecord extends UpdatableRecordImpl<HrChatUnreadCountRecord> implements Record7<Integer, Integer, Integer, Timestamp, Timestamp, Byte, Byte> {

    private static final long serialVersionUID = -963068663;

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.room_id</code>. 聊天室编号
     */
    public void setRoomId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.room_id</code>. 聊天室编号
     */
    public Integer getRoomId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.hr_id</code>. HR编号 userdb.user_hr_account
     */
    public void setHrId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.hr_id</code>. HR编号 userdb.user_hr_account
     */
    public Integer getHrId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.user_id</code>. 用户编号 userdb.user_user.id
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.user_id</code>. 用户编号 userdb.user_user.id
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public void setWxChatTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public Timestamp getWxChatTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.hr_chat_time</code>. HR最近一次聊天时间
     */
    public void setHrChatTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.hr_chat_time</code>. HR最近一次聊天时间
     */
    public Timestamp getHrChatTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.hr_have_unread_msg</code>. HR是否有未读消息，0：没有，1有未读消息
     */
    public void setHrHaveUnreadMsg(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.hr_have_unread_msg</code>. HR是否有未读消息，0：没有，1有未读消息
     */
    public Byte getHrHaveUnreadMsg() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_chat_unread_count.user_have_unread_msg</code>. user是否有未读消息 ，0：没有，1有未读消息
     */
    public void setUserHaveUnreadMsg(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_chat_unread_count.user_have_unread_msg</code>. user是否有未读消息 ，0：没有，1有未读消息
     */
    public Byte getUserHaveUnreadMsg() {
        return (Byte) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, Timestamp, Timestamp, Byte, Byte> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, Timestamp, Timestamp, Byte, Byte> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.ROOM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.WX_CHAT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_CHAT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_HAVE_UNREAD_MSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_HAVE_UNREAD_MSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getRoomId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getWxChatTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getHrChatTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getHrHaveUnreadMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getUserHaveUnreadMsg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value1(Integer value) {
        setRoomId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value2(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value4(Timestamp value) {
        setWxChatTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value5(Timestamp value) {
        setHrChatTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value6(Byte value) {
        setHrHaveUnreadMsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord value7(Byte value) {
        setUserHaveUnreadMsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCountRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Timestamp value5, Byte value6, Byte value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrChatUnreadCountRecord
     */
    public HrChatUnreadCountRecord() {
        super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT);
    }

    /**
     * Create a detached, initialised HrChatUnreadCountRecord
     */
    public HrChatUnreadCountRecord(Integer roomId, Integer hrId, Integer userId, Timestamp wxChatTime, Timestamp hrChatTime, Byte hrHaveUnreadMsg, Byte userHaveUnreadMsg) {
        super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT);

        set(0, roomId);
        set(1, hrId);
        set(2, userId);
        set(3, wxChatTime);
        set(4, hrChatTime);
        set(5, hrHaveUnreadMsg);
        set(6, userHaveUnreadMsg);
    }
}
