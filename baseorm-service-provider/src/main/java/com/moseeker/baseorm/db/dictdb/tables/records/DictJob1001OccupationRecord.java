/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictJob1001Occupation;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 一览人才的职位表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictJob1001OccupationRecord extends UpdatableRecordImpl<DictJob1001OccupationRecord> implements Record8<Integer, Integer, String, String, Short, Short, Timestamp, String> {

    private static final long serialVersionUID = 1222355415;

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.code</code>. 职能id
     */
    public void setCode(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.code</code>. 职能id
     */
    public Integer getCode() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.parent_id</code>. 父Id，上一级职能的ID
     */
    public void setParentId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.parent_id</code>. 父Id，上一级职能的ID
     */
    public Integer getParentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.name</code>. 职能名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.name</code>. 职能名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.code_other</code>. 第三方职能id
     */
    public void setCodeOther(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.code_other</code>. 第三方职能id
     */
    public String getCodeOther() {
        return (String) get(3);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.level</code>. 职能级别 1是一级2是二级依次类推
     */
    public void setLevel(Short value) {
        set(4, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.level</code>. 职能级别 1是一级2是二级依次类推
     */
    public Short getLevel() {
        return (Short) get(4);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.status</code>. 只能状态 0 是有效 1是无效
     */
    public void setStatus(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.status</code>. 只能状态 0 是有效 1是无效
     */
    public Short getStatus() {
        return (Short) get(5);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>dictdb.dict_job1001_occupation.subsite</code>. 所属发布网站
     */
    public void setSubsite(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>dictdb.dict_job1001_occupation.subsite</code>. 所属发布网站
     */
    public String getSubsite() {
        return (String) get(7);
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
    public Row8<Integer, Integer, String, String, Short, Short, Timestamp, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, String, Short, Short, Timestamp, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CODE_OTHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field5() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field6() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.SUBSITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCodeOther();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value5() {
        return getLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value6() {
        return getStatus();
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
    public String value8() {
        return getSubsite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value1(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value2(Integer value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value4(String value) {
        setCodeOther(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value5(Short value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value6(Short value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord value8(String value) {
        setSubsite(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001OccupationRecord values(Integer value1, Integer value2, String value3, String value4, Short value5, Short value6, Timestamp value7, String value8) {
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
     * Create a detached DictJob1001OccupationRecord
     */
    public DictJob1001OccupationRecord() {
        super(DictJob1001Occupation.DICT_JOB1001_OCCUPATION);
    }

    /**
     * Create a detached, initialised DictJob1001OccupationRecord
     */
    public DictJob1001OccupationRecord(Integer code, Integer parentId, String name, String codeOther, Short level, Short status, Timestamp createTime, String subsite) {
        super(DictJob1001Occupation.DICT_JOB1001_OCCUPATION);

        set(0, code);
        set(1, parentId);
        set(2, name);
        set(3, codeOther);
        set(4, level);
        set(5, status);
        set(6, createTime);
        set(7, subsite);
    }
}