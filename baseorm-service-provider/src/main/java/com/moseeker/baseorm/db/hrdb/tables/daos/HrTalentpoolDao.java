/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrTalentpool;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 人才库
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTalentpoolDao extends DAOImpl<HrTalentpoolRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool, Integer> {

    /**
     * Create a new HrTalentpoolDao without any configuration
     */
    public HrTalentpoolDao() {
        super(HrTalentpool.HR_TALENTPOOL, com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool.class);
    }

    /**
     * Create a new HrTalentpoolDao with an attached configuration
     */
    public HrTalentpoolDao(Configuration configuration) {
        super(HrTalentpool.HR_TALENTPOOL, com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool> fetchById(Integer... values) {
        return fetch(HrTalentpool.HR_TALENTPOOL.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool fetchOneById(Integer value) {
        return fetchOne(HrTalentpool.HR_TALENTPOOL.ID, value);
    }

    /**
     * Fetch records that have <code>hr_account_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool> fetchByHrAccountId(Integer... values) {
        return fetch(HrTalentpool.HR_TALENTPOOL.HR_ACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>applier_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool> fetchByApplierId(Integer... values) {
        return fetch(HrTalentpool.HR_TALENTPOOL.APPLIER_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool> fetchByCreateTime(Timestamp... values) {
        return fetch(HrTalentpool.HR_TALENTPOOL.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrTalentpool.HR_TALENTPOOL.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpool> fetchByStatus(Integer... values) {
        return fetch(HrTalentpool.HR_TALENTPOOL.STATUS, values);
    }
}
