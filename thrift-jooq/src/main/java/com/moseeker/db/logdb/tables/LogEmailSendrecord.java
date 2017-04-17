/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.logdb.tables;


import com.moseeker.db.logdb.Keys;
import com.moseeker.db.logdb.Logdb;
import com.moseeker.db.logdb.tables.records.LogEmailSendrecordRecord;

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
 * 短信发送记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogEmailSendrecord extends TableImpl<LogEmailSendrecordRecord> {

    private static final long serialVersionUID = -886592315;

    /**
     * The reference instance of <code>logdb.log_email_sendrecord</code>
     */
    public static final LogEmailSendrecord LOG_EMAIL_SENDRECORD = new LogEmailSendrecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogEmailSendrecordRecord> getRecordType() {
        return LogEmailSendrecordRecord.class;
    }

    /**
     * The column <code>logdb.log_email_sendrecord.id</code>.
     */
    public final TableField<LogEmailSendrecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_email_sendrecord.tpl_id</code>. 邮件模板ID
     */
    public final TableField<LogEmailSendrecordRecord, Integer> TPL_ID = createField("tpl_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "邮件模板ID");

    /**
     * The column <code>logdb.log_email_sendrecord.sys</code>. 来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script
     */
    public final TableField<LogEmailSendrecordRecord, Byte> SYS = createField("sys", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script");

    /**
     * The column <code>logdb.log_email_sendrecord.email</code>. 邮箱地址
     */
    public final TableField<LogEmailSendrecordRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "邮箱地址");

    /**
     * The column <code>logdb.log_email_sendrecord.content</code>. 邮件变量部分内容以json方式
     */
    public final TableField<LogEmailSendrecordRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB, this, "邮件变量部分内容以json方式");

    /**
     * The column <code>logdb.log_email_sendrecord.create_time</code>.
     */
    public final TableField<LogEmailSendrecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>logdb.log_email_sendrecord</code> table reference
     */
    public LogEmailSendrecord() {
        this("log_email_sendrecord", null);
    }

    /**
     * Create an aliased <code>logdb.log_email_sendrecord</code> table reference
     */
    public LogEmailSendrecord(String alias) {
        this(alias, LOG_EMAIL_SENDRECORD);
    }

    private LogEmailSendrecord(String alias, Table<LogEmailSendrecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogEmailSendrecord(String alias, Table<LogEmailSendrecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "短信发送记录表");
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
    public Identity<LogEmailSendrecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_EMAIL_SENDRECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogEmailSendrecordRecord> getPrimaryKey() {
        return Keys.KEY_LOG_EMAIL_SENDRECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogEmailSendrecordRecord>> getKeys() {
        return Arrays.<UniqueKey<LogEmailSendrecordRecord>>asList(Keys.KEY_LOG_EMAIL_SENDRECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogEmailSendrecord as(String alias) {
        return new LogEmailSendrecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogEmailSendrecord rename(String name) {
        return new LogEmailSendrecord(name, null);
    }
}
