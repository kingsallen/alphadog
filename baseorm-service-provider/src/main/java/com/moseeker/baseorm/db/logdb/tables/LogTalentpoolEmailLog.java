/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailLogRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 邮件日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogTalentpoolEmailLog extends TableImpl<LogTalentpoolEmailLogRecord> {

    private static final long serialVersionUID = -14847576;

    /**
     * The reference instance of <code>logdb.log_talentpool_email_log</code>
     */
    public static final LogTalentpoolEmailLog LOG_TALENTPOOL_EMAIL_LOG = new LogTalentpoolEmailLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogTalentpoolEmailLogRecord> getRecordType() {
        return LogTalentpoolEmailLogRecord.class;
    }

    /**
     * The column <code>logdb.log_talentpool_email_log.id</code>.
     */
    public final TableField<LogTalentpoolEmailLogRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_talentpool_email_log.company_id</code>. 公司id
     */
    public final TableField<LogTalentpoolEmailLogRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "公司id");

    /**
     * The column <code>logdb.log_talentpool_email_log.type</code>. 邮件类别 1:投递成功邮件 2：不匹配通知邮件 3：生日祝福邮件 4：邀请投递邮件
5：转发求职者简历邮件 0充值
     */
    public final TableField<LogTalentpoolEmailLogRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "邮件类别 1:投递成功邮件 2：不匹配通知邮件 3：生日祝福邮件 4：邀请投递邮件\n5：转发求职者简历邮件 0充值");

    /**
     * The column <code>logdb.log_talentpool_email_log.hr_id</code>. hr_id
     */
    public final TableField<LogTalentpoolEmailLogRecord, Integer> HR_ID = createField("hr_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_id");

    /**
     * The column <code>logdb.log_talentpool_email_log.balance</code>. 操作之后邮件剩余额度
     */
    public final TableField<LogTalentpoolEmailLogRecord, Integer> BALANCE = createField("balance", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "操作之后邮件剩余额度");

    /**
     * The column <code>logdb.log_talentpool_email_log.lost</code>. 本次使用点数或者充值点数
     */
    public final TableField<LogTalentpoolEmailLogRecord, Integer> LOST = createField("lost", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "本次使用点数或者充值点数");

    /**
     * The column <code>logdb.log_talentpool_email_log.create_time</code>. 创建时间
     */
    public final TableField<LogTalentpoolEmailLogRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * Create a <code>logdb.log_talentpool_email_log</code> table reference
     */
    public LogTalentpoolEmailLog() {
        this("log_talentpool_email_log", null);
    }

    /**
     * Create an aliased <code>logdb.log_talentpool_email_log</code> table reference
     */
    public LogTalentpoolEmailLog(String alias) {
        this(alias, LOG_TALENTPOOL_EMAIL_LOG);
    }

    private LogTalentpoolEmailLog(String alias, Table<LogTalentpoolEmailLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogTalentpoolEmailLog(String alias, Table<LogTalentpoolEmailLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "邮件日志");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Logdb.LOGDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<LogTalentpoolEmailLogRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_TALENTPOOL_EMAIL_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogTalentpoolEmailLogRecord> getPrimaryKey() {
        return Keys.KEY_LOG_TALENTPOOL_EMAIL_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogTalentpoolEmailLogRecord>> getKeys() {
        return Arrays.<UniqueKey<LogTalentpoolEmailLogRecord>>asList(Keys.KEY_LOG_TALENTPOOL_EMAIL_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogTalentpoolEmailLog as(String alias) {
        return new LogTalentpoolEmailLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogTalentpoolEmailLog rename(String name) {
        return new LogTalentpoolEmailLog(name, null);
    }
}
