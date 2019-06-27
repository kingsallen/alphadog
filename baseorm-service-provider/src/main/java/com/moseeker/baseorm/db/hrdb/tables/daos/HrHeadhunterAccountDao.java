/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrHeadhunterAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHeadhunterAccountRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 猎头渠道表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHeadhunterAccountDao extends DAOImpl<HrHeadhunterAccountRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount, Integer> {

    /**
     * Create a new HrHeadhunterAccountDao without any configuration
     */
    public HrHeadhunterAccountDao() {
        super(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount.class);
    }

    /**
     * Create a new HrHeadhunterAccountDao with an attached configuration
     */
    public HrHeadhunterAccountDao(Configuration configuration) {
        super(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchById(Integer... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount fetchOneById(Integer value) {
        return fetchOne(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.ID, value);
    }

    /**
     * Fetch records that have <code>email IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByEmail(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.EMAIL, values);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByPassword(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.PASSWORD, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByCompanyId(Integer... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByHrId(Integer... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.HR_ID, values);
    }

    /**
     * Fetch records that have <code>hr_company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByHrCompanyId(Integer... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.HR_COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByUsername(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.USERNAME, values);
    }

    /**
     * Fetch records that have <code>service_start_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByServiceStartTime(Timestamp... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.SERVICE_START_TIME, values);
    }

    /**
     * Fetch records that have <code>service_end_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByServiceEndTime(Timestamp... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.SERVICE_END_TIME, values);
    }

    /**
     * Fetch records that have <code>contract_file_path IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByContractFilePath(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.CONTRACT_FILE_PATH, values);
    }

    /**
     * Fetch records that have <code>contract_file_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByContractFileName(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.CONTRACT_FILE_NAME, values);
    }

    /**
     * Fetch records that have <code>contract_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByContractType(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.CONTRACT_TYPE, values);
    }

    /**
     * Fetch records that have <code>fee_rate IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByFeeRate(BigDecimal... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.FEE_RATE, values);
    }

    /**
     * Fetch records that have <code>term_of_protection IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByTermOfProtection(Integer... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.TERM_OF_PROTECTION, values);
    }

    /**
     * Fetch records that have <code>mobile IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByMobile(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.MOBILE, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByRemark(String... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.REMARK, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByStatus(Byte... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.STATUS, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByCreateTime(Timestamp... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterAccount> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrHeadhunterAccount.HR_HEADHUNTER_ACCOUNT.UPDATE_TIME, values);
    }
}
