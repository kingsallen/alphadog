/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeRegisterLog;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 员工认证取消认证操作记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralEmployeeRegisterLogRecord extends UpdatableRecordImpl<ReferralEmployeeRegisterLogRecord> implements Record6<Integer, Integer, Byte, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1700166373;

    /**
     * Setter for <code>referraldb.referral_employee_register_log.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_register_log.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.referral_employee_register_log.employee_id</code>. 员工编号
     */
    public void setEmployeeId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_register_log.employee_id</code>. 员工编号
     */
    public Integer getEmployeeId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>referraldb.referral_employee_register_log.register</code>. 是否是注册 1表示注册，0表示取消注册
     */
    public void setRegister(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_register_log.register</code>. 是否是注册 1表示注册，0表示取消注册
     */
    public Byte getRegister() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>referraldb.referral_employee_register_log.operate_time</code>. 操作时间
     */
    public void setOperateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_register_log.operate_time</code>. 操作时间
     */
    public Timestamp getOperateTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>referraldb.referral_employee_register_log.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_register_log.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>referraldb.referral_employee_register_log.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_register_log.update_time</code>. 更新时间
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
    public Row6<Integer, Integer, Byte, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Byte, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG.EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG.REGISTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG.OPERATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG.UPDATE_TIME;
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
    public Byte value3() {
        return getRegister();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getOperateTime();
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
    public ReferralEmployeeRegisterLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLogRecord value2(Integer value) {
        setEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLogRecord value3(Byte value) {
        setRegister(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLogRecord value4(Timestamp value) {
        setOperateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLogRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLogRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeRegisterLogRecord values(Integer value1, Integer value2, Byte value3, Timestamp value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached ReferralEmployeeRegisterLogRecord
     */
    public ReferralEmployeeRegisterLogRecord() {
        super(ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG);
    }

    /**
     * Create a detached, initialised ReferralEmployeeRegisterLogRecord
     */
    public ReferralEmployeeRegisterLogRecord(Integer id, Integer employeeId, Byte register, Timestamp operateTime, Timestamp createTime, Timestamp updateTime) {
        super(ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG);

        set(0, id);
        set(1, employeeId);
        set(2, register);
        set(3, operateTime);
        set(4, createTime);
        set(5, updateTime);
    }
}
