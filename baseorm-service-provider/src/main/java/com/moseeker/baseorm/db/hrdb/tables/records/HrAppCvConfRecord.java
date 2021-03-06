/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrAppCvConf;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 企业申请简历校验配置
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAppCvConfRecord extends UpdatableRecordImpl<HrAppCvConfRecord> implements Record10<Integer, String, Integer, Timestamp, Timestamp, Integer, Integer, Integer, Integer, String> {

    private static final long serialVersionUID = 2054910342;

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.name</code>. 属性含义
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.name</code>. 属性含义
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.priority</code>. 排序字段
     */
    public void setPriority(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.priority</code>. 排序字段
     */
    public Integer getPriority() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.disable</code>. 是否禁用 0：可用1：不可用
     */
    public void setDisable(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.disable</code>. 是否禁用 0：可用1：不可用
     */
    public Integer getDisable() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.company_id</code>. 所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id
     */
    public void setCompanyId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.company_id</code>. 所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.hraccount_id</code>. 账号编号 hr_account.id
     */
    public void setHraccountId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.hraccount_id</code>. 账号编号 hr_account.id
     */
    public Integer getHraccountId() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.required</code>. 是否必填项 0：必填项 1：选填项
     */
    public void setRequired(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.required</code>. 是否必填项 0：必填项 1：选填项
     */
    public Integer getRequired() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_app_cv_conf.field_value</code>. 微信端页面标签默认值
     */
    public void setFieldValue(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_app_cv_conf.field_value</code>. 微信端页面标签默认值
     */
    public String getFieldValue() {
        return (String) get(9);
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
    public Row10<Integer, String, Integer, Timestamp, Timestamp, Integer, Integer, Integer, Integer, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, Integer, Timestamp, Timestamp, Integer, Integer, Integer, Integer, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrAppCvConf.HR_APP_CV_CONF.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrAppCvConf.HR_APP_CV_CONF.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrAppCvConf.HR_APP_CV_CONF.PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return HrAppCvConf.HR_APP_CV_CONF.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrAppCvConf.HR_APP_CV_CONF.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrAppCvConf.HR_APP_CV_CONF.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrAppCvConf.HR_APP_CV_CONF.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return HrAppCvConf.HR_APP_CV_CONF.HRACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return HrAppCvConf.HR_APP_CV_CONF.REQUIRED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return HrAppCvConf.HR_APP_CV_CONF.FIELD_VALUE;
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
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getHraccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getRequired();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getFieldValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value3(Integer value) {
        setPriority(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value4(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value5(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value6(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value7(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value8(Integer value) {
        setHraccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value9(Integer value) {
        setRequired(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord value10(String value) {
        setFieldValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAppCvConfRecord values(Integer value1, String value2, Integer value3, Timestamp value4, Timestamp value5, Integer value6, Integer value7, Integer value8, Integer value9, String value10) {
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
     * Create a detached HrAppCvConfRecord
     */
    public HrAppCvConfRecord() {
        super(HrAppCvConf.HR_APP_CV_CONF);
    }

    /**
     * Create a detached, initialised HrAppCvConfRecord
     */
    public HrAppCvConfRecord(Integer id, String name, Integer priority, Timestamp createTime, Timestamp updateTime, Integer disable, Integer companyId, Integer hraccountId, Integer required, String fieldValue) {
        super(HrAppCvConf.HR_APP_CV_CONF);

        set(0, id);
        set(1, name);
        set(2, priority);
        set(3, createTime);
        set(4, updateTime);
        set(5, disable);
        set(6, companyId);
        set(7, hraccountId);
        set(8, required);
        set(9, fieldValue);
    }
}
