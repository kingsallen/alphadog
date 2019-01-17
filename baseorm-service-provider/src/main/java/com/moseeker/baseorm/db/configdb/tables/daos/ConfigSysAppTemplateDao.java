/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.daos;


import com.moseeker.baseorm.db.configdb.tables.ConfigSysAppTemplate;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysAppTemplateRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 申请字段模板表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysAppTemplateDao extends DAOImpl<ConfigSysAppTemplateRecord, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate, Integer> {

    /**
     * Create a new ConfigSysAppTemplateDao without any configuration
     */
    public ConfigSysAppTemplateDao() {
        super(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate.class);
    }

    /**
     * Create a new ConfigSysAppTemplateDao with an attached configuration
     */
    public ConfigSysAppTemplateDao(Configuration configuration) {
        super(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchById(Integer... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate fetchOneById(Integer value) {
        return fetchOne(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ID, value);
    }

    /**
     * Fetch records that have <code>enname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByEnname(String... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ENNAME, values);
    }

    /**
     * Fetch records that have <code>chname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByChname(String... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.CHNAME, values);
    }

    /**
     * Fetch records that have <code>priority IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByPriority(Byte... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>display IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByDisplay(Byte... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.DISPLAY, values);
    }

    /**
     * Fetch records that have <code>required IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByRequired(Byte... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.REQUIRED, values);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByType(Byte... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.TYPE, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByRemark(String... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.REMARK, values);
    }

    /**
     * Fetch records that have <code>entitle IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByEntitle(String... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.ENTITLE, values);
    }

    /**
     * Fetch records that have <code>parent_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigSysAppTemplate> fetchByParentId(Integer... values) {
        return fetch(ConfigSysAppTemplate.CONFIG_SYS_APP_TEMPLATE.PARENT_ID, values);
    }
}
