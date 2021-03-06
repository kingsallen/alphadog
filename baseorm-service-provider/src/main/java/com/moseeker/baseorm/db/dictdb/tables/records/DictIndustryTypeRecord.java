/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictIndustryType;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 行业一级分类字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictIndustryTypeRecord extends UpdatableRecordImpl<DictIndustryTypeRecord> implements Record7<Integer, String, String, String, String, String, String> {

    private static final long serialVersionUID = 636339670;

    /**
     * Setter for <code>dictdb.dict_industry_type.code</code>. 字典code
     */
    public void setCode(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.code</code>. 字典code
     */
    public Integer getCode() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.name</code>. 字典name
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.name</code>. 字典name
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.company_img</code>. 行业背景图，公司背景
     */
    public void setCompanyImg(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.company_img</code>. 行业背景图，公司背景
     */
    public String getCompanyImg() {
        return (String) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.job_img</code>. 行业背景图，职位背景
     */
    public void setJobImg(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.job_img</code>. 行业背景图，职位背景
     */
    public String getJobImg() {
        return (String) get(3);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.team_img</code>. 行业背景图，团队背景
     */
    public void setTeamImg(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.team_img</code>. 行业背景图，团队背景
     */
    public String getTeamImg() {
        return (String) get(4);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.pc_img</code>. 行业背景图，pc背景
     */
    public void setPcImg(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.pc_img</code>. 行业背景图，pc背景
     */
    public String getPcImg() {
        return (String) get(5);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.ename</code>. 英文字段
     */
    public void setEname(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.ename</code>. 英文字段
     */
    public String getEname() {
        return (String) get(6);
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
    public Row7<Integer, String, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.COMPANY_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.JOB_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.TEAM_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.PC_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.ENAME;
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
    public String value3() {
        return getCompanyImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getJobImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getTeamImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPcImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getEname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value1(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value3(String value) {
        setCompanyImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value4(String value) {
        setJobImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value5(String value) {
        setTeamImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value6(String value) {
        setPcImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value7(String value) {
        setEname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7) {
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
     * Create a detached DictIndustryTypeRecord
     */
    public DictIndustryTypeRecord() {
        super(DictIndustryType.DICT_INDUSTRY_TYPE);
    }

    /**
     * Create a detached, initialised DictIndustryTypeRecord
     */
    public DictIndustryTypeRecord(Integer code, String name, String companyImg, String jobImg, String teamImg, String pcImg, String ename) {
        super(DictIndustryType.DICT_INDUSTRY_TYPE);

        set(0, code);
        set(1, name);
        set(2, companyImg);
        set(3, jobImg);
        set(4, teamImg);
        set(5, pcImg);
        set(6, ename);
    }
}
