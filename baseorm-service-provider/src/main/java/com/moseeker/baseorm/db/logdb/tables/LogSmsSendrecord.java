/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;

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
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogSmsSendrecord extends TableImpl<LogSmsSendrecordRecord> {

    private static final long serialVersionUID = -566538007;

    /**
     * The reference instance of <code>logdb.log_sms_sendrecord</code>
     */
    public static final LogSmsSendrecord LOG_SMS_SENDRECORD = new LogSmsSendrecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogSmsSendrecordRecord> getRecordType() {
        return LogSmsSendrecordRecord.class;
    }

    /**
     * The column <code>logdb.log_sms_sendrecord.id</code>.
     */
    public final TableField<LogSmsSendrecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_sms_sendrecord.country_code</code>. 国家代码
     */
    public final TableField<LogSmsSendrecordRecord, String> COUNTRY_CODE = createField("country_code", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false).defaultValue(org.jooq.impl.DSL.inline("86", org.jooq.impl.SQLDataType.VARCHAR)), this, "国家代码");

    /**
     * The column <code>logdb.log_sms_sendrecord.sys</code>. 来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script
     */
    public final TableField<LogSmsSendrecordRecord, Byte> SYS = createField("sys", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script");

    /**
     * The column <code>logdb.log_sms_sendrecord.mobile</code>.
     */
    public final TableField<LogSmsSendrecordRecord, Long> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>logdb.log_sms_sendrecord.msg</code>. 发送内容
     */
    public final TableField<LogSmsSendrecordRecord, String> MSG = createField("msg", org.jooq.impl.SQLDataType.CLOB, this, "发送内容");

    /**
     * The column <code>logdb.log_sms_sendrecord.ip</code>. IP
     */
    public final TableField<LogSmsSendrecordRecord, String> IP = createField("ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "IP");

    /**
     * The column <code>logdb.log_sms_sendrecord.create_time</code>.
     */
    public final TableField<LogSmsSendrecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>logdb.log_sms_sendrecord</code> table reference
     */
    public LogSmsSendrecord() {
        this("log_sms_sendrecord", null);
    }

    /**
     * Create an aliased <code>logdb.log_sms_sendrecord</code> table reference
     */
    public LogSmsSendrecord(String alias) {
        this(alias, LOG_SMS_SENDRECORD);
    }

    private LogSmsSendrecord(String alias, Table<LogSmsSendrecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogSmsSendrecord(String alias, Table<LogSmsSendrecordRecord> aliased, Field<?>[] parameters) {
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
    public Identity<LogSmsSendrecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_SMS_SENDRECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogSmsSendrecordRecord> getPrimaryKey() {
        return Keys.KEY_LOG_SMS_SENDRECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogSmsSendrecordRecord>> getKeys() {
        return Arrays.<UniqueKey<LogSmsSendrecordRecord>>asList(Keys.KEY_LOG_SMS_SENDRECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogSmsSendrecord as(String alias) {
        return new LogSmsSendrecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogSmsSendrecord rename(String name) {
        return new LogSmsSendrecord(name, null);
    }
}
