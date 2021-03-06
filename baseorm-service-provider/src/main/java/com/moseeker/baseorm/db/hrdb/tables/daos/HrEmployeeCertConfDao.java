/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCertConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 部门员工配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCertConfDao extends DAOImpl<HrEmployeeCertConfRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf, Integer> {

    /**
     * Create a new HrEmployeeCertConfDao without any configuration
     */
    public HrEmployeeCertConfDao() {
        super(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf.class);
    }

    /**
     * Create a new HrEmployeeCertConfDao with an attached configuration
     */
    public HrEmployeeCertConfDao(Configuration configuration) {
        super(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchById(Integer... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf fetchOneById(Integer value) {
        return fetchOne(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByCompanyId(Integer... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>is_strict IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByIsStrict(Byte... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.IS_STRICT, values);
    }

    /**
     * Fetch records that have <code>email_suffix IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByEmailSuffix(String... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.EMAIL_SUFFIX, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByCreateTime(Timestamp... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByDisable(Byte... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.DISABLE, values);
    }

    /**
     * Fetch records that have <code>bd_add_group IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByBdAddGroup(Byte... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.BD_ADD_GROUP, values);
    }

    /**
     * Fetch records that have <code>bd_use_group_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByBdUseGroupId(Integer... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.BD_USE_GROUP_ID, values);
    }

    /**
     * Fetch records that have <code>auth_mode IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByAuthMode(Byte... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.AUTH_MODE, values);
    }

    /**
     * Fetch records that have <code>auth_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByAuthCode(String... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.AUTH_CODE, values);
    }

    /**
     * Fetch records that have <code>custom IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByCustom(String... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.CUSTOM, values);
    }

    /**
     * Fetch records that have <code>questions IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByQuestions(String... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.QUESTIONS, values);
    }

    /**
     * Fetch records that have <code>custom_hint IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCertConf> fetchByCustomHint(String... values) {
        return fetch(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.CUSTOM_HINT, values);
    }
}
