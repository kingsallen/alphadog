/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.jobdb.tables.records;


import com.moseeker.db.jobdb.tables.JobOccupationRel;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 职位与职能关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobOccupationRelRecord extends UpdatableRecordImpl<JobOccupationRelRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1811506874;

    /**
     * Setter for <code>jobdb.job_occupation_rel.pid</code>. 职位id
     */
    public void setPid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_occupation_rel.pid</code>. 职位id
     */
    public Integer getPid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_occupation_rel.code</code>. 职位职能code
     */
    public void setCode(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_occupation_rel.code</code>. 职位职能code
     */
    public Integer getCode() {
        return (Integer) get(1);
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
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<Integer, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return JobOccupationRel.JOB_OCCUPATION_REL.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JobOccupationRel.JOB_OCCUPATION_REL.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobOccupationRelRecord value1(Integer value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobOccupationRelRecord value2(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobOccupationRelRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JobOccupationRelRecord
     */
    public JobOccupationRelRecord() {
        super(JobOccupationRel.JOB_OCCUPATION_REL);
    }

    /**
     * Create a detached, initialised JobOccupationRelRecord
     */
    public JobOccupationRelRecord(Integer pid, Integer code) {
        super(JobOccupationRel.JOB_OCCUPATION_REL);

        set(0, pid);
        set(1, code);
    }
}
