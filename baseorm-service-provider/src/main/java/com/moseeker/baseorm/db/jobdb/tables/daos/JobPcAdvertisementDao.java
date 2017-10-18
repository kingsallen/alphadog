/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.daos;


import com.moseeker.baseorm.db.jobdb.tables.JobPcAdvertisement;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcAdvertisementRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 首页广告位数据表设计
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPcAdvertisementDao extends DAOImpl<JobPcAdvertisementRecord, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement, Integer> {

    /**
     * Create a new JobPcAdvertisementDao without any configuration
     */
    public JobPcAdvertisementDao() {
        super(JobPcAdvertisement.JOB_PC_ADVERTISEMENT, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement.class);
    }

    /**
     * Create a new JobPcAdvertisementDao with an attached configuration
     */
    public JobPcAdvertisementDao(Configuration configuration) {
        super(JobPcAdvertisement.JOB_PC_ADVERTISEMENT, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchById(Integer... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement fetchOneById(Integer value) {
        return fetchOne(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.ID, value);
    }

    /**
     * Fetch records that have <code>img IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByImg(String... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.IMG, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByName(String... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.NAME, values);
    }

    /**
     * Fetch records that have <code>href IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByHref(String... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.HREF, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByStatus(Byte... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.STATUS, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByDescription(String... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByCreateTime(Timestamp... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPcAdvertisement> fetchByUpdateTime(Timestamp... values) {
        return fetch(JobPcAdvertisement.JOB_PC_ADVERTISEMENT.UPDATE_TIME, values);
    }
}