/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserEmployeeUpvote;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 员工点赞记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeeUpvoteRecord extends UpdatableRecordImpl<UserEmployeeUpvoteRecord> implements Record9<Integer, Integer, Integer, Integer, Timestamp, Byte, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = -202006472;

    /**
     * Setter for <code>userdb.user_employee_upvote.id</code>. 主键
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.id</code>. 主键
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.sender</code>. 点赞的人的仟寻员工编号
     */
    public void setSender(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.sender</code>. 点赞的人的仟寻员工编号
     */
    public Integer getSender() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.receiver</code>. 被点赞的人的仟寻员工编号
     */
    public void setReceiver(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.receiver</code>. 被点赞的人的仟寻员工编号
     */
    public Integer getReceiver() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.company_id</code>. 公司编号，用于标识哪家员工的点赞
     */
    public void setCompanyId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.company_id</code>. 公司编号，用于标识哪家员工的点赞
     */
    public Integer getCompanyId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.upvote_time</code>. 点赞时间
     */
    public void setUpvoteTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.upvote_time</code>. 点赞时间
     */
    public Timestamp getUpvoteTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.cancel</code>. 是否可用, 0 表示 点赞，1表示取消点赞
     */
    public void setCancel(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.cancel</code>. 是否可用, 0 表示 点赞，1表示取消点赞
     */
    public Byte getCancel() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.cancel_time</code>. 取消时间
     */
    public void setCancelTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.cancel_time</code>. 取消时间
     */
    public Timestamp getCancelTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>userdb.user_employee_upvote.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>userdb.user_employee_upvote.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Integer, Integer, Timestamp, Byte, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Integer, Integer, Timestamp, Byte, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.SENDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.RECEIVER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPVOTE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CANCEL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE.UPDATE_TIME;
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
        return getSender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getReceiver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpvoteTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getCancel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCancelTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value2(Integer value) {
        setSender(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value3(Integer value) {
        setReceiver(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value4(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value5(Timestamp value) {
        setUpvoteTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value6(Byte value) {
        setCancel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value7(Timestamp value) {
        setCancelTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeUpvoteRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Timestamp value5, Byte value6, Timestamp value7, Timestamp value8, Timestamp value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserEmployeeUpvoteRecord
     */
    public UserEmployeeUpvoteRecord() {
        super(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE);
    }

    /**
     * Create a detached, initialised UserEmployeeUpvoteRecord
     */
    public UserEmployeeUpvoteRecord(Integer id, Integer sender, Integer receiver, Integer companyId, Timestamp upvoteTime, Byte cancel, Timestamp cancelTime, Timestamp createTime, Timestamp updateTime) {
        super(UserEmployeeUpvote.USER_EMPLOYEE_UPVOTE);

        set(0, id);
        set(1, sender);
        set(2, receiver);
        set(3, companyId);
        set(4, upvoteTime);
        set(5, cancel);
        set(6, cancelTime);
        set(7, createTime);
        set(8, updateTime);
    }
}
