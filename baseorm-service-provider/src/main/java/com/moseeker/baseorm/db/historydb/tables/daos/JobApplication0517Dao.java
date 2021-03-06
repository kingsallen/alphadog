/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.JobApplication0517;
import com.moseeker.baseorm.db.historydb.tables.records.JobApplication0517Record;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobApplication0517Dao extends DAOImpl<JobApplication0517Record, com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517, Integer> {

    /**
     * Create a new JobApplication0517Dao without any configuration
     */
    public JobApplication0517Dao() {
        super(JobApplication0517.JOB_APPLICATION0517, com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517.class);
    }

    /**
     * Create a new JobApplication0517Dao with an attached configuration
     */
    public JobApplication0517Dao(Configuration configuration) {
        super(JobApplication0517.JOB_APPLICATION0517, com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517 object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchById(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517 fetchOneById(Integer value) {
        return fetchOne(JobApplication0517.JOB_APPLICATION0517.ID, value);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByWechatId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByPositionId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>recommender_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByRecommenderId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.RECOMMENDER_ID, values);
    }

    /**
     * Fetch records that have <code>submit_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchBySubmitTime(Timestamp... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.SUBMIT_TIME, values);
    }

    /**
     * Fetch records that have <code>status_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByStatusId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.STATUS_ID, values);
    }

    /**
     * Fetch records that have <code>l_application_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByLApplicationId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.L_APPLICATION_ID, values);
    }

    /**
     * Fetch records that have <code>reward IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByReward(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.REWARD, values);
    }

    /**
     * Fetch records that have <code>source_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchBySourceId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.SOURCE_ID, values);
    }

    /**
     * Fetch records that have <code>_create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchBy_CreateTime(Timestamp... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517._CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>applier_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByApplierId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.APPLIER_ID, values);
    }

    /**
     * Fetch records that have <code>interview_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByInterviewId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.INTERVIEW_ID, values);
    }

    /**
     * Fetch records that have <code>resume_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByResumeId(String... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.RESUME_ID, values);
    }

    /**
     * Fetch records that have <code>ats_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByAtsStatus(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.ATS_STATUS, values);
    }

    /**
     * Fetch records that have <code>applier_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByApplierName(String... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.APPLIER_NAME, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByDisable(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.DISABLE, values);
    }

    /**
     * Fetch records that have <code>routine IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByRoutine(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.ROUTINE, values);
    }

    /**
     * Fetch records that have <code>is_viewed IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByIsViewed(Byte... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.IS_VIEWED, values);
    }

    /**
     * Fetch records that have <code>not_suitable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByNotSuitable(Byte... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.NOT_SUITABLE, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByCompanyId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByUpdateTime(Timestamp... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>app_tpl_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByAppTplId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.APP_TPL_ID, values);
    }

    /**
     * Fetch records that have <code>proxy IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByProxy(Byte... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.PROXY, values);
    }

    /**
     * Fetch records that have <code>apply_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByApplyType(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.APPLY_TYPE, values);
    }

    /**
     * Fetch records that have <code>email_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByEmailStatus(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.EMAIL_STATUS, values);
    }

    /**
     * Fetch records that have <code>view_count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByViewCount(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.VIEW_COUNT, values);
    }

    /**
     * Fetch records that have <code>recommender_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.JobApplication0517> fetchByRecommenderUserId(Integer... values) {
        return fetch(JobApplication0517.JOB_APPLICATION0517.RECOMMENDER_USER_ID, values);
    }
}
