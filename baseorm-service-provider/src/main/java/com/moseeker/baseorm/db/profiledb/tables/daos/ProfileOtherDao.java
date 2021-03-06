/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables.daos;


import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 用户profile导入记录信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileOtherDao extends DAOImpl<ProfileOtherRecord, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther, Integer> {

    /**
     * Create a new ProfileOtherDao without any configuration
     */
    public ProfileOtherDao() {
        super(ProfileOther.PROFILE_OTHER, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther.class);
    }

    /**
     * Create a new ProfileOtherDao with an attached configuration
     */
    public ProfileOtherDao(Configuration configuration) {
        super(ProfileOther.PROFILE_OTHER, com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther object) {
        return object.getProfileId();
    }

    /**
     * Fetch records that have <code>profile_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther> fetchByProfileId(Integer... values) {
        return fetch(ProfileOther.PROFILE_OTHER.PROFILE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>profile_id = value</code>
     */
    public com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther fetchOneByProfileId(Integer value) {
        return fetchOne(ProfileOther.PROFILE_OTHER.PROFILE_ID, value);
    }

    /**
     * Fetch records that have <code>other IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther> fetchByOther(String... values) {
        return fetch(ProfileOther.PROFILE_OTHER.OTHER, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther> fetchByCreateTime(Timestamp... values) {
        return fetch(ProfileOther.PROFILE_OTHER.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.profiledb.tables.pojos.ProfileOther> fetchByUpdateTime(Timestamp... values) {
        return fetch(ProfileOther.PROFILE_OTHER.UPDATE_TIME, values);
    }
}
