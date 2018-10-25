/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.ProfileOther180726;
import com.moseeker.baseorm.db.historydb.tables.records.ProfileOther180726Record;

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
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileOther180726Dao extends DAOImpl<ProfileOther180726Record, com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726, Integer> {

    /**
     * Create a new ProfileOther180726Dao without any configuration
     */
    public ProfileOther180726Dao() {
        super(ProfileOther180726.PROFILE_OTHER180726, com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726.class);
    }

    /**
     * Create a new ProfileOther180726Dao with an attached configuration
     */
    public ProfileOther180726Dao(Configuration configuration) {
        super(ProfileOther180726.PROFILE_OTHER180726, com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726 object) {
        return object.getProfileId();
    }

    /**
     * Fetch records that have <code>profile_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726> fetchByProfileId(Integer... values) {
        return fetch(ProfileOther180726.PROFILE_OTHER180726.PROFILE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>profile_id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726 fetchOneByProfileId(Integer value) {
        return fetchOne(ProfileOther180726.PROFILE_OTHER180726.PROFILE_ID, value);
    }

    /**
     * Fetch records that have <code>other IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726> fetchByOther(String... values) {
        return fetch(ProfileOther180726.PROFILE_OTHER180726.OTHER, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726> fetchByCreateTime(Timestamp... values) {
        return fetch(ProfileOther180726.PROFILE_OTHER180726.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.ProfileOther180726> fetchByUpdateTime(Timestamp... values) {
        return fetch(ProfileOther180726.PROFILE_OTHER180726.UPDATE_TIME, values);
    }
}
