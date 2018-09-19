/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobPositionHrCompanyFeature;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * 职位福利特色-关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionHrCompanyFeatureRecord extends UpdatableRecordImpl<JobPositionHrCompanyFeatureRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = -960114391;

    /**
     * Setter for <code>jobdb.job_position_hr_company_feature.pid</code>. 职位id
     */
    public void setPid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_position_hr_company_feature.pid</code>. 职位id
     */
    public Integer getPid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_position_hr_company_feature.fid</code>. 福利特色id
     */
    public void setFid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_position_hr_company_feature.fid</code>. 福利特色id
     */
    public Integer getFid() {
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
        return JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE.FID;
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
        return getFid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionHrCompanyFeatureRecord value1(Integer value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionHrCompanyFeatureRecord value2(Integer value) {
        setFid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionHrCompanyFeatureRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JobPositionHrCompanyFeatureRecord
     */
    public JobPositionHrCompanyFeatureRecord() {
        super(JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE);
    }

    /**
     * Create a detached, initialised JobPositionHrCompanyFeatureRecord
     */
    public JobPositionHrCompanyFeatureRecord(Integer pid, Integer fid) {
        super(JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE);

        set(0, pid);
        set(1, fid);
    }
}
