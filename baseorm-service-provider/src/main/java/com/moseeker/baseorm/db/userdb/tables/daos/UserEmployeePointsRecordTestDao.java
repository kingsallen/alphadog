/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecordTest;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordTestRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 员工积分记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeePointsRecordTestDao extends DAOImpl<UserEmployeePointsRecordTestRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest, Integer> {

    /**
     * Create a new UserEmployeePointsRecordTestDao without any configuration
     */
    public UserEmployeePointsRecordTestDao() {
        super(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest.class);
    }

    /**
     * Create a new UserEmployeePointsRecordTestDao with an attached configuration
     */
    public UserEmployeePointsRecordTestDao(Configuration configuration) {
        super(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST, com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchById(Integer... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest fetchOneById(Integer value) {
        return fetchOne(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.ID, value);
    }

    /**
     * Fetch records that have <code>employee_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByEmployeeId(Long... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.EMPLOYEE_ID, values);
    }

    /**
     * Fetch records that have <code>reason IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByReason(String... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.REASON, values);
    }

    /**
     * Fetch records that have <code>award IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByAward(Integer... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.AWARD, values);
    }

    /**
     * Fetch records that have <code>_create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchBy_CreateTime(Timestamp... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST._CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>application_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByApplicationId(Long... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.APPLICATION_ID, values);
    }

    /**
     * Fetch records that have <code>recom_wxuser IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByRecomWxuser(Long... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.RECOM_WXUSER, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByPositionId(Long... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>berecom_wxuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByBerecomWxuserId(Long... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.BERECOM_WXUSER_ID, values);
    }

    /**
     * Fetch records that have <code>award_config_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByAwardConfigId(Long... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.AWARD_CONFIG_ID, values);
    }

    /**
     * Fetch records that have <code>recom_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByRecomUserId(Integer... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.RECOM_USER_ID, values);
    }

    /**
     * Fetch records that have <code>berecom_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecordTest> fetchByBerecomUserId(Integer... values) {
        return fetch(UserEmployeePointsRecordTest.USER_EMPLOYEE_POINTS_RECORD_TEST.BERECOM_USER_ID, values);
    }
}
