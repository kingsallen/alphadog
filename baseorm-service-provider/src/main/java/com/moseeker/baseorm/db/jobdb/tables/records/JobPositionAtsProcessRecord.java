/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobPositionAtsProcess;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 职位招聘流程绑定表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionAtsProcessRecord extends UpdatableRecordImpl<JobPositionAtsProcessRecord> implements Record5<Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -431130327;

    /**
     * Setter for <code>jobdb.job_position_ats_process.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ats_process.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_position_ats_process.pid</code>. 职位id  job_application.id
     */
    public void setPid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ats_process.pid</code>. 职位id  job_application.id
     */
    public Integer getPid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>jobdb.job_position_ats_process.process_id</code>. hrdb.hr_ats_process_company.id
     */
    public void setProcessId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ats_process.process_id</code>. hrdb.hr_ats_process_company.id
     */
    public Integer getProcessId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>jobdb.job_position_ats_process.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ats_process.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>jobdb.job_position_ats_process.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ats_process.update_time</code>.
     */
    public Timestamp getUpdateTime() {
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
        return JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.PROCESS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.UPDATE_TIME;
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
        return getPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getProcessId();
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
    public Timestamp value5() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionAtsProcessRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionAtsProcessRecord value2(Integer value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionAtsProcessRecord value3(Integer value) {
        setProcessId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionAtsProcessRecord value4(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionAtsProcessRecord value5(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionAtsProcessRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Timestamp value5) {
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
     * Create a detached JobPositionAtsProcessRecord
     */
    public JobPositionAtsProcessRecord() {
        super(JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS);
    }

    /**
     * Create a detached, initialised JobPositionAtsProcessRecord
     */
    public JobPositionAtsProcessRecord(Integer id, Integer pid, Integer processId, Timestamp createTime, Timestamp updateTime) {
        super(JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS);

        set(0, id);
        set(1, pid);
        set(2, processId);
        set(3, createTime);
        set(4, updateTime);
    }
}
