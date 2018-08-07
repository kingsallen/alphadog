/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables.daos;


import com.moseeker.baseorm.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * Profile的项目经验
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileProjectexpDao extends DAOImpl<ProfileProjectexpRecord, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp, Integer> {

    /**
     * Create a new ProfileProjectexpDao without any configuration
     */
    public ProfileProjectexpDao() {
        super(ProfileProjectexp.PROFILE_PROJECTEXP, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp.class);
    }

    /**
     * Create a new ProfileProjectexpDao with an attached configuration
     */
    public ProfileProjectexpDao(Configuration configuration) {
        super(ProfileProjectexp.PROFILE_PROJECTEXP, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchById(Integer... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp fetchOneById(Integer value) {
        return fetchOne(ProfileProjectexp.PROFILE_PROJECTEXP.ID, value);
    }

    /**
     * Fetch records that have <code>profile_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByProfileId(Integer... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID, values);
    }

    /**
     * Fetch records that have <code>start IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByStart(Date... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.START, values);
    }

    /**
     * Fetch records that have <code>end IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByEnd(Date... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.END, values);
    }

    /**
     * Fetch records that have <code>end_until_now IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByEndUntilNow(Byte... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.END_UNTIL_NOW, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByName(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.NAME, values);
    }

    /**
     * Fetch records that have <code>company_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByCompanyName(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.COMPANY_NAME, values);
    }

    /**
     * Fetch records that have <code>is_it IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByIsIt(Byte... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.IS_IT, values);
    }

    /**
     * Fetch records that have <code>dev_tool IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByDevTool(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.DEV_TOOL, values);
    }

    /**
     * Fetch records that have <code>hardware IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByHardware(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.HARDWARE, values);
    }

    /**
     * Fetch records that have <code>software IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchBySoftware(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.SOFTWARE, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByUrl(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.URL, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByDescription(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>role IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByRole(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.ROLE, values);
    }

    /**
     * Fetch records that have <code>responsibility IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByResponsibility(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.RESPONSIBILITY, values);
    }

    /**
     * Fetch records that have <code>achievement IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByAchievement(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.ACHIEVEMENT, values);
    }

    /**
     * Fetch records that have <code>member IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByMember(String... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.MEMBER, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByCreateTime(Timestamp... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileProjectexp> fetchByUpdateTime(Timestamp... values) {
        return fetch(ProfileProjectexp.PROFILE_PROJECTEXP.UPDATE_TIME, values);
    }
}
