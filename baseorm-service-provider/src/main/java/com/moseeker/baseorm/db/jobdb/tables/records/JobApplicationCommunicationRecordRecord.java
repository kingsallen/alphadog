/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobApplicationCommunicationRecord;

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
public class JobApplicationCommunicationRecordRecord extends UpdatableRecordImpl<JobApplicationCommunicationRecordRecord> implements Record6<Integer, String, Byte, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1300597307;

    /**
     * Setter for <code>jobdb.job_application_communication_record.id</code>. primaryKey
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_application_communication_record.id</code>. primaryKey
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_application_communication_record.content</code>. 备注内容
     */
    public void setContent(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_application_communication_record.content</code>. 备注内容
     */
    public String getContent() {
        return (String) get(1);
    }

    /**
     * Setter for <code>jobdb.job_application_communication_record.creator_type</code>. 1:userdb.user_hr_account
     */
    public void setCreatorType(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>jobdb.job_application_communication_record.creator_type</code>. 1:userdb.user_hr_account
     */
    public Byte getCreatorType() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>jobdb.job_application_communication_record.creator_id</code>. 备注创建人id,对应的创建人类型存储对应的创建人id
     */
    public void setCreatorId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>jobdb.job_application_communication_record.creator_id</code>. 备注创建人id,对应的创建人类型存储对应的创建人id
     */
    public Integer getCreatorId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>jobdb.job_application_communication_record.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>jobdb.job_application_communication_record.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>jobdb.job_application_communication_record.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>jobdb.job_application_communication_record.update_time</code>. 更新时间
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
    public Row6<Integer, String, Byte, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, Byte, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD.CREATOR_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD.CREATOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD.UPDATE_TIME;
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
    public String value2() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getCreatorType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCreatorId();
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
    public JobApplicationCommunicationRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationRecordRecord value2(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationRecordRecord value3(Byte value) {
        setCreatorType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationRecordRecord value4(Integer value) {
        setCreatorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationRecordRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationRecordRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationRecordRecord values(Integer value1, String value2, Byte value3, Integer value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached JobApplicationCommunicationRecordRecord
     */
    public JobApplicationCommunicationRecordRecord() {
        super(JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD);
    }

    /**
     * Create a detached, initialised JobApplicationCommunicationRecordRecord
     */
    public JobApplicationCommunicationRecordRecord(Integer id, String content, Byte creatorType, Integer creatorId, Timestamp createTime, Timestamp updateTime) {
        super(JobApplicationCommunicationRecord.JOB_APPLICATION_COMMUNICATION_RECORD);

        set(0, id);
        set(1, content);
        set(2, creatorType);
        set(3, creatorId);
        set(4, createTime);
        set(5, updateTime);
    }
}