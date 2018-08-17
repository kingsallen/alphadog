/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserRecommendRefusal;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户拒绝推荐信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecommendRefusalRecord extends UpdatableRecordImpl<UserRecommendRefusalRecord> implements Record5<Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1462075564;

    /**
     * Setter for <code>userdb.user_recommend_refusal.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_recommend_refusal.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_recommend_refusal.user_id</code>. C端用户ID
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_recommend_refusal.user_id</code>. C端用户ID
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_recommend_refusal.wechat_id</code>. 公众号ID，表示用户在哪个公众号拒绝推送
     */
    public void setWechatId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_recommend_refusal.wechat_id</code>. 公众号ID，表示用户在哪个公众号拒绝推送
     */
    public Integer getWechatId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_recommend_refusal.refuse_time</code>. 拒绝推荐的时间
     */
    public void setRefuseTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_recommend_refusal.refuse_time</code>. 拒绝推荐的时间
     */
    public Timestamp getRefuseTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>userdb.user_recommend_refusal.refuse_timeout</code>. 拒绝推荐过期时间,默认refuse_time+6个月
     */
    public void setRefuseTimeout(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_recommend_refusal.refuse_timeout</code>. 拒绝推荐过期时间,默认refuse_time+6个月
     */
    public Timestamp getRefuseTimeout() {
        return (Timestamp) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserRecommendRefusal.USER_RECOMMEND_REFUSAL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserRecommendRefusal.USER_RECOMMEND_REFUSAL.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserRecommendRefusal.USER_RECOMMEND_REFUSAL.WECHAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return UserRecommendRefusal.USER_RECOMMEND_REFUSAL.REFUSE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return UserRecommendRefusal.USER_RECOMMEND_REFUSAL.REFUSE_TIMEOUT;
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
    public Integer value3() {
        return getWechatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getRefuseTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getRefuseTimeout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusalRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusalRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusalRecord value3(Integer value) {
        setWechatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusalRecord value4(Timestamp value) {
        setRefuseTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusalRecord value5(Timestamp value) {
        setRefuseTimeout(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusalRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecommendRefusalRecord
     */
    public UserRecommendRefusalRecord() {
        super(UserRecommendRefusal.USER_RECOMMEND_REFUSAL);
    }

    /**
     * Create a detached, initialised UserRecommendRefusalRecord
     */
    public UserRecommendRefusalRecord(Integer id, Integer userId, Integer wechatId, Timestamp refuseTime, Timestamp refuseTimeout) {
        super(UserRecommendRefusal.USER_RECOMMEND_REFUSAL);

        set(0, id);
        set(1, userId);
        set(2, wechatId);
        set(3, refuseTime);
        set(4, refuseTimeout);
    }
}
