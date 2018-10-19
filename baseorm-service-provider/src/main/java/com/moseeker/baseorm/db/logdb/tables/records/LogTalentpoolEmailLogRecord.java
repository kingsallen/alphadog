/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.records;


import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 邮件日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogTalentpoolEmailLogRecord extends UpdatableRecordImpl<LogTalentpoolEmailLogRecord> implements Record7<Integer, Integer, Integer, Integer, Integer, Integer, Timestamp> {

    private static final long serialVersionUID = 349904990;

    /**
     * Setter for <code>logdb.log_talentpool_email_log.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_log.company_id</code>. 公司id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.company_id</code>. 公司id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_log.type</code>. 邮件类别 1:投递成功邮件 2：不匹配通知邮件 3：生日祝福邮件 4：邀请投递邮件
5：转发求职者简历邮件 0充值
     */
    public void setType(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.type</code>. 邮件类别 1:投递成功邮件 2：不匹配通知邮件 3：生日祝福邮件 4：邀请投递邮件
5：转发求职者简历邮件 0充值
     */
    public Integer getType() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_log.hr_id</code>. hr_id
     */
    public void setHrId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.hr_id</code>. hr_id
     */
    public Integer getHrId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_log.balance</code>. 操作之后邮件剩余额度
     */
    public void setBalance(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.balance</code>. 操作之后邮件剩余额度
     */
    public Integer getBalance() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_log.lost</code>. 本次使用点数或者充值点数
     */
    public void setLost(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.lost</code>. 本次使用点数或者充值点数
     */
    public Integer getLost() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>logdb.log_talentpool_email_log.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>logdb.log_talentpool_email_log.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
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
    public Row7<Integer, Integer, Integer, Integer, Integer, Integer, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, Integer, Integer, Integer, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.BALANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.LOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME;
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
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getBalance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getLost();
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
    public LogTalentpoolEmailLogRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord value3(Integer value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord value4(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord value5(Integer value) {
        setBalance(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord value6(Integer value) {
        setLost(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLogRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6, Timestamp value7) {
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
     * Create a detached LogTalentpoolEmailLogRecord
     */
    public LogTalentpoolEmailLogRecord() {
        super(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG);
    }

    /**
     * Create a detached, initialised LogTalentpoolEmailLogRecord
     */
    public LogTalentpoolEmailLogRecord(Integer id, Integer companyId, Integer type, Integer hrId, Integer balance, Integer lost, Timestamp createTime) {
        super(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG);

        set(0, id);
        set(1, companyId);
        set(2, type);
        set(3, hrId);
        set(4, balance);
        set(5, lost);
        set(6, createTime);
    }
}
