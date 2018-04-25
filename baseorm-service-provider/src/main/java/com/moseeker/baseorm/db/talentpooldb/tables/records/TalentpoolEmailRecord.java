/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.records;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolEmail;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 人才库邮件模板表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolEmailRecord extends UpdatableRecordImpl<TalentpoolEmailRecord> implements Record8<Integer, Integer, String, String, Integer, Timestamp, Timestamp, Integer> {

    private static final long serialVersionUID = -80560374;

    /**
     * Setter for <code>talentpooldb.talentpool_email.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.disable</code>. 状态 0是不开启 1是开启 2 关闭
     */
    public void setDisable(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.disable</code>. 状态 0是不开启 1是开启 2 关闭
     */
    public Integer getDisable() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.inscribe</code>. 落款
     */
    public void setInscribe(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.inscribe</code>. 落款
     */
    public String getInscribe() {
        return (String) get(2);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.context</code>. 邮件内容
     */
    public void setContext(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.context</code>. 邮件内容
     */
    public String getContext() {
        return (String) get(3);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.config_id</code>. 邮件配置模板id
     */
    public void setConfigId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.config_id</code>. 邮件配置模板id
     */
    public Integer getConfigId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>talentpooldb.talentpool_email.company_id</code>.
     */
    public void setCompanyId(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>talentpooldb.talentpool_email.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(7);
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
    public Row8<Integer, Integer, String, String, Integer, Timestamp, Timestamp, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, String, Integer, Timestamp, Timestamp, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.INSCRIBE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.CONTEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.CONFIG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return TalentpoolEmail.TALENTPOOL_EMAIL.COMPANY_ID;
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
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getInscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getConfigId();
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
    public Integer value8() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value2(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value3(String value) {
        setInscribe(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value4(String value) {
        setContext(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value5(Integer value) {
        setConfigId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord value8(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolEmailRecord values(Integer value1, Integer value2, String value3, String value4, Integer value5, Timestamp value6, Timestamp value7, Integer value8) {
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
     * Create a detached TalentpoolEmailRecord
     */
    public TalentpoolEmailRecord() {
        super(TalentpoolEmail.TALENTPOOL_EMAIL);
    }

    /**
     * Create a detached, initialised TalentpoolEmailRecord
     */
    public TalentpoolEmailRecord(Integer id, Integer disable, String inscribe, String context, Integer configId, Timestamp createTime, Timestamp updateTime, Integer companyId) {
        super(TalentpoolEmail.TALENTPOOL_EMAIL);

        set(0, id);
        set(1, disable);
        set(2, inscribe);
        set(3, context);
        set(4, configId);
        set(5, createTime);
        set(6, updateTime);
        set(7, companyId);
    }
}
