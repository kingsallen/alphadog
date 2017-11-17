/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 员工积分记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeePointsRecordRecord extends UpdatableRecordImpl<UserEmployeePointsRecordRecord> implements Record13<Integer, Long, String, Integer, Timestamp, Long, Long, Timestamp, Long, Long, Long, Integer, Integer> {

    private static final long serialVersionUID = -973406131;

    /**
     * Setter for <code>userdb.user_employee_points_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.employee_id</code>. user_employee.id
     */
    public void setEmployeeId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.employee_id</code>. user_employee.id
     */
    public Long getEmployeeId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.reason</code>. 积分变更说明
     */
    public void setReason(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.reason</code>. 积分变更说明
     */
    public String getReason() {
        return (String) get(2);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.award</code>. could use positive number to add rewards to user or nagetive number to minus
     */
    public void setAward(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.award</code>. could use positive number to add rewards to user or nagetive number to minus
     */
    public Integer getAward() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record._create_time</code>. time stamp when record created
     */
    public void set_CreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record._create_time</code>. time stamp when record created
     */
    public Timestamp get_CreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.application_id</code>. job_application.id
     */
    public void setApplicationId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.application_id</code>. job_application.id
     */
    public Long getApplicationId() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.recom_wxuser</code>. user_wx_user.id，推荐人的微信 id
     */
    public void setRecomWxuser(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.recom_wxuser</code>. user_wx_user.id，推荐人的微信 id
     */
    public Long getRecomWxuser() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.position_id</code>. job_position.id
     */
    public void setPositionId(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.position_id</code>. job_position.id
     */
    public Long getPositionId() {
        return (Long) get(8);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.berecom_wxuser_id</code>. 已废弃
     */
    public void setBerecomWxuserId(Long value) {
        set(9, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.berecom_wxuser_id</code>. 已废弃
     */
    public Long getBerecomWxuserId() {
        return (Long) get(9);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.award_config_id</code>. 积分记录来源hr_points_conf.id
     */
    public void setAwardConfigId(Long value) {
        set(10, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.award_config_id</code>. 积分记录来源hr_points_conf.id
     */
    public Long getAwardConfigId() {
        return (Long) get(10);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.recom_user_id</code>. userdb.user_user.id 推荐者的C端账号
     */
    public void setRecomUserId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.recom_user_id</code>. userdb.user_user.id 推荐者的C端账号
     */
    public Integer getRecomUserId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>userdb.user_employee_points_record.berecom_user_id</code>. userdb.user_user.id 被推荐者的C端账号
     */
    public void setBerecomUserId(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>userdb.user_employee_points_record.berecom_user_id</code>. userdb.user_user.id 被推荐者的C端账号
     */
    public Integer getBerecomUserId() {
        return (Integer) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Long, String, Integer, Timestamp, Long, Long, Timestamp, Long, Long, Long, Integer, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Long, String, Integer, Timestamp, Long, Long, Timestamp, Long, Long, Long, Integer, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.REASON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD._CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.APPLICATION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.RECOM_WXUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field9() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field10() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.BERECOM_WXUSER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field11() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD_CONFIG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.RECOM_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.BERECOM_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getAward();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return get_CreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getApplicationId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getRecomWxuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value9() {
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value10() {
        return getBerecomWxuserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value11() {
        return getAwardConfigId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getRecomUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getBerecomUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value2(Long value) {
        setEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value3(String value) {
        setReason(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value4(Integer value) {
        setAward(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value5(Timestamp value) {
        set_CreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value6(Long value) {
        setApplicationId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value7(Long value) {
        setRecomWxuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value9(Long value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value10(Long value) {
        setBerecomWxuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value11(Long value) {
        setAwardConfigId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value12(Integer value) {
        setRecomUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord value13(Integer value) {
        setBerecomUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeePointsRecordRecord values(Integer value1, Long value2, String value3, Integer value4, Timestamp value5, Long value6, Long value7, Timestamp value8, Long value9, Long value10, Long value11, Integer value12, Integer value13) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserEmployeePointsRecordRecord
     */
    public UserEmployeePointsRecordRecord() {
        super(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD);
    }

    /**
     * Create a detached, initialised UserEmployeePointsRecordRecord
     */
    public UserEmployeePointsRecordRecord(Integer id, Long employeeId, String reason, Integer award, Timestamp _CreateTime, Long applicationId, Long recomWxuser, Timestamp updateTime, Long positionId, Long berecomWxuserId, Long awardConfigId, Integer recomUserId, Integer berecomUserId) {
        super(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD);

        set(0, id);
        set(1, employeeId);
        set(2, reason);
        set(3, award);
        set(4, _CreateTime);
        set(5, applicationId);
        set(6, recomWxuser);
        set(7, updateTime);
        set(8, positionId);
        set(9, berecomWxuserId);
        set(10, awardConfigId);
        set(11, recomUserId);
        set(12, berecomUserId);
    }
}
