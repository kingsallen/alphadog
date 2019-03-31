/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables.records;


import com.moseeker.baseorm.db.redpacketdb.tables.RedpacketAmountsLog;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 红包埋点
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketAmountsLogRecord extends UpdatableRecordImpl<RedpacketAmountsLogRecord> implements Record6<Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1911763932;

    /**
     * Setter for <code>redpacketdb.redpacket_amounts_log.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_amounts_log.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_amounts_log.activity_id</code>. 所属红包活动
     */
    public void setActivityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_amounts_log.activity_id</code>. 所属红包活动
     */
    public Integer getActivityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_amounts_log.amount</code>. 红包金额，有可能是0
     */
    public void setAmount(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_amounts_log.amount</code>. 红包金额，有可能是0
     */
    public Integer getAmount() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_amounts_log.position_id</code>. 职位编号
     */
    public void setPositionId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_amounts_log.position_id</code>. 职位编号
     */
    public Integer getPositionId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_amounts_log.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_amounts_log.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_amounts_log.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_amounts_log.update_time</code>. 更新时间
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
    public Row6<Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG.ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG.UPDATE_TIME;
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
        return getActivityId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getPositionId();
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
    public RedpacketAmountsLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketAmountsLogRecord value2(Integer value) {
        setActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketAmountsLogRecord value3(Integer value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketAmountsLogRecord value4(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketAmountsLogRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketAmountsLogRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketAmountsLogRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached RedpacketAmountsLogRecord
     */
    public RedpacketAmountsLogRecord() {
        super(RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG);
    }

    /**
     * Create a detached, initialised RedpacketAmountsLogRecord
     */
    public RedpacketAmountsLogRecord(Integer id, Integer activityId, Integer amount, Integer positionId, Timestamp createTime, Timestamp updateTime) {
        super(RedpacketAmountsLog.REDPACKET_AMOUNTS_LOG);

        set(0, id);
        set(1, activityId);
        set(2, amount);
        set(3, positionId);
        set(4, createTime);
        set(5, updateTime);
    }
}
