/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrAtsRecommend;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAtsRecommendRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 推荐给用人部门表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsRecommendDao extends DAOImpl<HrAtsRecommendRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend, Integer> {

    /**
     * Create a new HrAtsRecommendDao without any configuration
     */
    public HrAtsRecommendDao() {
        super(HrAtsRecommend.HR_ATS_RECOMMEND, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend.class);
    }

    /**
     * Create a new HrAtsRecommendDao with an attached configuration
     */
    public HrAtsRecommendDao(Configuration configuration) {
        super(HrAtsRecommend.HR_ATS_RECOMMEND, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchById(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend fetchOneById(Integer value) {
        return fetchOne(HrAtsRecommend.HR_ATS_RECOMMEND.ID, value);
    }

    /**
     * Fetch records that have <code>application_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByApplicationId(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.APPLICATION_ID, values);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByHrId(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.HR_ID, values);
    }

    /**
     * Fetch records that have <code>interviewer_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByInterviewerId(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.INTERVIEWER_ID, values);
    }

    /**
     * Fetch records that have <code>recommend_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByRecommendStatus(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.RECOMMEND_STATUS, values);
    }

    /**
     * Fetch records that have <code>post_script IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByPostScript(String... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.POST_SCRIPT, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByDisable(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.DISABLE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByCreateTime(Timestamp... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>candidate_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByCandidateStatus(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.CANDIDATE_STATUS, values);
    }

    /**
     * Fetch records that have <code>read_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsRecommend> fetchByReadStatus(Integer... values) {
        return fetch(HrAtsRecommend.HR_ATS_RECOMMEND.READ_STATUS, values);
    }
}
