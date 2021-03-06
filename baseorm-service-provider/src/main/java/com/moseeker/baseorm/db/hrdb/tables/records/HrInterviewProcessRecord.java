/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewProcess;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 面试流程表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewProcessRecord extends UpdatableRecordImpl<HrInterviewProcessRecord> implements Record9<Integer, Integer, Integer, String, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 563323885;

    /**
     * Setter for <code>hrdb.hr_interview_process.id</code>. 序列ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.id</code>. 序列ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.company_id</code>. 职位ID
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.company_id</code>. 职位ID
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.hr_id</code>. 创建人 hr id
     */
    public void setHrId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.hr_id</code>. 创建人 hr id
     */
    public Integer getHrId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.process_name</code>. 面试流程名称
     */
    public void setProcessName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.process_name</code>. 面试流程名称
     */
    public String getProcessName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.disabled</code>. 状态： 0 启用  1 禁用 
     */
    public void setDisabled(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.disabled</code>. 状态： 0 启用  1 禁用 
     */
    public Integer getDisabled() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.deleted</code>. 逻辑删除 1 删除 0 未删除
     */
    public void setDeleted(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.deleted</code>. 逻辑删除 1 删除 0 未删除
     */
    public Integer getDeleted() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.is_default_process</code>. 是否是默认流程 0 不是 1 是
     */
    public void setIsDefaultProcess(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.is_default_process</code>. 是否是默认流程 0 不是 1 是
     */
    public Integer getIsDefaultProcess() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_interview_process.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_process.update_time</code>. 更新时间
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
    public Row9<Integer, Integer, Integer, String, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Integer, String, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.PROCESS_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.DISABLED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.DELETED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.IS_DEFAULT_PROCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return HrInterviewProcess.HR_INTERVIEW_PROCESS.UPDATE_TIME;
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
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getProcessName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getDisabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getDeleted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getIsDefaultProcess();
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
    public HrInterviewProcessRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value3(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value4(String value) {
        setProcessName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value5(Integer value) {
        setDisabled(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value6(Integer value) {
        setDeleted(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value7(Integer value) {
        setIsDefaultProcess(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewProcessRecord values(Integer value1, Integer value2, Integer value3, String value4, Integer value5, Integer value6, Integer value7, Timestamp value8, Timestamp value9) {
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
     * Create a detached HrInterviewProcessRecord
     */
    public HrInterviewProcessRecord() {
        super(HrInterviewProcess.HR_INTERVIEW_PROCESS);
    }

    /**
     * Create a detached, initialised HrInterviewProcessRecord
     */
    public HrInterviewProcessRecord(Integer id, Integer companyId, Integer hrId, String processName, Integer disabled, Integer deleted, Integer isDefaultProcess, Timestamp createTime, Timestamp updateTime) {
        super(HrInterviewProcess.HR_INTERVIEW_PROCESS);

        set(0, id);
        set(1, companyId);
        set(2, hrId);
        set(3, processName);
        set(4, disabled);
        set(5, deleted);
        set(6, isDefaultProcess);
        set(7, createTime);
        set(8, updateTime);
    }
}
