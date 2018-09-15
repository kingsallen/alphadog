/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.records;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveDetail;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 凡是记录在该表的手机号对应的简历都是已成功入库的简历搬家简历，
 * 但是如果简历搬家失败时根据status字段标记出哪些简历是搬家失败的，下次搬家时不会因为重新合并一次导致数据不准确
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveDetailRecord extends UpdatableRecordImpl<TalentpoolProfileMoveDetailRecord> implements Record6<Integer, Long, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1106609756;

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_detail.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_detail.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_detail.mobile</code>. 手机号
     */
    public void setMobile(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_detail.mobile</code>. 手机号
     */
    public Long getMobile() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_detail.profile_move_id</code>. talentpool_profile_move.id，对应的是上一次搬家失败的操作id
     */
    public void setProfileMoveId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_detail.profile_move_id</code>. talentpool_profile_move.id，对应的是上一次搬家失败的操作id
     */
    public Integer getProfileMoveId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_detail.profile_move_status</code>. 该手机号对应的简历搬家成功状态 0 失败 1 成功
     */
    public void setProfileMoveStatus(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_detail.profile_move_status</code>. 该手机号对应的简历搬家成功状态 0 失败 1 成功
     */
    public Byte getProfileMoveStatus() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_detail.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_detail.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_detail.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_detail.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Long, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Long, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL.MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL.UPDATE_TIME;
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
        return getMobile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getProfileMoveId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getProfileMoveStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord value2(Long value) {
        setMobile(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord value3(Integer value) {
        setProfileMoveId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord value4(Byte value) {
        setProfileMoveStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveDetailRecord values(Integer value1, Long value2, Integer value3, Byte value4, Timestamp value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TalentpoolProfileMoveDetailRecord
     */
    public TalentpoolProfileMoveDetailRecord() {
        super(TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL);
    }

    /**
     * Create a detached, initialised TalentpoolProfileMoveDetailRecord
     */
    public TalentpoolProfileMoveDetailRecord(Integer id, Long mobile, Integer profileMoveId, Byte profileMoveStatus, Timestamp createTime, Timestamp updateTime) {
        super(TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL);

        set(0, id);
        set(1, mobile);
        set(2, profileMoveId);
        set(3, profileMoveStatus);
        set(4, createTime);
        set(5, updateTime);
    }
}
