/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobPositionCcmail;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class JobPositionCcmailRecord extends UpdatableRecordImpl<JobPositionCcmailRecord> implements Record6<Integer, Integer, String, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 2130034682;

    /**
     * Setter for <code>jobdb.job_position_ccmail.id</code>. 主键ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ccmail.id</code>. 主键ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_position_ccmail.position_id</code>. job_position.id
     */
    public void setPositionId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ccmail.position_id</code>. job_position.id
     */
    public Integer getPositionId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>jobdb.job_position_ccmail.to_email</code>. 抄送邮箱
     */
    public void setToEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ccmail.to_email</code>. 抄送邮箱
     */
    public String getToEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>jobdb.job_position_ccmail.hr_id</code>. 操作人User_hr_account.id
     */
    public void setHrId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ccmail.hr_id</code>. 操作人User_hr_account.id
     */
    public Integer getHrId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>jobdb.job_position_ccmail.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ccmail.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>jobdb.job_position_ccmail.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>jobdb.job_position_ccmail.update_time</code>. 更新时间
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
    public Row6<Integer, Integer, String, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return JobPositionCcmail.JOB_POSITION_CCMAIL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JobPositionCcmail.JOB_POSITION_CCMAIL.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JobPositionCcmail.JOB_POSITION_CCMAIL.TO_EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return JobPositionCcmail.JOB_POSITION_CCMAIL.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return JobPositionCcmail.JOB_POSITION_CCMAIL.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return JobPositionCcmail.JOB_POSITION_CCMAIL.UPDATE_TIME;
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
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getToEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getHrId();
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
    public JobPositionCcmailRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCcmailRecord value2(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCcmailRecord value3(String value) {
        setToEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCcmailRecord value4(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCcmailRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCcmailRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCcmailRecord values(Integer value1, Integer value2, String value3, Integer value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached JobPositionCcmailRecord
     */
    public JobPositionCcmailRecord() {
        super(JobPositionCcmail.JOB_POSITION_CCMAIL);
    }

    /**
     * Create a detached, initialised JobPositionCcmailRecord
     */
    public JobPositionCcmailRecord(Integer id, Integer positionId, String toEmail, Integer hrId, Timestamp createTime, Timestamp updateTime) {
        super(JobPositionCcmail.JOB_POSITION_CCMAIL);

        set(0, id);
        set(1, positionId);
        set(2, toEmail);
        set(3, hrId);
        set(4, createTime);
        set(5, updateTime);
    }
}
