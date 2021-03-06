/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 第三方渠道帐号
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccountDao extends DAOImpl<HrThirdPartyAccountRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount, Integer> {

    /**
     * Create a new HrThirdPartyAccountDao without any configuration
     */
    public HrThirdPartyAccountDao() {
        super(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount.class);
    }

    /**
     * Create a new HrThirdPartyAccountDao with an attached configuration
     */
    public HrThirdPartyAccountDao(Configuration configuration) {
        super(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchById(Integer... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount fetchOneById(Integer value) {
        return fetchOne(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID, value);
    }

    /**
     * Fetch records that have <code>channel IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByChannel(Short... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL, values);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByUsername(String... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.USERNAME, values);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByPassword(String... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.PASSWORD, values);
    }

    /**
     * Fetch records that have <code>binding IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByBinding(Short... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByCompanyId(Integer... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>remain_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByRemainNum(Integer... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.REMAIN_NUM, values);
    }

    /**
     * Fetch records that have <code>sync_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchBySyncTime(Timestamp... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.SYNC_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByCreateTime(Timestamp... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>remain_profile_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByRemainProfileNum(Integer... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.REMAIN_PROFILE_NUM, values);
    }

    /**
     * Fetch records that have <code>error_message IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByErrorMessage(String... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ERROR_MESSAGE, values);
    }

    /**
     * Fetch records that have <code>ext IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByExt(String... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT, values);
    }

    /**
     * Fetch records that have <code>sync_require_company IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchBySyncRequireCompany(Byte... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.SYNC_REQUIRE_COMPANY, values);
    }

    /**
     * Fetch records that have <code>sync_require_department IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchBySyncRequireDepartment(Byte... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.SYNC_REQUIRE_DEPARTMENT, values);
    }

    /**
     * Fetch records that have <code>ext2 IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByExt2(String... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2, values);
    }

    /**
     * Fetch records that have <code>template_sender IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrThirdPartyAccount> fetchByTemplateSender(Integer... values) {
        return fetch(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.TEMPLATE_SENDER, values);
    }
}
