/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogHrloginRecordRecord;

import java.sql.Date;
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
 * hr每日登陆/使用统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogHrloginRecord extends TableImpl<LogHrloginRecordRecord> {

    private static final long serialVersionUID = 396877750;

    /**
     * The reference instance of <code>logdb.log_hrlogin_record</code>
     */
    public static final LogHrloginRecord LOG_HRLOGIN_RECORD = new LogHrloginRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogHrloginRecordRecord> getRecordType() {
        return LogHrloginRecordRecord.class;
    }

    /**
     * The column <code>logdb.log_hrlogin_record.id</code>.
     */
    public final TableField<LogHrloginRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_hrlogin_record.hr_id</code>.
     */
    public final TableField<LogHrloginRecordRecord, Integer> HR_ID = createField("hr_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_hrlogin_record.login_date</code>. 使用日期
     */
    public final TableField<LogHrloginRecordRecord, Date> LOGIN_DATE = createField("login_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "使用日期");

    /**
     * The column <code>logdb.log_hrlogin_record.create_time</code>.
     */
    public final TableField<LogHrloginRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>logdb.log_hrlogin_record.update_time</code>.
     */
    public final TableField<LogHrloginRecordRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>logdb.log_hrlogin_record</code> table reference
     */
    public LogHrloginRecord() {
        this("log_hrlogin_record", null);
    }

    /**
     * Create an aliased <code>logdb.log_hrlogin_record</code> table reference
     */
    public LogHrloginRecord(String alias) {
        this(alias, LOG_HRLOGIN_RECORD);
    }

    private LogHrloginRecord(String alias, Table<LogHrloginRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogHrloginRecord(String alias, Table<LogHrloginRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "hr每日登陆/使用统计表");
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
    public Identity<LogHrloginRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_HRLOGIN_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogHrloginRecordRecord> getPrimaryKey() {
        return Keys.KEY_LOG_HRLOGIN_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogHrloginRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<LogHrloginRecordRecord>>asList(Keys.KEY_LOG_HRLOGIN_RECORD_PRIMARY, Keys.KEY_LOG_HRLOGIN_RECORD_HR_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogHrloginRecord as(String alias) {
        return new LogHrloginRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogHrloginRecord rename(String name) {
        return new LogHrloginRecord(name, null);
    }
}
