/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserReferralRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserReferralRecordRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 员工主动推荐记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserReferralRecordDao extends DAOImpl<UserReferralRecordRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord, Integer> {

    /**
     * Create a new UserReferralRecordDao without any configuration
     */
    public UserReferralRecordDao() {
        super(UserReferralRecord.USER_REFERRAL_RECORD, com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord.class);
    }

    /**
     * Create a new UserReferralRecordDao with an attached configuration
     */
    public UserReferralRecordDao(Configuration configuration) {
        super(UserReferralRecord.USER_REFERRAL_RECORD, com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord> fetchById(Integer... values) {
        return fetch(UserReferralRecord.USER_REFERRAL_RECORD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord fetchOneById(Integer value) {
        return fetchOne(UserReferralRecord.USER_REFERRAL_RECORD.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord> fetchByUserId(Integer... values) {
        return fetch(UserReferralRecord.USER_REFERRAL_RECORD.USER_ID, values);
    }

    /**
     * Fetch records that have <code>reference_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord> fetchByReferenceId(Integer... values) {
        return fetch(UserReferralRecord.USER_REFERRAL_RECORD.REFERENCE_ID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord> fetchByCompanyId(Integer... values) {
        return fetch(UserReferralRecord.USER_REFERRAL_RECORD.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord> fetchByCreateTime(Timestamp... values) {
        return fetch(UserReferralRecord.USER_REFERRAL_RECORD.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserReferralRecord.USER_REFERRAL_RECORD.UPDATE_TIME, values);
    }
}
