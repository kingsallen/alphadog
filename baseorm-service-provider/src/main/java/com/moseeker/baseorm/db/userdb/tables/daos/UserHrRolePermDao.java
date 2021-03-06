/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserHrRolePerm;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrRolePermRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 角色的操作权限
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserHrRolePermDao extends DAOImpl<UserHrRolePermRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm, Integer> {

    /**
     * Create a new UserHrRolePermDao without any configuration
     */
    public UserHrRolePermDao() {
        super(UserHrRolePerm.USER_HR_ROLE_PERM, com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm.class);
    }

    /**
     * Create a new UserHrRolePermDao with an attached configuration
     */
    public UserHrRolePermDao(Configuration configuration) {
        super(UserHrRolePerm.USER_HR_ROLE_PERM, com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchById(Integer... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm fetchOneById(Integer value) {
        return fetchOne(UserHrRolePerm.USER_HR_ROLE_PERM.ID, value);
    }

    /**
     * Fetch records that have <code>role_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchByRoleId(Integer... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.ROLE_ID, values);
    }

    /**
     * Fetch records that have <code>perm_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchByPermId(Integer... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.PERM_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchByCreateTime(Timestamp... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>creator IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchByCreator(String... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.CREATOR, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>updator IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserHrRolePerm> fetchByUpdator(String... values) {
        return fetch(UserHrRolePerm.USER_HR_ROLE_PERM.UPDATOR, values);
    }
}
