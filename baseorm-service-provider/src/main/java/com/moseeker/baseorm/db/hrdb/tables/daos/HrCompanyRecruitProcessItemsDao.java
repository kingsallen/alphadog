/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyRecruitProcessItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecruitProcessItemsRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 公司招聘流程配置表具体状态字表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyRecruitProcessItemsDao extends DAOImpl<HrCompanyRecruitProcessItemsRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems, Integer> {

    /**
     * Create a new HrCompanyRecruitProcessItemsDao without any configuration
     */
    public HrCompanyRecruitProcessItemsDao() {
        super(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems.class);
    }

    /**
     * Create a new HrCompanyRecruitProcessItemsDao with an attached configuration
     */
    public HrCompanyRecruitProcessItemsDao(Configuration configuration) {
        super(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchById(Integer... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems fetchOneById(Integer value) {
        return fetchOne(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.ID, value);
    }

    /**
     * Fetch records that have <code>parent_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchByParentId(Integer... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.PARENT_ID, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchByDisable(Integer... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.DISABLE, values);
    }

    /**
     * Fetch records that have <code>process_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchByProcessOrder(Integer... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.PROCESS_ORDER, values);
    }

    /**
     * Fetch records that have <code>app_tpl_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchByAppTplId(Integer... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.APP_TPL_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchByCreateTime(Timestamp... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcessItems> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrCompanyRecruitProcessItems.HR_COMPANY_RECRUIT_PROCESS_ITEMS.UPDATE_TIME, values);
    }
}
