/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables.records;


import com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivityPosition;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 参与红包活动的职位记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketActivityPositionRecord extends UpdatableRecordImpl<RedpacketActivityPositionRecord> implements Record9<Integer, Integer, Integer, Integer, Integer, Byte, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = -448491078;

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.activity_id</code>. 红包活动编号
     */
    public void setActivityId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.activity_id</code>. 红包活动编号
     */
    public Integer getActivityId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.position_id</code>. 职位编号
     */
    public void setPositionId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.position_id</code>. 职位编号
     */
    public Integer getPositionId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.total_amount</code>. 职位分配到的金额。单位 分
     */
    public void setTotalAmount(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.total_amount</code>. 职位分配到的金额。单位 分
     */
    public Integer getTotalAmount() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.left_amount</code>. 剩余红包金额。单位 分
     */
    public void setLeftAmount(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.left_amount</code>. 剩余红包金额。单位 分
     */
    public Integer getLeftAmount() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.enable</code>. 是否有效。1:表示有效；0:表示无效
     */
    public void setEnable(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.enable</code>. 是否有效。1:表示有效；0:表示无效
     */
    public Byte getEnable() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>redpacketdb.redpacket_activity_position.storage</code>. 剩余红包总数
     */
    public void setStorage(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>redpacketdb.redpacket_activity_position.storage</code>. 剩余红包总数
     */
    public Integer getStorage() {
        return (Integer) get(8);
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
    public Row9<Integer, Integer, Integer, Integer, Integer, Byte, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Integer, Integer, Integer, Byte, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.TOTAL_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.LEFT_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ENABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.STORAGE;
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
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getTotalAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getLeftAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getEnable();
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
    public Integer value9() {
        return getStorage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value2(Integer value) {
        setActivityId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value3(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value4(Integer value) {
        setTotalAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value5(Integer value) {
        setLeftAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value6(Byte value) {
        setEnable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord value9(Integer value) {
        setStorage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedpacketActivityPositionRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Byte value6, Timestamp value7, Timestamp value8, Integer value9) {
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
     * Create a detached RedpacketActivityPositionRecord
     */
    public RedpacketActivityPositionRecord() {
        super(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION);
    }

    /**
     * Create a detached, initialised RedpacketActivityPositionRecord
     */
    public RedpacketActivityPositionRecord(Integer id, Integer activityId, Integer positionId, Integer totalAmount, Integer leftAmount, Byte enable, Timestamp createTime, Timestamp updateTime, Integer storage) {
        super(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION);

        set(0, id);
        set(1, activityId);
        set(2, positionId);
        set(3, totalAmount);
        set(4, leftAmount);
        set(5, enable);
        set(6, createTime);
        set(7, updateTime);
        set(8, storage);
    }
}
