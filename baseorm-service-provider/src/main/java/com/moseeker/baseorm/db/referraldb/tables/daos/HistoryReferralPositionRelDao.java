/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.daos;


import com.moseeker.baseorm.db.referraldb.tables.HistoryReferralPositionRel;
import com.moseeker.baseorm.db.referraldb.tables.records.HistoryReferralPositionRelRecord;

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
public class HistoryReferralPositionRelDao extends DAOImpl<HistoryReferralPositionRelRecord, com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel, Integer> {

    /**
     * Create a new HistoryReferralPositionRelDao without any configuration
     */
    public HistoryReferralPositionRelDao() {
        super(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL, com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel.class);
    }

    /**
     * Create a new HistoryReferralPositionRelDao with an attached configuration
     */
    public HistoryReferralPositionRelDao(Configuration configuration) {
        super(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL, com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel> fetchById(Integer... values) {
        return fetch(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel fetchOneById(Integer value) {
        return fetchOne(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.ID, value);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel> fetchByPositionId(Integer... values) {
        return fetch(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel> fetchByCompanyId(Integer... values) {
        return fetch(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>record_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel> fetchByRecordType(String... values) {
        return fetch(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.RECORD_TYPE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel> fetchByCreateTime(Timestamp... values) {
        return fetch(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.HistoryReferralPositionRel> fetchByUpdateTime(Timestamp... values) {
        return fetch(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.UPDATE_TIME, values);
    }
}