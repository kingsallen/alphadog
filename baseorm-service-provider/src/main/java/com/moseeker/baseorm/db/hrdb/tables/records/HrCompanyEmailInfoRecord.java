/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyEmailInfo;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 企业邮件总量信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyEmailInfoRecord extends UpdatableRecordImpl<HrCompanyEmailInfoRecord> implements Record6<Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1079687902;

    /**
     * Setter for <code>hrdb.hr_company_email_info.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_email_info.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_company_email_info.company_id</code>. 公司id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_email_info.company_id</code>. 公司id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_company_email_info.total</code>. 邮件总量
     */
    public void setTotal(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_email_info.total</code>. 邮件总量
     */
    public Integer getTotal() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_company_email_info.balance</code>. 邮件剩余量
     */
    public void setBalance(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_email_info.balance</code>. 邮件剩余量
     */
    public Integer getBalance() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_company_email_info.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_email_info.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_company_email_info.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_email_info.update_time</code>. 修改时间
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
    public Row6<Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO.TOTAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO.BALANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO.UPDATE_TIME;
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
        return getTotal();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getBalance();
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
    public HrCompanyEmailInfoRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyEmailInfoRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyEmailInfoRecord value3(Integer value) {
        setTotal(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyEmailInfoRecord value4(Integer value) {
        setBalance(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyEmailInfoRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyEmailInfoRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyEmailInfoRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached HrCompanyEmailInfoRecord
     */
    public HrCompanyEmailInfoRecord() {
        super(HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO);
    }

    /**
     * Create a detached, initialised HrCompanyEmailInfoRecord
     */
    public HrCompanyEmailInfoRecord(Integer id, Integer companyId, Integer total, Integer balance, Timestamp createTime, Timestamp updateTime) {
        super(HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO);

        set(0, id);
        set(1, companyId);
        set(2, total);
        set(3, balance);
        set(4, createTime);
        set(5, updateTime);
    }
}
