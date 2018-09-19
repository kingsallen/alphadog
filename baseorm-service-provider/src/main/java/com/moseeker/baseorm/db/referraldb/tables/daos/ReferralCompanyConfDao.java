/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.daos;


import com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralCompanyConfRecord;

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
public class ReferralCompanyConfDao extends DAOImpl<ReferralCompanyConfRecord, com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf, Integer> {

    /**
     * Create a new ReferralCompanyConfDao without any configuration
     */
    public ReferralCompanyConfDao() {
        super(ReferralCompanyConf.REFERRAL_COMPANY_CONF, com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf.class);
    }

    /**
     * Create a new ReferralCompanyConfDao with an attached configuration
     */
    public ReferralCompanyConfDao(Configuration configuration) {
        super(ReferralCompanyConf.REFERRAL_COMPANY_CONF, com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf> fetchById(Integer... values) {
        return fetch(ReferralCompanyConf.REFERRAL_COMPANY_CONF.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf fetchOneById(Integer value) {
        return fetchOne(ReferralCompanyConf.REFERRAL_COMPANY_CONF.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf> fetchByCompanyId(Integer... values) {
        return fetch(ReferralCompanyConf.REFERRAL_COMPANY_CONF.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>position_points_flag IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf> fetchByPositionPointsFlag(Byte... values) {
        return fetch(ReferralCompanyConf.REFERRAL_COMPANY_CONF.POSITION_POINTS_FLAG, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf> fetchByCreateTime(Timestamp... values) {
        return fetch(ReferralCompanyConf.REFERRAL_COMPANY_CONF.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf> fetchByUpdateTime(Timestamp... values) {
        return fetch(ReferralCompanyConf.REFERRAL_COMPANY_CONF.UPDATE_TIME, values);
    }
}
