/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.HrEmployeeCustomFields;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 员工认证自定义字段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCustomFieldsRecord extends UpdatableRecordImpl<HrEmployeeCustomFieldsRecord> implements Record9<Integer, Integer, String, String, Integer, Byte, Integer, Integer, Integer> {

    private static final long serialVersionUID = -1816320062;

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.company_id</code>. sys_company.id, 部门ID
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.company_id</code>. sys_company.id, 部门ID
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.fname</code>. 自定义字段名
     */
    public void setFname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.fname</code>. 自定义字段名
     */
    public String getFname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.fvalues</code>. 自定义字段可选值
     */
    public void setFvalues(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.fvalues</code>. 自定义字段可选值
     */
    public String getFvalues() {
        return (String) get(3);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.forder</code>. 排序优先级，0:不显示, 正整数由小到大排序
     */
    public void setForder(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.forder</code>. 排序优先级，0:不显示, 正整数由小到大排序
     */
    public Integer getForder() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.disable</code>. 是否停用 0:不停用(有效)， 1:停用(无效)
     */
    public void setDisable(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.disable</code>. 是否停用 0:不停用(有效)， 1:停用(无效)
     */
    public Byte getDisable() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.mandatory</code>. 是否必填 0:不是必填, 1:必填
     */
    public void setMandatory(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.mandatory</code>. 是否必填 0:不是必填, 1:必填
     */
    public Integer getMandatory() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.status</code>. 0: 正常 1: 被删除
     */
    public void setStatus(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.status</code>. 0: 正常 1: 被删除
     */
    public Integer getStatus() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>referraldb.hr_employee_custom_fields.option_type</code>. 选项类型  0:下拉选项, 1:文本
     */
    public void setOptionType(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>referraldb.hr_employee_custom_fields.option_type</code>. 选项类型  0:下拉选项, 1:文本
     */
    public Integer getOptionType() {
        return (Integer) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, String, String, Integer, Byte, Integer, Integer, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, String, String, Integer, Byte, Integer, Integer, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FVALUES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.MANDATORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.OPTION_TYPE;
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
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getFvalues();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getForder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getMandatory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getOptionType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value3(String value) {
        setFname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value4(String value) {
        setFvalues(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value5(Integer value) {
        setForder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value6(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value7(Integer value) {
        setMandatory(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value8(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord value9(Integer value) {
        setOptionType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFieldsRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, Byte value6, Integer value7, Integer value8, Integer value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrEmployeeCustomFieldsRecord
     */
    public HrEmployeeCustomFieldsRecord() {
        super(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS);
    }

    /**
     * Create a detached, initialised HrEmployeeCustomFieldsRecord
     */
    public HrEmployeeCustomFieldsRecord(Integer id, Integer companyId, String fname, String fvalues, Integer forder, Byte disable, Integer mandatory, Integer status, Integer optionType) {
        super(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS);

        set(0, id);
        set(1, companyId);
        set(2, fname);
        set(3, fvalues);
        set(4, forder);
        set(5, disable);
        set(6, mandatory);
        set(7, status);
        set(8, optionType);
    }
}