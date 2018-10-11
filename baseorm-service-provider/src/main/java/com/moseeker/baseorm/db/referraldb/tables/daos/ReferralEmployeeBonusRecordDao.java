/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.daos;


import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeBonusRecordRecord;

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
public class ReferralEmployeeBonusRecordDao extends DAOImpl<ReferralEmployeeBonusRecordRecord, com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord, Integer> {

    /**
     * Create a new ReferralEmployeeBonusRecordDao without any configuration
     */
    public ReferralEmployeeBonusRecordDao() {
        super(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD, com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord.class);
    }

    /**
     * Create a new ReferralEmployeeBonusRecordDao with an attached configuration
     */
    public ReferralEmployeeBonusRecordDao(Configuration configuration) {
        super(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD, com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchById(Integer... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord fetchOneById(Integer value) {
        return fetchOne(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.ID, value);
    }

    /**
     * Fetch records that have <code>employee_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByEmployeeId(Integer... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.EMPLOYEE_ID, values);
    }

    /**
     * Fetch records that have <code>application_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByApplicationId(Integer... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.APPLICATION_ID, values);
    }

    /**
     * Fetch records that have <code>bonus_stage_detail_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByBonusStageDetailId(Integer... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS_STAGE_DETAIL_ID, values);
    }

    /**
     * Fetch records that have <code>bonus IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByBonus(Integer... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.BONUS, values);
    }

    /**
     * Fetch records that have <code>claim IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByClaim(Byte... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.CLAIM, values);
    }

    /**
     * Fetch records that have <code>claim_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByClaimTime(Timestamp... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.CLAIM_TIME, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByCreateTime(Timestamp... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByUpdateTime(Timestamp... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord> fetchByDisable(Integer... values) {
        return fetch(ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD.DISABLE, values);
    }
}
