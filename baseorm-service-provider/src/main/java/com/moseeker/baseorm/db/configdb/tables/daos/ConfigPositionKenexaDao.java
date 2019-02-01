/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.daos;


import com.moseeker.baseorm.db.configdb.tables.ConfigPositionKenexa;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigPositionKenexaRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * kenexa职位字段映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigPositionKenexaDao extends DAOImpl<ConfigPositionKenexaRecord, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa, Integer> {

    /**
     * Create a new ConfigPositionKenexaDao without any configuration
     */
    public ConfigPositionKenexaDao() {
        super(ConfigPositionKenexa.CONFIG_POSITION_KENEXA, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa.class);
    }

    /**
     * Create a new ConfigPositionKenexaDao with an attached configuration
     */
    public ConfigPositionKenexaDao(Configuration configuration) {
        super(ConfigPositionKenexa.CONFIG_POSITION_KENEXA, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchById(Integer... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa fetchOneById(Integer value) {
        return fetchOne(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.ID, value);
    }

    /**
     * Fetch records that have <code>source_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchBySourceId(Integer... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.SOURCE_ID, values);
    }

    /**
     * Fetch records that have <code>position_column IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchByPositionColumn(String... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.POSITION_COLUMN, values);
    }

    /**
     * Fetch records that have <code>kenexa_job_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchByKenexaJobId(String... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.KENEXA_JOB_ID, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchByDisable(Integer... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.DISABLE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchByCreateTime(Timestamp... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchByUpdateTime(Timestamp... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>app_tpl_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigPositionKenexa> fetchByAppTplId(Integer... values) {
        return fetch(ConfigPositionKenexa.CONFIG_POSITION_KENEXA.APP_TPL_ID, values);
    }
}
