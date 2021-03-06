/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.daos;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrAutomaticTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * hr自动标签
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolHrAutomaticTagDao extends DAOImpl<TalentpoolHrAutomaticTagRecord, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag, Integer> {

    /**
     * Create a new TalentpoolHrAutomaticTagDao without any configuration
     */
    public TalentpoolHrAutomaticTagDao() {
        super(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag.class);
    }

    /**
     * Create a new TalentpoolHrAutomaticTagDao with an attached configuration
     */
    public TalentpoolHrAutomaticTagDao(Configuration configuration) {
        super(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchById(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag fetchOneById(Integer value) {
        return fetchOne(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.ID, value);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByHrId(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.HR_ID, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByName(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.NAME, values);
    }

    /**
     * Fetch records that have <code>color IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByColor(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.COLOR, values);
    }

    /**
     * Fetch records that have <code>origins IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByOrigins(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.ORIGINS, values);
    }

    /**
     * Fetch records that have <code>work_years IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByWorkYears(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.WORK_YEARS, values);
    }

    /**
     * Fetch records that have <code>city_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByCityName(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.CITY_NAME, values);
    }

    /**
     * Fetch records that have <code>city_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByCityCode(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.CITY_CODE, values);
    }

    /**
     * Fetch records that have <code>degree IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByDegree(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.DEGREE, values);
    }

    /**
     * Fetch records that have <code>past_position IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByPastPosition(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.PAST_POSITION, values);
    }

    /**
     * Fetch records that have <code>in_last_job_search_position IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByInLastJobSearchPosition(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.IN_LAST_JOB_SEARCH_POSITION, values);
    }

    /**
     * Fetch records that have <code>min_age IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByMinAge(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.MIN_AGE, values);
    }

    /**
     * Fetch records that have <code>max_age IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByMaxAge(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.MAX_AGE, values);
    }

    /**
     * Fetch records that have <code>intention_city_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByIntentionCityName(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.INTENTION_CITY_NAME, values);
    }

    /**
     * Fetch records that have <code>intention_city_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByIntentionCityCode(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.INTENTION_CITY_CODE, values);
    }

    /**
     * Fetch records that have <code>intention_salary_code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByIntentionSalaryCode(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.INTENTION_SALARY_CODE, values);
    }

    /**
     * Fetch records that have <code>sex IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchBySex(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.SEX, values);
    }

    /**
     * Fetch records that have <code>is_recommend IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByIsRecommend(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.IS_RECOMMEND, values);
    }

    /**
     * Fetch records that have <code>company_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByCompanyName(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.COMPANY_NAME, values);
    }

    /**
     * Fetch records that have <code>in_last_job_search_company IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByInLastJobSearchCompany(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.IN_LAST_JOB_SEARCH_COMPANY, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByCreateTime(Timestamp... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByUpdateTime(Timestamp... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByDisable(Integer... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.DISABLE, values);
    }

    /**
     * Fetch records that have <code>keywords IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByKeywords(String... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.KEYWORDS, values);
    }

    /**
     * Fetch records that have <code>contain_any_key IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag> fetchByContainAnyKey(Byte... values) {
        return fetch(TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG.CONTAIN_ANY_KEY, values);
    }
}
