/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeNetworkResources;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 员工的雷达人脉top
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralEmployeeNetworkResourcesRecord extends UpdatableRecordImpl<ReferralEmployeeNetworkResourcesRecord> implements Record8<Integer, Integer, Integer, Timestamp, Timestamp, Byte, Integer, Integer> {

    private static final long serialVersionUID = -1404178743;

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.post_user_id</code>. 员工的C端账号
     */
    public void setPostUserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.post_user_id</code>. 员工的C端账号
     */
    public Integer getPostUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.presentee_user_id</code>. user_user.id 人脉候选人的C端账号
     */
    public void setPresenteeUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.presentee_user_id</code>. user_user.id 人脉候选人的C端账号
     */
    public Integer getPresenteeUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.disable</code>. 是否有效 0有效 1 无效
     */
    public void setDisable(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.disable</code>. 是否有效 0有效 1 无效
     */
    public Byte getDisable() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.company_id</code>.
     */
    public void setCompanyId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>referraldb.referral_employee_network_resources.position_id</code>.
     */
    public void setPositionId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>referraldb.referral_employee_network_resources.position_id</code>.
     */
    public Integer getPositionId() {
        return (Integer) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Timestamp, Timestamp, Byte, Integer, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Timestamp, Timestamp, Byte, Integer, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POST_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.PRESENTEE_USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES.POSITION_ID;
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
    public Integer value2() {
        return getPostUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPresenteeUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value2(Integer value) {
        setPostUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value3(Integer value) {
        setPresenteeUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value4(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value5(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value6(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value7(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord value8(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralEmployeeNetworkResourcesRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Timestamp value5, Byte value6, Integer value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ReferralEmployeeNetworkResourcesRecord
     */
    public ReferralEmployeeNetworkResourcesRecord() {
        super(ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES);
    }

    /**
     * Create a detached, initialised ReferralEmployeeNetworkResourcesRecord
     */
    public ReferralEmployeeNetworkResourcesRecord(Integer id, Integer postUserId, Integer presenteeUserId, Timestamp createTime, Timestamp updateTime, Byte disable, Integer companyId, Integer positionId) {
        super(ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES);

        set(0, id);
        set(1, postUserId);
        set(2, presenteeUserId);
        set(3, createTime);
        set(4, updateTime);
        set(5, disable);
        set(6, companyId);
        set(7, positionId);
    }
}
