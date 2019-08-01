/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.daos;


import com.moseeker.baseorm.db.jobdb.tables.JobPosition_58Mapping;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPosition_58MappingRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class JobPosition_58MappingDao extends DAOImpl<JobPosition_58MappingRecord, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping, Integer> {

    /**
     * Create a new JobPosition_58MappingDao without any configuration
     */
    public JobPosition_58MappingDao() {
        super(JobPosition_58Mapping.JOB_POSITION_58_MAPPING, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping.class);
    }

    /**
     * Create a new JobPosition_58MappingDao with an attached configuration
     */
    public JobPosition_58MappingDao(Configuration configuration) {
        super(JobPosition_58Mapping.JOB_POSITION_58_MAPPING, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchById(Integer... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping fetchOneById(Integer value) {
        return fetchOne(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.ID, value);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByPositionId(Integer... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>info_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByInfoId(Integer... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.INFO_ID, values);
    }

    /**
     * Fetch records that have <code>state IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByState(Byte... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.STATE, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByUrl(String... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.URL, values);
    }

    /**
     * Fetch records that have <code>errmsg IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByErrmsg(String... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.ERRMSG, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByCreateTime(Timestamp... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition_58Mapping> fetchByUpdateTime(Timestamp... values) {
        return fetch(JobPosition_58Mapping.JOB_POSITION_58_MAPPING.UPDATE_TIME, values);
    }
}