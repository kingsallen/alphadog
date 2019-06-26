/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewAddress;
import com.moseeker.baseorm.db.hrdb.tables.records.HrInterviewAddressRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 面试地址表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewAddressDao extends DAOImpl<HrInterviewAddressRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress, Integer> {

    /**
     * Create a new HrInterviewAddressDao without any configuration
     */
    public HrInterviewAddressDao() {
        super(HrInterviewAddress.HR_INTERVIEW_ADDRESS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress.class);
    }

    /**
     * Create a new HrInterviewAddressDao with an attached configuration
     */
    public HrInterviewAddressDao(Configuration configuration) {
        super(HrInterviewAddress.HR_INTERVIEW_ADDRESS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchById(Integer... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress fetchOneById(Integer value) {
        return fetchOne(HrInterviewAddress.HR_INTERVIEW_ADDRESS.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByCompanyId(Integer... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByHrId(Integer... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.HR_ID, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByName(String... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.NAME, values);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByAddress(String... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.ADDRESS, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByDisable(Integer... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.DISABLE, values);
    }

    /**
     * Fetch records that have <code>default_flag IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByDefaultFlag(Integer... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.DEFAULT_FLAG, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByCreateTime(Timestamp... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>snapshot IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrInterviewAddress> fetchBySnapshot(Integer... values) {
        return fetch(HrInterviewAddress.HR_INTERVIEW_ADDRESS.SNAPSHOT, values);
    }
}
