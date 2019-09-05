/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyWorkwxConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyWorkwxConfRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 企业微信配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyWorkwxConfDao extends DAOImpl<HrCompanyWorkwxConfRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf, Integer> {

    /**
     * Create a new HrCompanyWorkwxConfDao without any configuration
     */
    public HrCompanyWorkwxConfDao() {
        super(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf.class);
    }

    /**
     * Create a new HrCompanyWorkwxConfDao with an attached configuration
     */
    public HrCompanyWorkwxConfDao(Configuration configuration) {
        super(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchById(Integer... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf fetchOneById(Integer value) {
        return fetchOne(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByCompanyId(Integer... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>corpid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByCorpid(String... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.CORPID, values);
    }

    /**
     * Fetch records that have <code>secret IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchBySecret(String... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.SECRET, values);
    }

    /**
     * Fetch records that have <code>access_token IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByAccessToken(String... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.ACCESS_TOKEN, values);
    }

    /**
     * Fetch records that have <code>token_update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByTokenUpdateTime(Timestamp... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.TOKEN_UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>token_expire_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByTokenExpireTime(Timestamp... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.TOKEN_EXPIRE_TIME, values);
    }

    /**
     * Fetch records that have <code>error_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByErrorCode(Integer... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.ERROR_CODE, values);
    }

    /**
     * Fetch records that have <code>error_msg IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByErrorMsg(String... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.ERROR_MSG, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByCreateTime(Timestamp... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>jsapi_ticket IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByJsapiTicket(String... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.JSAPI_TICKET, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyWorkwxConf> fetchByDisable(Byte... values) {
        return fetch(HrCompanyWorkwxConf.HR_COMPANY_WORKWX_CONF.DISABLE, values);
    }
}
