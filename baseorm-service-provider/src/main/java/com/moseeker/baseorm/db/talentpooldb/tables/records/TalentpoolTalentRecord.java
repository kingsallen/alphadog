/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.records;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTalent;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 人才库人才表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolTalentRecord extends UpdatableRecordImpl<TalentpoolTalentRecord> implements Record7<Integer, Integer, Integer, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1569559572;

    /**
     * Setter for <code>talentpooldb.talentpool_talent.user_id</code>. 用户编号
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.user_id</code>. 用户编号
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_talent.company_id</code>. 公司编号
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.company_id</code>. 公司编号
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_talent.collect_num</code>. 收藏次数
     */
    public void setCollectNum(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.collect_num</code>. 收藏次数
     */
    public Integer getCollectNum() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_talent.public_num</code>. 公开次数
     */
    public void setPublicNum(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.public_num</code>. 公开次数
     */
    public Integer getPublicNum() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_talent.upload</code>. 0 非上传，1上传
     */
    public void setUpload(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.upload</code>. 0 非上传，1上传
     */
    public Byte getUpload() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_talent.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_talent.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_talent.update_time</code>.
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
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TalentpoolTalent.TALENTPOOL_TALENT.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TalentpoolTalent.TALENTPOOL_TALENT.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TalentpoolTalent.TALENTPOOL_TALENT.COLLECT_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TalentpoolTalent.TALENTPOOL_TALENT.PUBLIC_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return TalentpoolTalent.TALENTPOOL_TALENT.UPLOAD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return TalentpoolTalent.TALENTPOOL_TALENT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return TalentpoolTalent.TALENTPOOL_TALENT.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCollectNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getPublicNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getUpload();
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
    public TalentpoolTalentRecord value1(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord value3(Integer value) {
        setCollectNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord value4(Integer value) {
        setPublicNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord value5(Byte value) {
        setUpload(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTalentRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Byte value5, Timestamp value6, Timestamp value7) {
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
     * Create a detached TalentpoolTalentRecord
     */
    public TalentpoolTalentRecord() {
        super(TalentpoolTalent.TALENTPOOL_TALENT);
    }

    /**
     * Create a detached, initialised TalentpoolTalentRecord
     */
    public TalentpoolTalentRecord(Integer userId, Integer companyId, Integer collectNum, Integer publicNum, Byte upload, Timestamp createTime, Timestamp updateTime) {
        super(TalentpoolTalent.TALENTPOOL_TALENT);

        set(0, userId);
        set(1, companyId);
        set(2, collectNum);
        set(3, publicNum);
        set(4, upload);
        set(5, createTime);
        set(6, updateTime);
    }
}
