/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.ConfigSysCvTpl180726;
import com.moseeker.baseorm.db.historydb.tables.records.ConfigSysCvTpl180726Record;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 简历模板库
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysCvTpl180726Dao extends DAOImpl<ConfigSysCvTpl180726Record, com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726, Integer> {

    /**
     * Create a new ConfigSysCvTpl180726Dao without any configuration
     */
    public ConfigSysCvTpl180726Dao() {
        super(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726, com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726.class);
    }

    /**
     * Create a new ConfigSysCvTpl180726Dao with an attached configuration
     */
    public ConfigSysCvTpl180726Dao(Configuration configuration) {
        super(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726, com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726 object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchById(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726 fetchOneById(Integer value) {
        return fetchOne(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.ID, value);
    }

    /**
     * Fetch records that have <code>field_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByFieldName(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.FIELD_NAME, values);
    }

    /**
     * Fetch a unique record that has <code>field_name = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726 fetchOneByFieldName(String value) {
        return fetchOne(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.FIELD_NAME, value);
    }

    /**
     * Fetch records that have <code>field_title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByFieldTitle(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.FIELD_TITLE, values);
    }

    /**
     * Fetch records that have <code>field_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByFieldType(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.FIELD_TYPE, values);
    }

    /**
     * Fetch records that have <code>priority IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByPriority(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>is_basic IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByIsBasic(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.IS_BASIC, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByCreateTime(Timestamp... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByUpdateTime(Timestamp... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByDisable(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.DISABLE, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByCompanyId(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>required IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByRequired(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.REQUIRED, values);
    }

    /**
     * Fetch records that have <code>field_description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByFieldDescription(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.FIELD_DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>map IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByMap(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.MAP, values);
    }

    /**
     * Fetch records that have <code>constant_parent_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByConstantParentCode(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.CONSTANT_PARENT_CODE, values);
    }

    /**
     * Fetch records that have <code>parent_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByParentId(Integer... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.PARENT_ID, values);
    }

    /**
     * Fetch records that have <code>validate_re IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByValidateRe(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.VALIDATE_RE, values);
    }

    /**
     * Fetch records that have <code>error_msg IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByErrorMsg(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.ERROR_MSG, values);
    }

    /**
     * Fetch records that have <code>field_value IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ConfigSysCvTpl180726> fetchByFieldValue(String... values) {
        return fetch(ConfigSysCvTpl180726.CONFIG_SYS_CV_TPL180726.FIELD_VALUE, values);
    }
}
