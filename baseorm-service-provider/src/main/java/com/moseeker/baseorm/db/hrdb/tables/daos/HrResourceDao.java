/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.records.HrResourceRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 资源集合表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrResourceDao extends DAOImpl<HrResourceRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource, Integer> {

    /**
     * Create a new HrResourceDao without any configuration
     */
    public HrResourceDao() {
        super(HrResource.HR_RESOURCE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource.class);
    }

    /**
     * Create a new HrResourceDao with an attached configuration
     */
    public HrResourceDao(Configuration configuration) {
        super(HrResource.HR_RESOURCE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchById(Integer... values) {
        return fetch(HrResource.HR_RESOURCE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource fetchOneById(Integer value) {
        return fetchOne(HrResource.HR_RESOURCE.ID, value);
    }

    /**
     * Fetch records that have <code>res_url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByResUrl(String... values) {
        return fetch(HrResource.HR_RESOURCE.RES_URL, values);
    }

    /**
     * Fetch records that have <code>res_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByResType(Integer... values) {
        return fetch(HrResource.HR_RESOURCE.RES_TYPE, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByRemark(String... values) {
        return fetch(HrResource.HR_RESOURCE.REMARK, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByCompanyId(Integer... values) {
        return fetch(HrResource.HR_RESOURCE.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByTitle(String... values) {
        return fetch(HrResource.HR_RESOURCE.TITLE, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByDisable(Integer... values) {
        return fetch(HrResource.HR_RESOURCE.DISABLE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByCreateTime(Timestamp... values) {
        return fetch(HrResource.HR_RESOURCE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrResource.HR_RESOURCE.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>cover IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrResource> fetchByCover(String... values) {
        return fetch(HrResource.HR_RESOURCE.COVER, values);
    }
}
