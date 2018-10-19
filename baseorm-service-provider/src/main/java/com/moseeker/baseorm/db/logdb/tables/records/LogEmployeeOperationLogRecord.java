/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.records;


import com.moseeker.baseorm.db.logdb.tables.LogEmployeeOperationLog;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogEmployeeOperationLogRecord extends UpdatableRecordImpl<LogEmployeeOperationLogRecord> implements Record9<Integer, Integer, Byte, Byte, Byte, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 136264966;

    /**
     * Setter for <code>logdb.log_employee_operation_log.id</code>. primaryKey
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.id</code>. primaryKey
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.user_id</code>. user_user Id
     */
    public void setUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.user_id</code>. user_user Id
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.type</code>. 入口类型  102：我是员工
     */
    public void setType(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.type</code>. 入口类型  102：我是员工
     */
    public Byte getType() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.operation_type</code>. 操作类型  0：员工认证， 1：推荐简历
     */
    public void setOperationType(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.operation_type</code>. 操作类型  0：员工认证， 1：推荐简历
     */
    public Byte getOperationType() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.is_success</code>. 是否成功 1：成功，0：失败
     */
    public void setIsSuccess(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.is_success</code>. 是否成功 1：成功，0：失败
     */
    public Byte getIsSuccess() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.company_id</code>. hr_company id
     */
    public void setCompanyId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.company_id</code>. hr_company id
     */
    public Integer getCompanyId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.profile_id</code>. profile_profile id 建立成功的简历id。
     */
    public void setProfileId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.profile_id</code>. profile_profile id 建立成功的简历id。
     */
    public Integer getProfileId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>logdb.log_employee_operation_log.update_tiem</code>. 更新时间
     */
    public void setUpdateTiem(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>logdb.log_employee_operation_log.update_tiem</code>. 更新时间
     */
    public Timestamp getUpdateTiem() {
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
    public Row9<Integer, Integer, Byte, Byte, Byte, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Byte, Byte, Byte, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.OPERATION_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.IS_SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.PROFILE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG.UPDATE_TIEM;
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
    public Byte value3() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getOperationType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getIsSuccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getProfileId();
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
        return getUpdateTiem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value3(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value4(Byte value) {
        setOperationType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value5(Byte value) {
        setIsSuccess(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value6(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value7(Integer value) {
        setProfileId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord value9(Timestamp value) {
        setUpdateTiem(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmployeeOperationLogRecord values(Integer value1, Integer value2, Byte value3, Byte value4, Byte value5, Integer value6, Integer value7, Timestamp value8, Timestamp value9) {
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
     * Create a detached LogEmployeeOperationLogRecord
     */
    public LogEmployeeOperationLogRecord() {
        super(LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG);
    }

    /**
     * Create a detached, initialised LogEmployeeOperationLogRecord
     */
    public LogEmployeeOperationLogRecord(Integer id, Integer userId, Byte type, Byte operationType, Byte isSuccess, Integer companyId, Integer profileId, Timestamp createTime, Timestamp updateTiem) {
        super(LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG);

        set(0, id);
        set(1, userId);
        set(2, type);
        set(3, operationType);
        set(4, isSuccess);
        set(5, companyId);
        set(6, profileId);
        set(7, createTime);
        set(8, updateTiem);
    }
}
