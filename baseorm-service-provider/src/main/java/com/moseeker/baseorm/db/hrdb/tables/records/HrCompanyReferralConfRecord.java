/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyReferralConf;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 公司内推配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyReferralConfRecord extends UpdatableRecordImpl<HrCompanyReferralConfRecord> implements Record8<Integer, Integer, String, String, Byte, Timestamp, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1517681155;

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.company_id</code>. hr_company.id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.company_id</code>. hr_company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.link</code>. 内推政策链接
     */
    public void setLink(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.link</code>. 内推政策链接
     */
    public String getLink() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.TEXT</code>. 内推政策文案
     */
    public void setText(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.TEXT</code>. 内推政策文案
     */
    public String getText() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.priority</code>. 内推政策优先级 0 表示没有配置内推政策 1 链接  2 文案
     */
    public void setPriority(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.priority</code>. 内推政策优先级 0 表示没有配置内推政策 1 链接  2 文案
     */
    public Byte getPriority() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.text_update_time</code>. 文案更新时间
     */
    public void setTextUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.text_update_time</code>. 文案更新时间
     */
    public Timestamp getTextUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_company_referral_conf.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_referral_conf.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    public Row8<Integer, Integer, String, String, Byte, Timestamp, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, String, Byte, Timestamp, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.TEXT_UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF.UPDATE_TIME;
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
        return getLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getTextUpdateTime();
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
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value3(String value) {
        setLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value4(String value) {
        setText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value5(Byte value) {
        setPriority(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value6(Timestamp value) {
        setTextUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyReferralConfRecord values(Integer value1, Integer value2, String value3, String value4, Byte value5, Timestamp value6, Timestamp value7, Timestamp value8) {
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
     * Create a detached HrCompanyReferralConfRecord
     */
    public HrCompanyReferralConfRecord() {
        super(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF);
    }

    /**
     * Create a detached, initialised HrCompanyReferralConfRecord
     */
    public HrCompanyReferralConfRecord(Integer id, Integer companyId, String link, String text, Byte priority, Timestamp textUpdateTime, Timestamp createTime, Timestamp updateTime) {
        super(HrCompanyReferralConf.HR_COMPANY_REFERRAL_CONF);

        set(0, id);
        set(1, companyId);
        set(2, link);
        set(3, text);
        set(4, priority);
        set(5, textUpdateTime);
        set(6, createTime);
        set(7, updateTime);
    }
}
