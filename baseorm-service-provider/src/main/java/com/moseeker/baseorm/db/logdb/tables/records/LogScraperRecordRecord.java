/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.records;


import com.moseeker.baseorm.db.logdb.tables.LogScraperRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * c端导入日志明细表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogScraperRecordRecord extends UpdatableRecordImpl<LogScraperRecordRecord> implements Record8<Integer, Integer, String, Byte, Timestamp, String, String, Timestamp> {

    private static final long serialVersionUID = 1265806928;

    /**
     * Setter for <code>logdb.log_scraper_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.channel</code>. 1   51job， 2 liepin， 3 zhaopin，4 linkedin , 5 最佳东方 ， 8 jobsdb
     */
    public void setChannel(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.channel</code>. 1   51job， 2 liepin， 3 zhaopin，4 linkedin , 5 最佳东方 ， 8 jobsdb
     */
    public Integer getChannel() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.request</code>.
     */
    public void setRequest(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.request</code>.
     */
    public String getRequest() {
        return (String) get(2);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.status</code>. 导入最终状态 0成功 1密码错 2验证码错 4 解析错误 5没有简历 100新建待处理
     */
    public void setStatus(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.status</code>. 导入最终状态 0成功 1密码错 2验证码错 4 解析错误 5没有简历 100新建待处理
     */
    public Byte getStatus() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.fetched_resume</code>. 从第三方获取到的原始简历html
     */
    public void setFetchedResume(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.fetched_resume</code>. 从第三方获取到的原始简历html
     */
    public String getFetchedResume() {
        return (String) get(5);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.parsed_resume</code>. 通过parser解析后的格式json
     */
    public void setParsedResume(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.parsed_resume</code>. 通过parser解析后的格式json
     */
    public String getParsedResume() {
        return (String) get(6);
    }

    /**
     * Setter for <code>logdb.log_scraper_record.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>logdb.log_scraper_record.update_time</code>.
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
    public Row8<Integer, Integer, String, Byte, Timestamp, String, String, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, Byte, Timestamp, String, String, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.CHANNEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.REQUEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.FETCHED_RESUME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.PARSED_RESUME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return LogScraperRecord.LOG_SCRAPER_RECORD.UPDATE_TIME;
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
        return getChannel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getRequest();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getStatus();
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
    public String value6() {
        return getFetchedResume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getParsedResume();
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
    public LogScraperRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value2(Integer value) {
        setChannel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value3(String value) {
        setRequest(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value4(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value6(String value) {
        setFetchedResume(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value7(String value) {
        setParsedResume(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecordRecord values(Integer value1, Integer value2, String value3, Byte value4, Timestamp value5, String value6, String value7, Timestamp value8) {
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
     * Create a detached LogScraperRecordRecord
     */
    public LogScraperRecordRecord() {
        super(LogScraperRecord.LOG_SCRAPER_RECORD);
    }

    /**
     * Create a detached, initialised LogScraperRecordRecord
     */
    public LogScraperRecordRecord(Integer id, Integer channel, String request, Byte status, Timestamp createTime, String fetchedResume, String parsedResume, Timestamp updateTime) {
        super(LogScraperRecord.LOG_SCRAPER_RECORD);

        set(0, id);
        set(1, channel);
        set(2, request);
        set(3, status);
        set(4, createTime);
        set(5, fetchedResume);
        set(6, parsedResume);
        set(7, updateTime);
    }
}
