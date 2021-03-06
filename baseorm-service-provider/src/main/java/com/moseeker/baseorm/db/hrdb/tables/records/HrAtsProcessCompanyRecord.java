/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrAtsProcessCompany;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * ats流程企业端配置项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsProcessCompanyRecord extends UpdatableRecordImpl<HrAtsProcessCompanyRecord> implements Record7<Integer, String, Integer, Integer, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1262326146;

    /**
     * Setter for <code>hrdb.hr_ats_process_company.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_ats_process_company.name</code>. 流程名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.name</code>. 流程名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_ats_process_company.company_id</code>. 所属公司 hrdb.hr_company.id
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.company_id</code>. 所属公司 hrdb.hr_company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_ats_process_company.disable</code>. 是否有效 0有效 1无效
     */
    public void setDisable(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.disable</code>. 是否有效 0有效 1无效
     */
    public Integer getDisable() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_ats_process_company.description</code>. 描述
     */
    public void setDescription(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.description</code>. 描述
     */
    public String getDescription() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_ats_process_company.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_ats_process_company.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_process_company.update_time</code>. 更新时间
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
    public Row7<Integer, String, Integer, Integer, String, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, Integer, String, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY.UPDATE_TIME;
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
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDescription();
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
    public HrAtsProcessCompanyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord value3(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord value4(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord value5(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsProcessCompanyRecord values(Integer value1, String value2, Integer value3, Integer value4, String value5, Timestamp value6, Timestamp value7) {
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
     * Create a detached HrAtsProcessCompanyRecord
     */
    public HrAtsProcessCompanyRecord() {
        super(HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY);
    }

    /**
     * Create a detached, initialised HrAtsProcessCompanyRecord
     */
    public HrAtsProcessCompanyRecord(Integer id, String name, Integer companyId, Integer disable, String description, Timestamp createTime, Timestamp updateTime) {
        super(HrAtsProcessCompany.HR_ATS_PROCESS_COMPANY);

        set(0, id);
        set(1, name);
        set(2, companyId);
        set(3, disable);
        set(4, description);
        set(5, createTime);
        set(6, updateTime);
    }
}
