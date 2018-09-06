/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables.daos;


import com.moseeker.baseorm.db.profiledb.tables.ProfileWorks;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorksRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * Profile的个人作品
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileWorksDao extends DAOImpl<ProfileWorksRecord, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks, Integer> {

    /**
     * Create a new ProfileWorksDao without any configuration
     */
    public ProfileWorksDao() {
        super(ProfileWorks.PROFILE_WORKS, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks.class);
    }

    /**
     * Create a new ProfileWorksDao with an attached configuration
     */
    public ProfileWorksDao(Configuration configuration) {
        super(ProfileWorks.PROFILE_WORKS, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchById(Integer... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks fetchOneById(Integer value) {
        return fetchOne(ProfileWorks.PROFILE_WORKS.ID, value);
    }

    /**
     * Fetch records that have <code>profile_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByProfileId(Integer... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.PROFILE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>profile_id = value</code>
     */
    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks fetchOneByProfileId(Integer value) {
        return fetchOne(ProfileWorks.PROFILE_WORKS.PROFILE_ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByName(String... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.NAME, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByUrl(String... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.URL, values);
    }

    /**
     * Fetch records that have <code>cover IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByCover(String... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.COVER, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByDescription(String... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByCreateTime(Timestamp... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileWorks> fetchByUpdateTime(Timestamp... values) {
        return fetch(ProfileWorks.PROFILE_WORKS.UPDATE_TIME, values);
    }
}
