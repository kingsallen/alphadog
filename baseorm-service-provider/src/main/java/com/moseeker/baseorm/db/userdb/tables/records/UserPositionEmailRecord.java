/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserPositionEmail;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户订阅职位推荐邮件
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserPositionEmailRecord extends UpdatableRecordImpl<UserPositionEmailRecord> implements Record6<Integer, Integer, String, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1895134103;

    /**
     * Setter for <code>userdb.user_position_email.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_position_email.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_position_email.user_id</code>. 用户id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_position_email.user_id</code>. 用户id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_position_email.conditions</code>. 订阅的职位条件
     */
    public void setConditions(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_position_email.conditions</code>. 订阅的职位条件
     */
    public String getConditions() {
        return (String) get(2);
    }

    /**
     * Setter for <code>userdb.user_position_email.status</code>. 状态，0是正常，1是取消
     */
    public void setStatus(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_position_email.status</code>. 状态，0是正常，1是取消
     */
    public Integer getStatus() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>userdb.user_position_email.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_position_email.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>userdb.user_position_email.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_position_email.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserPositionEmail.USER_POSITION_EMAIL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserPositionEmail.USER_POSITION_EMAIL.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserPositionEmail.USER_POSITION_EMAIL.CONDITIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserPositionEmail.USER_POSITION_EMAIL.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return UserPositionEmail.USER_POSITION_EMAIL.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return UserPositionEmail.USER_POSITION_EMAIL.UPDATE_TIME;
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
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getConditions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
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
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord value3(String value) {
        setConditions(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord value4(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPositionEmailRecord values(Integer value1, Integer value2, String value3, Integer value4, Timestamp value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserPositionEmailRecord
     */
    public UserPositionEmailRecord() {
        super(UserPositionEmail.USER_POSITION_EMAIL);
    }

    /**
     * Create a detached, initialised UserPositionEmailRecord
     */
    public UserPositionEmailRecord(Integer id, Integer userId, String conditions, Integer status, Timestamp createTime, Timestamp updateTime) {
        super(UserPositionEmail.USER_POSITION_EMAIL);

        set(0, id);
        set(1, userId);
        set(2, conditions);
        set(3, status);
        set(4, createTime);
        set(5, updateTime);
    }
}
