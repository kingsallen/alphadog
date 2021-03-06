/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrNpsRecommend;
import com.moseeker.baseorm.db.hrdb.tables.records.HrNpsRecommendRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * hr推荐同行表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrNpsRecommendDao extends DAOImpl<HrNpsRecommendRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend, Integer> {

    /**
     * Create a new HrNpsRecommendDao without any configuration
     */
    public HrNpsRecommendDao() {
        super(HrNpsRecommend.HR_NPS_RECOMMEND, com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend.class);
    }

    /**
     * Create a new HrNpsRecommendDao with an attached configuration
     */
    public HrNpsRecommendDao(Configuration configuration) {
        super(HrNpsRecommend.HR_NPS_RECOMMEND, com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend> fetchById(Integer... values) {
        return fetch(HrNpsRecommend.HR_NPS_RECOMMEND.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend fetchOneById(Integer value) {
        return fetchOne(HrNpsRecommend.HR_NPS_RECOMMEND.ID, value);
    }

    /**
     * Fetch records that have <code>hr_nps_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend> fetchByHrNpsId(Integer... values) {
        return fetch(HrNpsRecommend.HR_NPS_RECOMMEND.HR_NPS_ID, values);
    }

    /**
     * Fetch a unique record that has <code>hr_nps_id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend fetchOneByHrNpsId(Integer value) {
        return fetchOne(HrNpsRecommend.HR_NPS_RECOMMEND.HR_NPS_ID, value);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend> fetchByUsername(String... values) {
        return fetch(HrNpsRecommend.HR_NPS_RECOMMEND.USERNAME, values);
    }

    /**
     * Fetch records that have <code>mobile IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend> fetchByMobile(String... values) {
        return fetch(HrNpsRecommend.HR_NPS_RECOMMEND.MOBILE, values);
    }

    /**
     * Fetch records that have <code>company IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend> fetchByCompany(String... values) {
        return fetch(HrNpsRecommend.HR_NPS_RECOMMEND.COMPANY, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrNpsRecommend> fetchByCreateTime(Timestamp... values) {
        return fetch(HrNpsRecommend.HR_NPS_RECOMMEND.CREATE_TIME, values);
    }
}
