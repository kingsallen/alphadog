/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.daos;


import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionExtRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 职位信息扩展表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionExtDao extends DAOImpl<JobPositionExtRecord, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt, Integer> {

    /**
     * Create a new JobPositionExtDao without any configuration
     */
    public JobPositionExtDao() {
        super(JobPositionExt.JOB_POSITION_EXT, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt.class);
    }

    /**
     * Create a new JobPositionExtDao with an attached configuration
     */
    public JobPositionExtDao(Configuration configuration) {
        super(JobPositionExt.JOB_POSITION_EXT, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt object) {
        return object.getPid();
    }

    /**
     * Fetch records that have <code>pid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByPid(Integer... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.PID, values);
    }

    /**
     * Fetch a unique record that has <code>pid = value</code>
     */
    public com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt fetchOneByPid(Integer value) {
        return fetchOne(JobPositionExt.JOB_POSITION_EXT.PID, value);
    }

    /**
     * Fetch records that have <code>job_custom_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByJobCustomId(Integer... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.JOB_CUSTOM_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByCreateTime(Timestamp... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByUpdateTime(Timestamp... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>extra IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByExtra(String... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.EXTRA, values);
    }

    /**
     * Fetch records that have <code>job_occupation_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByJobOccupationId(Integer... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.JOB_OCCUPATION_ID, values);
    }

    /**
     * Fetch records that have <code>alipay_job_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByAlipayJobId(Integer... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.ALIPAY_JOB_ID, values);
    }

    /**
     * Fetch records that have <code>ext IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionExt> fetchByExt(String... values) {
        return fetch(JobPositionExt.JOB_POSITION_EXT.EXT, values);
    }
}
