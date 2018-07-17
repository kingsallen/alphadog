/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserEmployeeReferralPolicy;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeReferralPolicyRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 员工想要了解内推政策点击次数表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeeReferralPolicyDao extends DAOImpl<UserEmployeeReferralPolicyRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy, Integer> {

    /**
     * Create a new UserEmployeeReferralPolicyDao without any configuration
     */
    public UserEmployeeReferralPolicyDao() {
        super(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy.class);
    }

    /**
     * Create a new UserEmployeeReferralPolicyDao with an attached configuration
     */
    public UserEmployeeReferralPolicyDao(Configuration configuration) {
        super(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy> fetchById(Integer... values) {
        return fetch(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy fetchOneById(Integer value) {
        return fetchOne(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.ID, value);
    }

    /**
     * Fetch records that have <code>employee_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy> fetchByEmployeeId(Integer... values) {
        return fetch(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.EMPLOYEE_ID, values);
    }

    /**
     * Fetch records that have <code>count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeeReferralPolicy> fetchByCount(Integer... values) {
        return fetch(UserEmployeeReferralPolicy.USER_EMPLOYEE_REFERRAL_POLICY.COUNT, values);
    }
}
