/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.ReferralLog;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 内推记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralLogRecord extends UpdatableRecordImpl<ReferralLogRecord> implements Record10<Integer, Integer, Integer, Integer, Integer, Timestamp, Byte, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = 2099641764;

    /**
     * Setter for <code>referraldb.referral_log.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.referral_log.employee_id</code>. 员工编号
     */
    public void setEmployeeId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.employee_id</code>. 员工编号
     */
    public Integer getEmployeeId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>referraldb.referral_log.reference_id</code>. 被推荐人编号
     */
    public void setReferenceId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.reference_id</code>. 被推荐人编号
     */
    public Integer getReferenceId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>referraldb.referral_log.position_id</code>. 职位编号
     */
    public void setPositionId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.position_id</code>. 职位编号
     */
    public Integer getPositionId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>referraldb.referral_log.type</code>. 推荐方式 1 手机文件上传，2 电脑扫码上传简历 3 推荐人才关键信息
     */
    public void setType(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.type</code>. 推荐方式 1 手机文件上传，2 电脑扫码上传简历 3 推荐人才关键信息
     */
    public Integer getType() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>referraldb.referral_log.referral_time</code>. 推荐时间
     */
    public void setReferralTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.referral_time</code>. 推荐时间
     */
    public Timestamp getReferralTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>referraldb.referral_log.claim</code>. 是否认领。0 未认领， 1 已经认领
     */
    public void setClaim(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.claim</code>. 是否认领。0 未认领， 1 已经认领
     */
    public Byte getClaim() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>referraldb.referral_log.claim_time</code>. 认领时间
     */
    public void setClaimTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.claim_time</code>. 认领时间
     */
    public Timestamp getClaimTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>referraldb.referral_log.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>referraldb.referral_log.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>referraldb.referral_log.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Integer, Integer, Timestamp, Byte, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Integer, Integer, Timestamp, Byte, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ReferralLog.REFERRAL_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ReferralLog.REFERRAL_LOG.EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ReferralLog.REFERRAL_LOG.REFERENCE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return ReferralLog.REFERRAL_LOG.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return ReferralLog.REFERRAL_LOG.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return ReferralLog.REFERRAL_LOG.REFERRAL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return ReferralLog.REFERRAL_LOG.CLAIM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ReferralLog.REFERRAL_LOG.CLAIM_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return ReferralLog.REFERRAL_LOG.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return ReferralLog.REFERRAL_LOG.UPDATE_TIME;
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
        return getEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getReferenceId();
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
    public Integer value5() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getReferralTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getClaim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getClaimTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value2(Integer value) {
        setEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value3(Integer value) {
        setReferenceId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value4(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value5(Integer value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value6(Timestamp value) {
        setReferralTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value7(Byte value) {
        setClaim(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value8(Timestamp value) {
        setClaimTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value9(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord value10(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralLogRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Timestamp value6, Byte value7, Timestamp value8, Timestamp value9, Timestamp value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ReferralLogRecord
     */
    public ReferralLogRecord() {
        super(ReferralLog.REFERRAL_LOG);
    }

    /**
     * Create a detached, initialised ReferralLogRecord
     */
    public ReferralLogRecord(Integer id, Integer employeeId, Integer referenceId, Integer positionId, Integer type, Timestamp referralTime, Byte claim, Timestamp claimTime, Timestamp createTime, Timestamp updateTime) {
        super(ReferralLog.REFERRAL_LOG);

        set(0, id);
        set(1, employeeId);
        set(2, referenceId);
        set(3, positionId);
        set(4, type);
        set(5, referralTime);
        set(6, claim);
        set(7, claimTime);
        set(8, createTime);
        set(9, updateTime);
    }
}