/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrAtsDepartment;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 部门机构管理表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsDepartmentRecord extends UpdatableRecordImpl<HrAtsDepartmentRecord> implements Record7<Integer, Integer, Integer, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 879346165;

    /**
     * Setter for <code>hrdb.hr_ats_department.id</code>. 自增id
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.id</code>. 自增id
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_ats_department.company_id</code>. 公司id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.company_id</code>. 公司id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_ats_department.parent_id</code>. 上级部门id
     */
    public void setParentId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.parent_id</code>. 上级部门id
     */
    public Integer getParentId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_ats_department.department_code</code>. 部门编码
     */
    public void setDepartmentCode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.department_code</code>. 部门编码
     */
    public String getDepartmentCode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_ats_department.department_name</code>. 部门名称
     */
    public void setDepartmentName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.department_name</code>. 部门名称
     */
    public String getDepartmentName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_ats_department.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_ats_department.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_ats_department.update_time</code>. 更新时间
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
    public Row7<Integer, Integer, Integer, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.DEPARTMENT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.DEPARTMENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrAtsDepartment.HR_ATS_DEPARTMENT.UPDATE_TIME;
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
    public Integer value3() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDepartmentCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDepartmentName();
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
    public HrAtsDepartmentRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord value3(Integer value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord value4(String value) {
        setDepartmentCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord value5(String value) {
        setDepartmentName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartmentRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, Timestamp value6, Timestamp value7) {
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
     * Create a detached HrAtsDepartmentRecord
     */
    public HrAtsDepartmentRecord() {
        super(HrAtsDepartment.HR_ATS_DEPARTMENT);
    }

    /**
     * Create a detached, initialised HrAtsDepartmentRecord
     */
    public HrAtsDepartmentRecord(Integer id, Integer companyId, Integer parentId, String departmentCode, String departmentName, Timestamp createTime, Timestamp updateTime) {
        super(HrAtsDepartment.HR_ATS_DEPARTMENT);

        set(0, id);
        set(1, companyId);
        set(2, parentId);
        set(3, departmentCode);
        set(4, departmentName);
        set(5, createTime);
        set(6, updateTime);
    }
}
