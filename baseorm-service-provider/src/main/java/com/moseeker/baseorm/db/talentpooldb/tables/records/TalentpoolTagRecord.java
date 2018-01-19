/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.records;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTag;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 人才库标签表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolTagRecord extends UpdatableRecordImpl<TalentpoolTagRecord> implements Record6<Integer, String, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1146801300;

    /**
     * Setter for <code>talentpooldb.talentpool_tag.id</code>. 主 key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_tag.id</code>. 主 key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_tag.name</code>. 标签名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_tag.name</code>. 标签名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_tag.hr_id</code>. h编号
     */
    public void setHrId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_tag.hr_id</code>. h编号
     */
    public Integer getHrId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_tag.talent_num</code>. 人才数量
     */
    public void setTalentNum(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_tag.talent_num</code>. 人才数量
     */
    public Integer getTalentNum() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_tag.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_tag.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_tag.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_tag.update_time</code>.
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
    public Row6<Integer, String, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TalentpoolTag.TALENTPOOL_TAG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TalentpoolTag.TALENTPOOL_TAG.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TalentpoolTag.TALENTPOOL_TAG.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TalentpoolTag.TALENTPOOL_TAG.TALENT_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TalentpoolTag.TALENTPOOL_TAG.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return TalentpoolTag.TALENTPOOL_TAG.UPDATE_TIME;
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getTalentNum();
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
    public TalentpoolTagRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTagRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTagRecord value3(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTagRecord value4(Integer value) {
        setTalentNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTagRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTagRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolTagRecord values(Integer value1, String value2, Integer value3, Integer value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached TalentpoolTagRecord
     */
    public TalentpoolTagRecord() {
        super(TalentpoolTag.TALENTPOOL_TAG);
    }

    /**
     * Create a detached, initialised TalentpoolTagRecord
     */
    public TalentpoolTagRecord(Integer id, String name, Integer hrId, Integer talentNum, Timestamp createTime, Timestamp updateTime) {
        super(TalentpoolTag.TALENTPOOL_TAG);

        set(0, id);
        set(1, name);
        set(2, hrId);
        set(3, talentNum);
        set(4, createTime);
        set(5, updateTime);
    }
}
