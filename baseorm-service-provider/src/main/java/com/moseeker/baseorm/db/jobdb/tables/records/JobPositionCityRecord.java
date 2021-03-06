/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;
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
public class JobPositionCityRecord extends UpdatableRecordImpl<JobPositionCityRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = -324004411;

    /**
     * Setter for <code>jobdb.job_position_city.pid</code>. 职位id
     */
    public void setPid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_position_city.pid</code>. 职位id
     */
    public Integer getPid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_position_city.code</code>. 城市code
     */
    public void setCode(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_position_city.code</code>. 城市code
     */
    public Integer getCode() {
        return (Integer) get(1);
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
        return JobPositionCity.JOB_POSITION_CITY.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JobPositionCity.JOB_POSITION_CITY.CODE;
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
    public JobPositionCityRecord value1(Integer value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCityRecord value2(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionCityRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JobPositionCityRecord
     */
    public JobPositionCityRecord() {
        super(JobPositionCity.JOB_POSITION_CITY);
    }

    /**
     * Create a detached, initialised JobPositionCityRecord
     */
    public JobPositionCityRecord(Integer pid, Integer code) {
        super(JobPositionCity.JOB_POSITION_CITY);

        set(0, pid);
        set(1, code);
    }
}
