/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrNps;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * nps打分推荐表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrNpsRecord extends UpdatableRecordImpl<HrNpsRecord> implements Record6<Integer, Integer, Byte, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -165676501;

    /**
     * Setter for <code>hrdb.hr_nps.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_nps.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_nps.hr_account_id</code>. hr帐号
     */
    public void setHrAccountId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_nps.hr_account_id</code>. hr帐号
     */
    public Integer getHrAccountId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_nps.intention</code>. 推荐同行的意愿【0-10】
     */
    public void setIntention(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_nps.intention</code>. 推荐同行的意愿【0-10】
     */
    public Byte getIntention() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_nps.accept_contact</code>.  是否愿意接听电话 0-未确认，1-愿意，2-不愿意
     */
    public void setAcceptContact(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_nps.accept_contact</code>.  是否愿意接听电话 0-未确认，1-愿意，2-不愿意
     */
    public Byte getAcceptContact() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_nps.create_time</code>. 分配账号的时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_nps.create_time</code>. 分配账号的时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_nps.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_nps.update_time</code>. 更新时间
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
    public Row6<Integer, Integer, Byte, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Byte, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrNps.HR_NPS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrNps.HR_NPS.HR_ACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return HrNps.HR_NPS.INTENTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return HrNps.HR_NPS.ACCEPT_CONTACT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrNps.HR_NPS.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrNps.HR_NPS.UPDATE_TIME;
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
        return getHrAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getIntention();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getAcceptContact();
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
    public HrNpsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrNpsRecord value2(Integer value) {
        setHrAccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrNpsRecord value3(Byte value) {
        setIntention(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrNpsRecord value4(Byte value) {
        setAcceptContact(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrNpsRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrNpsRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrNpsRecord values(Integer value1, Integer value2, Byte value3, Byte value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached HrNpsRecord
     */
    public HrNpsRecord() {
        super(HrNps.HR_NPS);
    }

    /**
     * Create a detached, initialised HrNpsRecord
     */
    public HrNpsRecord(Integer id, Integer hrAccountId, Byte intention, Byte acceptContact, Timestamp createTime, Timestamp updateTime) {
        super(HrNps.HR_NPS);

        set(0, id);
        set(1, hrAccountId);
        set(2, intention);
        set(3, acceptContact);
        set(4, createTime);
        set(5, updateTime);
    }
}
