/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 51的职位表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dict_51jobOccupationRecord extends UpdatableRecordImpl<Dict_51jobOccupationRecord> implements Record7<Integer, Integer, String, String, Short, Short, Timestamp> {

    private static final long serialVersionUID = 1681412120;

    /**
     * Setter for <code>dictdb.dict_51job_occupation.code</code>. 职能id
     */
    public void setCode(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.code</code>. 职能id
     */
    public Integer getCode() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_51job_occupation.parent_id</code>. 父Id，上一级职能的ID
     */
    public void setParentId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.parent_id</code>. 父Id，上一级职能的ID
     */
    public Integer getParentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_51job_occupation.name</code>. 职能名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.name</code>. 职能名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_51job_occupation.code_other</code>. 第三方职能id
     */
    public void setCodeOther(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.code_other</code>. 第三方职能id
     */
    public String getCodeOther() {
        return (String) get(3);
    }

    /**
     * Setter for <code>dictdb.dict_51job_occupation.level</code>. 职能级别 1是一级2是二级依次类推
     */
    public void setLevel(Short value) {
        set(4, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.level</code>. 职能级别 1是一级2是二级依次类推
     */
    public Short getLevel() {
        return (Short) get(4);
    }

    /**
     * Setter for <code>dictdb.dict_51job_occupation.status</code>. 只能状态 0 是有效 1是无效
     */
    public void setStatus(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.status</code>. 只能状态 0 是有效 1是无效
     */
    public Short getStatus() {
        return (Short) get(5);
    }

    /**
     * Setter for <code>dictdb.dict_51job_occupation.createTime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>dictdb.dict_51job_occupation.createTime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
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
    public Row7<Integer, Integer, String, String, Short, Short, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Short, Short, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE_OTHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field5() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field6() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CREATETIME;
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
        return getCreatetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value1(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value2(Integer value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value4(String value) {
        setCodeOther(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value5(Short value) {
        setLevel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value6(Short value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord value7(Timestamp value) {
        setCreatetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_51jobOccupationRecord values(Integer value1, Integer value2, String value3, String value4, Short value5, Short value6, Timestamp value7) {
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
     * Create a detached Dict_51jobOccupationRecord
     */
    public Dict_51jobOccupationRecord() {
        super(Dict_51jobOccupation.DICT_51JOB_OCCUPATION);
    }

    /**
     * Create a detached, initialised Dict_51jobOccupationRecord
     */
    public Dict_51jobOccupationRecord(Integer code, Integer parentId, String name, String codeOther, Short level, Short status, Timestamp createtime) {
        super(Dict_51jobOccupation.DICT_51JOB_OCCUPATION);

        set(0, code);
        set(1, parentId);
        set(2, name);
        set(3, codeOther);
        set(4, level);
        set(5, status);
        set(6, createtime);
    }
}
