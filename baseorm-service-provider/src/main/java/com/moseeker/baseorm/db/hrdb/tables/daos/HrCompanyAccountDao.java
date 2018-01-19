/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 账号公司关联记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyAccountDao extends DAOImpl<HrCompanyAccountRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount, Integer> {

    /**
     * Create a new HrCompanyAccountDao without any configuration
     */
    public HrCompanyAccountDao() {
        super(HrCompanyAccount.HR_COMPANY_ACCOUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount.class);
    }

    /**
     * Create a new HrCompanyAccountDao with an attached configuration
     */
    public HrCompanyAccountDao(Configuration configuration) {
        super(HrCompanyAccount.HR_COMPANY_ACCOUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount object) {
        return object.getAccountId();
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount> fetchByCompanyId(Integer... values) {
        return fetch(HrCompanyAccount.HR_COMPANY_ACCOUNT.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>account_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount> fetchByAccountId(Integer... values) {
        return fetch(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID, values);
    }

    /**
     * Fetch a unique record that has <code>account_id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount fetchOneByAccountId(Integer value) {
        return fetchOne(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID, value);
    }
}
