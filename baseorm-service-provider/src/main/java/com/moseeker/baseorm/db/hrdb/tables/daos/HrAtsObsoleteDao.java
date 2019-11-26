/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrAtsObsolete;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAtsObsoleteRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * hr淘汰候选人记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsObsoleteDao extends DAOImpl<HrAtsObsoleteRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete, Integer> {

    /**
     * Create a new HrAtsObsoleteDao without any configuration
     */
    public HrAtsObsoleteDao() {
        super(HrAtsObsolete.HR_ATS_OBSOLETE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete.class);
    }

    /**
     * Create a new HrAtsObsoleteDao with an attached configuration
     */
    public HrAtsObsoleteDao(Configuration configuration) {
        super(HrAtsObsolete.HR_ATS_OBSOLETE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchById(Integer... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete fetchOneById(Integer value) {
        return fetchOne(HrAtsObsolete.HR_ATS_OBSOLETE.ID, value);
    }

    /**
     * Fetch records that have <code>application_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByApplicationId(Integer... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.APPLICATION_ID, values);
    }

    /**
     * Fetch records that have <code>reason_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByReasonCode(Integer... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.REASON_CODE, values);
    }

    /**
     * Fetch records that have <code>reason IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByReason(String... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.REASON, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByRemark(String... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.REMARK, values);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByHrId(Integer... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.HR_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByCreateTime(Timestamp... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsObsolete> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrAtsObsolete.HR_ATS_OBSOLETE.UPDATE_TIME, values);
    }
}