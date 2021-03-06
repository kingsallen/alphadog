/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrHeadhunterOmsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHeadhunterOmsConfRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 猎头管理OMS配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHeadhunterOmsConfDao extends DAOImpl<HrHeadhunterOmsConfRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf, Integer> {

    /**
     * Create a new HrHeadhunterOmsConfDao without any configuration
     */
    public HrHeadhunterOmsConfDao() {
        super(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf.class);
    }

    /**
     * Create a new HrHeadhunterOmsConfDao with an attached configuration
     */
    public HrHeadhunterOmsConfDao(Configuration configuration) {
        super(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchById(Integer... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf fetchOneById(Integer value) {
        return fetchOne(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchByCompanyId(Integer... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.COMPANY_ID, values);
    }

    /**
     * Fetch a unique record that has <code>company_id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf fetchOneByCompanyId(Integer value) {
        return fetchOne(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.COMPANY_ID, value);
    }

    /**
     * Fetch records that have <code>channel_count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchByChannelCount(Integer... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.CHANNEL_COUNT, values);
    }

    /**
     * Fetch records that have <code>channel_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchByChannelStatus(Byte... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.CHANNEL_STATUS, values);
    }

    /**
     * Fetch records that have <code>login_url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchByLoginUrl(String... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.LOGIN_URL, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchByCreateTime(Timestamp... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHeadhunterOmsConf> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrHeadhunterOmsConf.HR_HEADHUNTER_OMS_CONF.UPDATE_TIME, values);
    }
}
