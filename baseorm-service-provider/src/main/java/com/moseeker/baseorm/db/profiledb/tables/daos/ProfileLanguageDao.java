/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables.daos;


import com.moseeker.baseorm.db.profiledb.tables.ProfileLanguage;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * Profile的语言
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileLanguageDao extends DAOImpl<ProfileLanguageRecord, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage, Integer> {

    /**
     * Create a new ProfileLanguageDao without any configuration
     */
    public ProfileLanguageDao() {
        super(ProfileLanguage.PROFILE_LANGUAGE, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage.class);
    }

    /**
     * Create a new ProfileLanguageDao with an attached configuration
     */
    public ProfileLanguageDao(Configuration configuration) {
        super(ProfileLanguage.PROFILE_LANGUAGE, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage> fetchById(Integer... values) {
        return fetch(ProfileLanguage.PROFILE_LANGUAGE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage fetchOneById(Integer value) {
        return fetchOne(ProfileLanguage.PROFILE_LANGUAGE.ID, value);
    }

    /**
     * Fetch records that have <code>profile_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage> fetchByProfileId(Integer... values) {
        return fetch(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage> fetchByName(String... values) {
        return fetch(ProfileLanguage.PROFILE_LANGUAGE.NAME, values);
    }

    /**
     * Fetch records that have <code>level IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage> fetchByLevel(Byte... values) {
        return fetch(ProfileLanguage.PROFILE_LANGUAGE.LEVEL, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage> fetchByCreateTime(Timestamp... values) {
        return fetch(ProfileLanguage.PROFILE_LANGUAGE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileLanguage> fetchByUpdateTime(Timestamp... values) {
        return fetch(ProfileLanguage.PROFILE_LANGUAGE.UPDATE_TIME, values);
    }
}
