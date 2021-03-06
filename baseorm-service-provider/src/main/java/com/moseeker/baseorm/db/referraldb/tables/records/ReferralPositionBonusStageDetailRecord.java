/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonusStageDetail;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReferralPositionBonusStageDetailRecord extends UpdatableRecordImpl<ReferralPositionBonusStageDetailRecord> implements Record8<Integer, Integer, Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1931741625;

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.id</code>. 序列ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.id</code>. 序列ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.referral_position_bonus_id</code>. 内推职位总奖金表ID
     */
    public void setReferralPositionBonusId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.referral_position_bonus_id</code>. 内推职位总奖金表ID
     */
    public Integer getReferralPositionBonusId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.position_id</code>. 职位ID
     */
    public void setPositionId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.position_id</code>. 职位ID
     */
    public Integer getPositionId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.stage_type</code>. 奖励节点
     */
    public void setStageType(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.stage_type</code>. 奖励节点
     */
    public Integer getStageType() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.stage_bonus</code>. 奖励金额
     */
    public void setStageBonus(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.stage_bonus</code>. 奖励金额
     */
    public Integer getStageBonus() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.stage_proportion</code>. 该节点金额占百分比
     */
    public void setStageProportion(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.stage_proportion</code>. 该节点金额占百分比
     */
    public Integer getStageProportion() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>referraldb.referral_position_bonus_stage_detail.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>referraldb.referral_position_bonus_stage_detail.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    public Row8<Integer, Integer, Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.REFERRAL_POSITION_BONUS_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_BONUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_PROPORTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.UPDATE_TIME;
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
        return getReferralPositionBonusId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getStageType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getStageBonus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getStageProportion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
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
    public ReferralPositionBonusStageDetailRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value2(Integer value) {
        setReferralPositionBonusId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value3(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value4(Integer value) {
        setStageType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value5(Integer value) {
        setStageBonus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value6(Integer value) {
        setStageProportion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferralPositionBonusStageDetailRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6, Timestamp value7, Timestamp value8) {
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
     * Create a detached ReferralPositionBonusStageDetailRecord
     */
    public ReferralPositionBonusStageDetailRecord() {
        super(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL);
    }

    /**
     * Create a detached, initialised ReferralPositionBonusStageDetailRecord
     */
    public ReferralPositionBonusStageDetailRecord(Integer id, Integer referralPositionBonusId, Integer positionId, Integer stageType, Integer stageBonus, Integer stageProportion, Timestamp createTime, Timestamp updateTime) {
        super(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL);

        set(0, id);
        set(1, referralPositionBonusId);
        set(2, positionId);
        set(3, stageType);
        set(4, stageBonus);
        set(5, stageProportion);
        set(6, createTime);
        set(7, updateTime);
    }
}
