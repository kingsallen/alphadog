/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.thirdpartydb.tables.records;


import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountJob1001Subsite;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 一览人才的第三方发布网站表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ThirdpartyAccountJob1001SubsiteRecord extends UpdatableRecordImpl<ThirdpartyAccountJob1001SubsiteRecord> implements Record6<Integer, Integer, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 43723694;

    /**
     * Setter for <code>thirdpartydb.thirdparty_account_job1001_subsite.id</code>. 主键
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>thirdpartydb.thirdparty_account_job1001_subsite.id</code>. 主键
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>thirdpartydb.thirdparty_account_job1001_subsite.account_id</code>. 第三方渠道账号的编号
     */
    public void setAccountId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>thirdpartydb.thirdparty_account_job1001_subsite.account_id</code>. 第三方渠道账号的编号
     */
    public Integer getAccountId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>thirdpartydb.thirdparty_account_job1001_subsite.text</code>. 第三方账号对应的发布网站名称
     */
    public void setText(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>thirdpartydb.thirdparty_account_job1001_subsite.text</code>. 第三方账号对应的发布网站名称
     */
    public String getText() {
        return (String) get(2);
    }

    /**
     * Setter for <code>thirdpartydb.thirdparty_account_job1001_subsite.site</code>. 第三方账号对应的发布网站网址(暂时弃用)
     */
    public void setSite(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>thirdpartydb.thirdparty_account_job1001_subsite.site</code>. 第三方账号对应的发布网站网址(暂时弃用)
     */
    public String getSite() {
        return (String) get(3);
    }

    /**
     * Setter for <code>thirdpartydb.thirdparty_account_job1001_subsite.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>thirdpartydb.thirdparty_account_job1001_subsite.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>thirdpartydb.thirdparty_account_job1001_subsite.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>thirdpartydb.thirdparty_account_job1001_subsite.update_time</code>. 更新时间
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
    public Row6<Integer, Integer, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.ACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.SITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.UPDATE_TIME;
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
        return getAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSite();
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
    public ThirdpartyAccountJob1001SubsiteRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001SubsiteRecord value2(Integer value) {
        setAccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001SubsiteRecord value3(String value) {
        setText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001SubsiteRecord value4(String value) {
        setSite(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001SubsiteRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001SubsiteRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdpartyAccountJob1001SubsiteRecord values(Integer value1, Integer value2, String value3, String value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached ThirdpartyAccountJob1001SubsiteRecord
     */
    public ThirdpartyAccountJob1001SubsiteRecord() {
        super(ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE);
    }

    /**
     * Create a detached, initialised ThirdpartyAccountJob1001SubsiteRecord
     */
    public ThirdpartyAccountJob1001SubsiteRecord(Integer id, Integer accountId, String text, String site, Timestamp createTime, Timestamp updateTime) {
        super(ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE);

        set(0, id);
        set(1, accountId);
        set(2, text);
        set(3, site);
        set(4, createTime);
        set(5, updateTime);
    }
}