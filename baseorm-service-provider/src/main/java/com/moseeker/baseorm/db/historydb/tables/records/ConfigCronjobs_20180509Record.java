/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.records;


import com.moseeker.baseorm.db.historydb.tables.ConfigCronjobs_20180509;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigCronjobs_20180509Record extends UpdatableRecordImpl<ConfigCronjobs_20180509Record> implements Record6<Integer, String, String, String, Integer, String> {

    private static final long serialVersionUID = -1144126699;

    /**
     * Setter for <code>historydb.config_cronjobs_20180509.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>historydb.config_cronjobs_20180509.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>historydb.config_cronjobs_20180509.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>historydb.config_cronjobs_20180509.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>historydb.config_cronjobs_20180509.command</code>.
     */
    public void setCommand(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>historydb.config_cronjobs_20180509.command</code>.
     */
    public String getCommand() {
        return (String) get(2);
    }

    /**
     * Setter for <code>historydb.config_cronjobs_20180509.desc</code>.
     */
    public void setDesc(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>historydb.config_cronjobs_20180509.desc</code>.
     */
    public String getDesc() {
        return (String) get(3);
    }

    /**
     * Setter for <code>historydb.config_cronjobs_20180509.check_delay</code>. job开始几分钟后开始检查结果
     */
    public void setCheckDelay(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>historydb.config_cronjobs_20180509.check_delay</code>. job开始几分钟后开始检查结果
     */
    public Integer getCheckDelay() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>historydb.config_cronjobs_20180509.notice_email</code>. email通知地址, 为空使用 alarm@moseeker.com
     */
    public void setNoticeEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>historydb.config_cronjobs_20180509.notice_email</code>. email通知地址, 为空使用 alarm@moseeker.com
     */
    public String getNoticeEmail() {
        return (String) get(5);
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
    public Row6<Integer, String, String, String, Integer, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, String, Integer, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509.COMMAND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509.DESC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509.CHECK_DELAY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509.NOTICE_EMAIL;
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
    public String value3() {
        return getCommand();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCheckDelay();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getNoticeEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record value3(String value) {
        setCommand(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record value4(String value) {
        setDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record value5(Integer value) {
        setCheckDelay(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record value6(String value) {
        setNoticeEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigCronjobs_20180509Record values(Integer value1, String value2, String value3, String value4, Integer value5, String value6) {
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
     * Create a detached ConfigCronjobs_20180509Record
     */
    public ConfigCronjobs_20180509Record() {
        super(ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509);
    }

    /**
     * Create a detached, initialised ConfigCronjobs_20180509Record
     */
    public ConfigCronjobs_20180509Record(Integer id, String name, String command, String desc, Integer checkDelay, String noticeEmail) {
        super(ConfigCronjobs_20180509.CONFIG_CRONJOBS_20180509);

        set(0, id);
        set(1, name);
        set(2, command);
        set(3, desc);
        set(4, checkDelay);
        set(5, noticeEmail);
    }
}
