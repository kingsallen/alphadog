/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.records;


import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailDailyLog;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 邮件额度每天消耗日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogTalentpoolEmailDailyLogRecord extends UpdatableRecordImpl<LogTalentpoolEmailDailyLogRecord> implements Record4<Integer, Integer, Integer, Date> {

    private static final long serialVersionUID = -686315045;

    /**
     * Setter for <code>logdb.log_talentpool_email_daily_log.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_daily_log.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_daily_log.company_id</code>. 公司id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_daily_log.company_id</code>. 公司id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_daily_log.lost</code>. 本日使用点数
     */
    public void setLost(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_daily_log.lost</code>. 本日使用点数
     */
    public Integer getLost() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_daily_log.date</code>. 使用时间
     */
    public void setDate(Date value) {
        set(3, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_daily_log.date</code>. 使用时间
     */
    public Date getDate() {
        return (Date) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Date> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, Integer, Date> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.LOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field4() {
        return LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE;
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
        return getLost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value4() {
        return getDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailDailyLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailDailyLogRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailDailyLogRecord value3(Integer value) {
        setLost(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailDailyLogRecord value4(Date value) {
        setDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailDailyLogRecord values(Integer value1, Integer value2, Integer value3, Date value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LogTalentpoolEmailDailyLogRecord
     */
    public LogTalentpoolEmailDailyLogRecord() {
        super(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG);
    }

    /**
     * Create a detached, initialised LogTalentpoolEmailDailyLogRecord
     */
    public LogTalentpoolEmailDailyLogRecord(Integer id, Integer companyId, Integer lost, Date date) {
        super(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG);

        set(0, id);
        set(1, companyId);
        set(2, lost);
        set(3, date);
    }
}
