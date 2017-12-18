/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrCmsModule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsModuleRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信端新jd内容模块配置项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCmsModuleDao extends DAOImpl<HrCmsModuleRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule, Integer> {

    /**
     * Create a new HrCmsModuleDao without any configuration
     */
    public HrCmsModuleDao() {
        super(HrCmsModule.HR_CMS_MODULE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule.class);
    }

    /**
     * Create a new HrCmsModuleDao with an attached configuration
     */
    public HrCmsModuleDao(Configuration configuration) {
        super(HrCmsModule.HR_CMS_MODULE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchById(Integer... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule fetchOneById(Integer value) {
        return fetchOne(HrCmsModule.HR_CMS_MODULE.ID, value);
    }

    /**
     * Fetch records that have <code>page_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByPageId(Integer... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.PAGE_ID, values);
    }

    /**
     * Fetch records that have <code>module_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByModuleName(String... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.MODULE_NAME, values);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByType(Integer... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.TYPE, values);
    }

    /**
     * Fetch records that have <code>orders IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByOrders(Integer... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.ORDERS, values);
    }

    /**
     * Fetch records that have <code>link IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByLink(String... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.LINK, values);
    }

    /**
     * Fetch records that have <code>limit IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByLimit(Integer... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.LIMIT, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByDisable(Integer... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.DISABLE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByCreateTime(Timestamp... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCmsModule> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrCmsModule.HR_CMS_MODULE.UPDATE_TIME, values);
    }
}
