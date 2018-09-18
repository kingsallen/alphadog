/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.records;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMove;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 简历搬家hr操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveRecord extends UpdatableRecordImpl<TalentpoolProfileMoveRecord> implements Record7<Integer, Integer, Byte, Date, Date, Timestamp, Timestamp> {

    private static final long serialVersionUID = -621063271;

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.hr_id</code>. hr.id
     */
    public void setHrId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.hr_id</code>. hr.id
     */
    public Integer getHrId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.channel</code>. 渠道  1 、51job  3、智联
     */
    public void setChannel(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.channel</code>. 渠道  1 、51job  3、智联
     */
    public Byte getChannel() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.start_date</code>. 简历搬家起始时间
     */
    public void setStartDate(Date value) {
        set(3, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.start_date</code>. 简历搬家起始时间
     */
    public Date getStartDate() {
        return (Date) get(3);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.end_date</code>. 简历搬家结束时间
     */
    public void setEndDate(Date value) {
        set(4, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.end_date</code>. 简历搬家结束时间
     */
    public Date getEndDate() {
        return (Date) get(4);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Byte, Date, Date, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Byte, Date, Date, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CHANNEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field4() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.START_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field5() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.END_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.UPDATE_TIME;
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
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getChannel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value4() {
        return getStartDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value5() {
        return getEndDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value2(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value3(Byte value) {
        setChannel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value4(Date value) {
        setStartDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value5(Date value) {
        setEndDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecord values(Integer value1, Integer value2, Byte value3, Date value4, Date value5, Timestamp value6, Timestamp value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TalentpoolProfileMoveRecord
     */
    public TalentpoolProfileMoveRecord() {
        super(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE);
    }

    /**
     * Create a detached, initialised TalentpoolProfileMoveRecord
     */
    public TalentpoolProfileMoveRecord(Integer id, Integer hrId, Byte channel, Date startDate, Date endDate, Timestamp createTime, Timestamp updateTime) {
        super(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE);

        set(0, id);
        set(1, hrId);
        set(2, channel);
        set(3, startDate);
        set(4, endDate);
        set(5, createTime);
        set(6, updateTime);
    }
}
