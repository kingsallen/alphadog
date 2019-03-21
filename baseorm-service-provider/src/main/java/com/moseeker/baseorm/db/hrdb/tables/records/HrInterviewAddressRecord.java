/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrInterviewAddress;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 面试地址表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewAddressRecord extends UpdatableRecordImpl<HrInterviewAddressRecord> implements Record9<Integer, Integer, Integer, String, String, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1543778746;

    /**
     * Setter for <code>hrdb.hr_interview_address.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.company_id</code>. 公司id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.company_id</code>. 公司id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.hr_id</code>. hr编号
     */
    public void setHrId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.hr_id</code>. hr编号
     */
    public Integer getHrId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.name</code>. 面试地址名称
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.name</code>. 面试地址名称
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.address</code>. 面试地址详情
     */
    public void setAddress(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.address</code>. 面试地址详情
     */
    public String getAddress() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.disable</code>. 0是启用 1是禁用 2逻辑删除
     */
    public void setDisable(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.disable</code>. 0是启用 1是禁用 2逻辑删除
     */
    public Integer getDisable() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.default_flag</code>. 是否是默认地址 0不是 1是
     */
    public void setDefaultFlag(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.default_flag</code>. 是否是默认地址 0不是 1是
     */
    public Integer getDefaultFlag() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_interview_address.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_interview_address.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
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
    public Row9<Integer, Integer, Integer, String, String, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Integer, String, String, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.DEFAULT_FLAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return HrInterviewAddress.HR_INTERVIEW_ADDRESS.UPDATE_TIME;
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
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAddress();
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
        return getDefaultFlag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value3(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value4(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value5(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value6(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value7(Integer value) {
        setDefaultFlag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewAddressRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, Integer value6, Integer value7, Timestamp value8, Timestamp value9) {
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
     * Create a detached HrInterviewAddressRecord
     */
    public HrInterviewAddressRecord() {
        super(HrInterviewAddress.HR_INTERVIEW_ADDRESS);
    }

    /**
     * Create a detached, initialised HrInterviewAddressRecord
     */
    public HrInterviewAddressRecord(Integer id, Integer companyId, Integer hrId, String name, String address, Integer disable, Integer defaultFlag, Timestamp createTime, Timestamp updateTime) {
        super(HrInterviewAddress.HR_INTERVIEW_ADDRESS);

        set(0, id);
        set(1, companyId);
        set(2, hrId);
        set(3, name);
        set(4, address);
        set(5, disable);
        set(6, defaultFlag);
        set(7, createTime);
        set(8, updateTime);
    }
}
