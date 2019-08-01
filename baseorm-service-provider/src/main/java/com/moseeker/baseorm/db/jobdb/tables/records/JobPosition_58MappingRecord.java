/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobPosition_58Mapping;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class JobPosition_58MappingRecord extends UpdatableRecordImpl<JobPosition_58MappingRecord> implements Record8<Integer, Integer, Integer, Byte, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 906820974;

    /**
     * Setter for <code>jobdb.job_position_58_mapping.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.position_id</code>.
     */
    public void setPositionId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.position_id</code>.
     */
    public Integer getPositionId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.info_id</code>.
     */
    public void setInfoId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.info_id</code>.
     */
    public Integer getInfoId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.state</code>.
     */
    public void setState(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.state</code>.
     */
    public Byte getState() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.url</code>.
     */
    public void setUrl(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.url</code>.
     */
    public String getUrl() {
        return (String) get(4);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.errmsg</code>.
     */
    public void setErrmsg(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.errmsg</code>.
     */
    public String getErrmsg() {
        return (String) get(5);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>jobdb.job_position_58_mapping.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>jobdb.job_position_58_mapping.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Byte, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Byte, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.INFO_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.STATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.ERRMSG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return JobPosition_58Mapping.JOB_POSITION_58_MAPPING.UPDATE_TIME;
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
    public Integer value3() {
        return getInfoId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getErrmsg();
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
    public JobPosition_58MappingRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value2(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value3(Integer value) {
        setInfoId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value4(Byte value) {
        setState(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value5(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value6(String value) {
        setErrmsg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPosition_58MappingRecord values(Integer value1, Integer value2, Integer value3, Byte value4, String value5, String value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JobPosition_58MappingRecord
     */
    public JobPosition_58MappingRecord() {
        super(JobPosition_58Mapping.JOB_POSITION_58_MAPPING);
    }

    /**
     * Create a detached, initialised JobPosition_58MappingRecord
     */
    public JobPosition_58MappingRecord(Integer id, Integer positionId, Integer infoId, Byte state, String url, String errmsg, Timestamp createTime, Timestamp updateTime) {
        super(JobPosition_58Mapping.JOB_POSITION_58_MAPPING);

        set(0, id);
        set(1, positionId);
        set(2, infoId);
        set(3, state);
        set(4, url);
        set(5, errmsg);
        set(6, createTime);
        set(7, updateTime);
    }
}
