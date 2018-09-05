/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.HistoryReferralPositionRel;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class HistoryReferralPositionRelRecord extends UpdatableRecordImpl<HistoryReferralPositionRelRecord> implements Record6<Integer, Integer, Integer, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -128091223;

    /**
     * Setter for <code>referraldb.history_referral_position_rel.id</code>. 序列ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.history_referral_position_rel.id</code>. 序列ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.history_referral_position_rel.position_id</code>. 职位ID
     */
    public void setPositionId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.history_referral_position_rel.position_id</code>. 职位ID
     */
    public Integer getPositionId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>referraldb.history_referral_position_rel.company_id</code>. 公司ID
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.history_referral_position_rel.company_id</code>. 公司ID
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>referraldb.history_referral_position_rel.record_type</code>. 记录类型 ADD DELETE  UPDATE
     */
    public void setRecordType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.history_referral_position_rel.record_type</code>. 记录类型 ADD DELETE  UPDATE
     */
    public String getRecordType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>referraldb.history_referral_position_rel.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.history_referral_position_rel.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>referraldb.history_referral_position_rel.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>referraldb.history_referral_position_rel.update_time</code>. 更新时间
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
    public Row6<Integer, Integer, Integer, String, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, String, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.POSITION_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.RECORD_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL.UPDATE_TIME;
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
        return getPositionId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getRecordType();
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
    public HistoryReferralPositionRelRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryReferralPositionRelRecord value2(Integer value) {
        setPositionId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryReferralPositionRelRecord value3(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryReferralPositionRelRecord value4(String value) {
        setRecordType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryReferralPositionRelRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryReferralPositionRelRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryReferralPositionRelRecord values(Integer value1, Integer value2, Integer value3, String value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached HistoryReferralPositionRelRecord
     */
    public HistoryReferralPositionRelRecord() {
        super(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL);
    }

    /**
     * Create a detached, initialised HistoryReferralPositionRelRecord
     */
    public HistoryReferralPositionRelRecord(Integer id, Integer positionId, Integer companyId, String recordType, Timestamp createTime, Timestamp updateTime) {
        super(HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL);

        set(0, id);
        set(1, positionId);
        set(2, companyId);
        set(3, recordType);
        set(4, createTime);
        set(5, updateTime);
    }
}