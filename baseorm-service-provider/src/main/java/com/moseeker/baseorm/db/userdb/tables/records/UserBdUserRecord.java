/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserBdUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 百度用户信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserBdUserRecord extends UpdatableRecordImpl<UserBdUserRecord> implements Record8<Long, Long, Integer, String, Integer, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -590608008;

    /**
     * Setter for <code>userdb.user_bd_user.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>userdb.user_bd_user.uid</code>. 百度帐号 id
     */
    public void setUid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.uid</code>. 百度帐号 id
     */
    public Long getUid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>userdb.user_bd_user.user_id</code>. user_user.id, C端用户ID
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.user_id</code>. user_user.id, C端用户ID
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_bd_user.username</code>. 登录用户名
     */
    public void setUsername(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.username</code>. 登录用户名
     */
    public String getUsername() {
        return (String) get(3);
    }

    /**
     * Setter for <code>userdb.user_bd_user.sex</code>. 用户性别 2:未知  0:女性 1:男性
     */
    public void setSex(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.sex</code>. 用户性别 2:未知  0:女性 1:男性
     */
    public Integer getSex() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>userdb.user_bd_user.headimgurl</code>. 用户头像
     */
    public void setHeadimgurl(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.headimgurl</code>. 用户头像
     */
    public String getHeadimgurl() {
        return (String) get(5);
    }

    /**
     * Setter for <code>userdb.user_bd_user.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>userdb.user_bd_user.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_bd_user.update_time</code>.
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
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Long, Integer, String, Integer, String, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, Long, Integer, String, Integer, String, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return UserBdUser.USER_BD_USER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return UserBdUser.USER_BD_USER.UID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserBdUser.USER_BD_USER.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UserBdUser.USER_BD_USER.USERNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return UserBdUser.USER_BD_USER.SEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UserBdUser.USER_BD_USER.HEADIMGURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return UserBdUser.USER_BD_USER.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return UserBdUser.USER_BD_USER.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getUid();
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
    public String value4() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getSex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getHeadimgurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
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
    public UserBdUserRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value2(Long value) {
        setUid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value4(String value) {
        setUsername(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value5(Integer value) {
        setSex(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value6(String value) {
        setHeadimgurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBdUserRecord values(Long value1, Long value2, Integer value3, String value4, Integer value5, String value6, Timestamp value7, Timestamp value8) {
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
     * Create a detached UserBdUserRecord
     */
    public UserBdUserRecord() {
        super(UserBdUser.USER_BD_USER);
    }

    /**
     * Create a detached, initialised UserBdUserRecord
     */
    public UserBdUserRecord(Long id, Long uid, Integer userId, String username, Integer sex, String headimgurl, Timestamp createTime, Timestamp updateTime) {
        super(UserBdUser.USER_BD_USER);

        set(0, id);
        set(1, uid);
        set(2, userId);
        set(3, username);
        set(4, sex);
        set(5, headimgurl);
        set(6, createTime);
        set(7, updateTime);
    }
}
