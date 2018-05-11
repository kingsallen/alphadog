/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.records;


import com.moseeker.baseorm.db.logdb.tables.LogJbEmailparseRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 第三方简历回流email解析日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogJbEmailparseRecordRecord extends UpdatableRecordImpl<LogJbEmailparseRecordRecord> implements Record9<Integer, String, Byte, String, String, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 559502492;

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.from_email</code>.
     */
    public void setFromEmail(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.from_email</code>.
     */
    public String getFromEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.channel</code>. 1：51job, 2liepin, 3zhaopin,6veryest,7yilan 0未知 
     */
    public void setChannel(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.channel</code>. 1：51job, 2liepin, 3zhaopin,6veryest,7yilan 0未知 
     */
    public Byte getChannel() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.to_email</code>.
     */
    public void setToEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.to_email</code>.
     */
    public String getToEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.subject</code>. 邮件主题
     */
    public void setSubject(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.subject</code>. 邮件主题
     */
    public String getSubject() {
        return (String) get(4);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.pid</code>. 职位id
     */
    public void setPid(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.pid</code>. 职位id
     */
    public Integer getPid() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.status</code>. 0成功 1垃圾邮件 2 解析失败 3 入库失败
     */
    public void setStatus(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.status</code>. 0成功 1垃圾邮件 2 解析失败 3 入库失败
     */
    public Byte getStatus() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>logdb.log_jb_emailparse_record.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>logdb.log_jb_emailparse_record.update_time</code>.
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
    public Row9<Integer, String, Byte, String, String, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, String, Byte, String, String, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.FROM_EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.CHANNEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.TO_EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.SUBJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD.UPDATE_TIME;
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
        return getFromEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value3() {
        return getChannel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getToEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getSubject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getStatus();
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
    public LogJbEmailparseRecordRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value2(String value) {
        setFromEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value3(Byte value) {
        setChannel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value4(String value) {
        setToEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value5(String value) {
        setSubject(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value6(Integer value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value7(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogJbEmailparseRecordRecord values(Integer value1, String value2, Byte value3, String value4, String value5, Integer value6, Byte value7, Timestamp value8, Timestamp value9) {
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
     * Create a detached LogJbEmailparseRecordRecord
     */
    public LogJbEmailparseRecordRecord() {
        super(LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD);
    }

    /**
     * Create a detached, initialised LogJbEmailparseRecordRecord
     */
    public LogJbEmailparseRecordRecord(Integer id, String fromEmail, Byte channel, String toEmail, String subject, Integer pid, Byte status, Timestamp createTime, Timestamp updateTime) {
        super(LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD);

        set(0, id);
        set(1, fromEmail);
        set(2, channel);
        set(3, toEmail);
        set(4, subject);
        set(5, pid);
        set(6, status);
        set(7, createTime);
        set(8, updateTime);
    }
}
