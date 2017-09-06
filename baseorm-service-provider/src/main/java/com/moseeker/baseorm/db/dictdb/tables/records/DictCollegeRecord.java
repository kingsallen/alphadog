/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictCollege;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.TableRecordImpl;


/**
 * 学校字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictCollegeRecord extends TableRecordImpl<DictCollegeRecord> implements Record4<Integer, String, Integer, String> {

    private static final long serialVersionUID = 1984726245;

    /**
     * Setter for <code>dictdb.dict_college.code</code>. 字典code
     */
    public void setCode(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_college.code</code>. 字典code
     */
    public Integer getCode() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_college.name</code>. 字典name
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_college.name</code>. 字典name
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_college.province</code>. 院校所在地
     */
    public void setProvince(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_college.province</code>. 院校所在地
     */
    public Integer getProvince() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_college.logo</code>. 院校logo
     */
    public void setLogo(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_college.logo</code>. 院校logo
     */
    public String getLogo() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Integer, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Integer, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DictCollege.DICT_COLLEGE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return DictCollege.DICT_COLLEGE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return DictCollege.DICT_COLLEGE.PROVINCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictCollege.DICT_COLLEGE.LOGO;
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getProvince();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getLogo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCollegeRecord value1(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCollegeRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCollegeRecord value3(Integer value) {
        setProvince(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCollegeRecord value4(String value) {
        setLogo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCollegeRecord values(Integer value1, String value2, Integer value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictCollegeRecord
     */
    public DictCollegeRecord() {
        super(DictCollege.DICT_COLLEGE);
    }

    /**
     * Create a detached, initialised DictCollegeRecord
     */
    public DictCollegeRecord(Integer code, String name, Integer province, String logo) {
        super(DictCollege.DICT_COLLEGE);

        set(0, code);
        set(1, name);
        set(2, province);
        set(3, logo);
    }
}
