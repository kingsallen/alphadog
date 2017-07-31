/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.historydb.tables.records;


import com.moseeker.baseorm.db.historydb.tables.HrWxHrChatListBakBak;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * IM聊天人关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatListBakBakRecord extends UpdatableRecordImpl<HrWxHrChatListBakBakRecord> implements Record8<Integer, Integer, Integer, Byte, Timestamp, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = 103206632;

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.id</code>. ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.id</code>. ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.sysuser_id</code>. sysuser.id
     */
    public void setSysuserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.sysuser_id</code>. sysuser.id
     */
    public Integer getSysuserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.hraccount_id</code>. hr_account.id
     */
    public void setHraccountId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.hraccount_id</code>. hr_account.id
     */
    public Integer getHraccountId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.status</code>. 状态，0：有效，1：无效
     */
    public void setStatus(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.status</code>. 状态，0：有效，1：无效
     */
    public Byte getStatus() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public void setWxChatTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public Timestamp getWxChatTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.hr_chat_time</code>. HR最近一次聊天时间
     */
    public void setHrChatTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.hr_chat_time</code>. HR最近一次聊天时间
     */
    public Timestamp getHrChatTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>historydb.hr_wx_hr_chat_list_bak_bak.update_time</code>. 创建时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>historydb.hr_wx_hr_chat_list_bak_bak.update_time</code>. 创建时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Byte, Timestamp, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Byte, Timestamp, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.SYSUSER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.HRACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.WX_CHAT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.HR_CHAT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getSysuserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getHraccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getWxChatTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getHrChatTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value2(Integer value) {
        setSysuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value3(Integer value) {
        setHraccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value4(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value6(Timestamp value) {
        setWxChatTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value7(Timestamp value) {
        setHrChatTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBakRecord values(Integer value1, Integer value2, Integer value3, Byte value4, Timestamp value5, Timestamp value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrWxHrChatListBakBakRecord
     */
    public HrWxHrChatListBakBakRecord() {
        super(HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK);
    }

    /**
     * Create a detached, initialised HrWxHrChatListBakBakRecord
     */
    public HrWxHrChatListBakBakRecord(Integer id, Integer sysuserId, Integer hraccountId, Byte status, Timestamp createTime, Timestamp wxChatTime, Timestamp hrChatTime, Timestamp updateTime) {
        super(HrWxHrChatListBakBak.HR_WX_HR_CHAT_LIST_BAK_BAK);

        set(0, id);
        set(1, sysuserId);
        set(2, hraccountId);
        set(3, status);
        set(4, createTime);
        set(5, wxChatTime);
        set(6, hrChatTime);
        set(7, updateTime);
    }
}