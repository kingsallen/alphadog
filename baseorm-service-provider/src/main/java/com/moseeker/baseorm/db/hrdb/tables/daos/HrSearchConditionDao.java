/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 候选人列表常用筛选项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrSearchConditionDao extends DAOImpl<HrSearchConditionRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition, Integer> {

    /**
     * Create a new HrSearchConditionDao without any configuration
     */
    public HrSearchConditionDao() {
        super(HrSearchCondition.HR_SEARCH_CONDITION, com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition.class);
    }

    /**
     * Create a new HrSearchConditionDao with an attached configuration
     */
    public HrSearchConditionDao(Configuration configuration) {
        super(HrSearchCondition.HR_SEARCH_CONDITION, com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchById(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition fetchOneById(Integer value) {
        return fetchOne(HrSearchCondition.HR_SEARCH_CONDITION.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByName(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.NAME, values);
    }

    /**
     * Fetch records that have <code>publisher IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPublisher(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.PUBLISHER, values);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPositionId(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>keyword IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByKeyword(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.KEYWORD, values);
    }

    /**
     * Fetch records that have <code>submit_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchBySubmitTime(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.SUBMIT_TIME, values);
    }

    /**
     * Fetch records that have <code>work_years IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByWorkYears(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.WORK_YEARS, values);
    }

    /**
     * Fetch records that have <code>city_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByCityName(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.CITY_NAME, values);
    }

    /**
     * Fetch records that have <code>degree IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByDegree(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.DEGREE, values);
    }

    /**
     * Fetch records that have <code>past_position IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPastPosition(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.PAST_POSITION, values);
    }

    /**
     * Fetch records that have <code>in_last_job_search_position IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByInLastJobSearchPosition(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.IN_LAST_JOB_SEARCH_POSITION, values);
    }

    /**
     * Fetch records that have <code>min_age IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByMinAge(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.MIN_AGE, values);
    }

    /**
     * Fetch records that have <code>max_age IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByMaxAge(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.MAX_AGE, values);
    }

    /**
     * Fetch records that have <code>intention_city_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByIntentionCityName(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.INTENTION_CITY_NAME, values);
    }

    /**
     * Fetch records that have <code>sex IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchBySex(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.SEX, values);
    }

    /**
     * Fetch records that have <code>intention_salary_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByIntentionSalaryCode(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.INTENTION_SALARY_CODE, values);
    }

    /**
     * Fetch records that have <code>company_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByCompanyName(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.COMPANY_NAME, values);
    }

    /**
     * Fetch records that have <code>in_last_job_search_company IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByInLastJobSearchCompany(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.IN_LAST_JOB_SEARCH_COMPANY, values);
    }

    /**
     * Fetch records that have <code>hr_account_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByHrAccountId(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.HR_ACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByCreateTime(Timestamp... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByUpdateTime(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByType(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.TYPE, values);
    }

    /**
     * Fetch records that have <code>candidate_source IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByCandidateSource(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.CANDIDATE_SOURCE, values);
    }

    /**
     * Fetch records that have <code>is_public IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByIsPublic(Byte... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.IS_PUBLIC, values);
    }

    /**
     * Fetch records that have <code>origins IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByOrigins(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.ORIGINS, values);
    }

    /**
     * Fetch records that have <code>is_recommend IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByIsRecommend(Byte... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.IS_RECOMMEND, values);
    }

    /**
     * Fetch records that have <code>tag_ids IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByTagIds(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.TAG_IDS, values);
    }

    /**
     * Fetch records that have <code>favorite_hrs IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByFavoriteHrs(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.FAVORITE_HRS, values);
    }

    /**
     * Fetch records that have <code>city_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByCityCode(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.CITY_CODE, values);
    }

    /**
     * Fetch records that have <code>intention_city_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByIntentionCityCode(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.INTENTION_CITY_CODE, values);
    }

    /**
     * Fetch records that have <code>position_key_word IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPositionKeyWord(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.POSITION_KEY_WORD, values);
    }

    /**
     * Fetch records that have <code>past_position_key_word IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPastPositionKeyWord(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.PAST_POSITION_KEY_WORD, values);
    }

    /**
     * Fetch records that have <code>past_company_key_word IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPastCompanyKeyWord(String... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.PAST_COMPANY_KEY_WORD, values);
    }

    /**
     * Fetch records that have <code>position_status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrSearchCondition> fetchByPositionStatus(Integer... values) {
        return fetch(HrSearchCondition.HR_SEARCH_CONDITION.POSITION_STATUS, values);
    }
}
