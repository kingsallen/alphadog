/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.daos;


import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysPointsConfTplRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 积分配置模板表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysPointsConfTplDao extends DAOImpl<ConfigSysPointsConfTplRecord, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl, Integer> {

    /**
     * Create a new ConfigSysPointsConfTplDao without any configuration
     */
    public ConfigSysPointsConfTplDao() {
        super(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl.class);
    }

    /**
     * Create a new ConfigSysPointsConfTplDao with an attached configuration
     */
    public ConfigSysPointsConfTplDao(Configuration configuration) {
        super(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchById(Integer... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl fetchOneById(Integer value) {
        return fetchOne(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID, value);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByStatus(String... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.STATUS, values);
    }

    /**
     * Fetch records that have <code>award IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByAward(Integer... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.AWARD, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByDescription(String... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByDisable(Integer... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.DISABLE, values);
    }

    /**
     * Fetch records that have <code>priority IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByPriority(Integer... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>type_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByTypeId(Integer... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.TYPE_ID, values);
    }

    /**
     * Fetch records that have <code>tag IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByTag(Byte... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.TAG, values);
    }

    /**
     * Fetch records that have <code>is_init_award IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByIsInitAward(Byte... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.IS_INIT_AWARD, values);
    }

    /**
     * Fetch records that have <code>recruit_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByRecruitOrder(Integer... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER, values);
    }

    /**
     * Fetch records that have <code>applier_view IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysPointsConfTpl> fetchByApplierView(String... values) {
        return fetch(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.APPLIER_VIEW, values);
    }
}