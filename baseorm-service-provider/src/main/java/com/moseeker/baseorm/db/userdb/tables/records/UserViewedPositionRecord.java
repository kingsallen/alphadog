/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserViewedPosition;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 用户查看过的职位
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserViewedPositionRecord extends UpdatableRecordImpl<UserViewedPositionRecord> implements Record4<Integer, Integer, Integer, Timestamp> {

    private static final long serialVersionUID = 1326843133;

    /**
     * Setter for <code>userdb.user_viewed_position.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_viewed_position.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_viewed_position.user_id</code>. 用户id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_viewed_position.user_id</code>. 用户id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_viewed_position.position_id</code>. 职位id
     */
    public void setPositionId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_viewed_position.position_id</code>. 职位id
     */
    public Integer getPositionId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_viewed_position.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_viewed_position.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserViewedPosition.USER_VIEWED_POSITION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserViewedPosition.USER_VIEWED_POSITION.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserViewedPosition.USER_VIEWED_POSITION.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return UserViewedPosition.USER_VIEWED_POSITION.CREATE_TIME;
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
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserViewedPositionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserViewedPositionRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserViewedPositionRecord value3(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserViewedPositionRecord value4(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserViewedPositionRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserViewedPositionRecord
     */
    public UserViewedPositionRecord() {
        super(UserViewedPosition.USER_VIEWED_POSITION);
    }

    /**
     * Create a detached, initialised UserViewedPositionRecord
     */
    public UserViewedPositionRecord(Integer id, Integer userId, Integer positionId, Timestamp createTime) {
        super(UserViewedPosition.USER_VIEWED_POSITION);

        set(0, id);
        set(1, userId);
        set(2, positionId);
        set(3, createTime);
    }
}