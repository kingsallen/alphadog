/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.records;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 简历搬家操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveRecordRecord extends UpdatableRecordImpl<TalentpoolProfileMoveRecordRecord> implements Record10<Integer, Integer, Integer, Byte, Integer, Byte, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -345517328;

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.profile_move_id</code>. hr.id
     */
    public void setProfileMoveId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.profile_move_id</code>. hr.id
     */
    public Integer getProfileMoveId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.thirdparty_company_id</code>. thirdparty_account_company.id
     */
    public void setThirdpartyCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.thirdparty_company_id</code>. thirdparty_account_company.id
     */
    public Integer getThirdpartyCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.crawl_type</code>. 简历类型 1.主动投递简历  2.已下载简历
     */
    public void setCrawlType(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.crawl_type</code>. 简历类型 1.主动投递简历  2.已下载简历
     */
    public Byte getCrawlType() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.crawl_num</code>. 本次简历搬家获取简历数
     */
    public void setCrawlNum(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.crawl_num</code>. 本次简历搬家获取简历数
     */
    public Integer getCrawlNum() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.status</code>. 0.获取中 1.已完成 2.获取失败
     */
    public void setStatus(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.status</code>. 0.获取中 1.已完成 2.获取失败
     */
    public Byte getStatus() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.current_email_num</code>. 当前执行的简历搬家的第n封邮件
     */
    public void setCurrentEmailNum(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.current_email_num</code>. 当前执行的简历搬家的第n封邮件
     */
    public Integer getCurrentEmailNum() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.total_email_num</code>. 本次简历搬家的邮件总数
     */
    public void setTotalEmailNum(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.total_email_num</code>. 本次简历搬家的邮件总数
     */
    public Integer getTotalEmailNum() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_profile_move_record.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_profile_move_record.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Byte, Integer, Byte, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Integer, Byte, Integer, Byte, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.PROFILE_MOVE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.THIRDPARTY_COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CURRENT_EMAIL_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.TOTAL_EMAIL_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.UPDATE_TIME;
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
        return getProfileMoveId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getThirdpartyCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getCrawlType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCrawlNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCurrentEmailNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getTotalEmailNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value2(Integer value) {
        setProfileMoveId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value3(Integer value) {
        setThirdpartyCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value4(Byte value) {
        setCrawlType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value5(Integer value) {
        setCrawlNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value6(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value7(Integer value) {
        setCurrentEmailNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value8(Integer value) {
        setTotalEmailNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value9(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord value10(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileMoveRecordRecord values(Integer value1, Integer value2, Integer value3, Byte value4, Integer value5, Byte value6, Integer value7, Integer value8, Timestamp value9, Timestamp value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TalentpoolProfileMoveRecordRecord
     */
    public TalentpoolProfileMoveRecordRecord() {
        super(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD);
    }

    /**
     * Create a detached, initialised TalentpoolProfileMoveRecordRecord
     */
    public TalentpoolProfileMoveRecordRecord(Integer id, Integer profileMoveId, Integer thirdpartyCompanyId, Byte crawlType, Integer crawlNum, Byte status, Integer currentEmailNum, Integer totalEmailNum, Timestamp createTime, Timestamp updateTime) {
        super(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD);

        set(0, id);
        set(1, profileMoveId);
        set(2, thirdpartyCompanyId);
        set(3, crawlType);
        set(4, crawlNum);
        set(5, status);
        set(6, currentEmailNum);
        set(7, totalEmailNum);
        set(8, createTime);
        set(9, updateTime);
    }
}
