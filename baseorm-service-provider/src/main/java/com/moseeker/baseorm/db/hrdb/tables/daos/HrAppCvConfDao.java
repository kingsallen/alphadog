/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrAppCvConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppCvConfRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 企业申请简历校验配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAppCvConfDao extends DAOImpl<HrAppCvConfRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf, Integer> {

    /**
     * Create a new HrAppCvConfDao without any configuration
     */
    public HrAppCvConfDao() {
        super(HrAppCvConf.HR_APP_CV_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf.class);
    }

    /**
     * Create a new HrAppCvConfDao with an attached configuration
     */
    public HrAppCvConfDao(Configuration configuration) {
        super(HrAppCvConf.HR_APP_CV_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchById(Integer... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf fetchOneById(Integer value) {
        return fetchOne(HrAppCvConf.HR_APP_CV_CONF.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByName(String... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.NAME, values);
    }

    /**
     * Fetch records that have <code>priority IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByPriority(Integer... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByCreateTime(Timestamp... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByDisable(Integer... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.DISABLE, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByCompanyId(Integer... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>hraccount_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByHraccountId(Integer... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.HRACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>required IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByRequired(Integer... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.REQUIRED, values);
    }

    /**
     * Fetch records that have <code>field_value IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppCvConf> fetchByFieldValue(String... values) {
        return fetch(HrAppCvConf.HR_APP_CV_CONF.FIELD_VALUE, values);
    }
}
