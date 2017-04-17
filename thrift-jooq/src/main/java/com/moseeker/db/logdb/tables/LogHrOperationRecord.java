/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.logdb.tables;


import com.moseeker.db.logdb.Keys;
import com.moseeker.db.logdb.Logdb;
import com.moseeker.db.logdb.tables.records.LogHrOperationRecordRecord;

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
 * HR操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogHrOperationRecord extends TableImpl<LogHrOperationRecordRecord> {

    private static final long serialVersionUID = 534229436;

    /**
     * The reference instance of <code>logdb.log_hr_operation_record</code>
     */
    public static final LogHrOperationRecord LOG_HR_OPERATION_RECORD = new LogHrOperationRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogHrOperationRecordRecord> getRecordType() {
        return LogHrOperationRecordRecord.class;
    }

    /**
     * The column <code>logdb.log_hr_operation_record.id</code>.
     */
    public final TableField<LogHrOperationRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_hr_operation_record.type</code>. 0:无效1：hr操作职位发布人
     */
    public final TableField<LogHrOperationRecordRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0:无效1：hr操作职位发布人");

    /**
     * The column <code>logdb.log_hr_operation_record.hraccount_id</code>. user_hr_account.id
     */
    public final TableField<LogHrOperationRecordRecord, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "user_hr_account.id");

    /**
     * The column <code>logdb.log_hr_operation_record.description</code>. 记录描述
     */
    public final TableField<LogHrOperationRecordRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "记录描述");

    /**
     * The column <code>logdb.log_hr_operation_record.create_time</code>.
     */
    public final TableField<LogHrOperationRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>logdb.log_hr_operation_record</code> table reference
     */
    public LogHrOperationRecord() {
        this("log_hr_operation_record", null);
    }

    /**
     * Create an aliased <code>logdb.log_hr_operation_record</code> table reference
     */
    public LogHrOperationRecord(String alias) {
        this(alias, LOG_HR_OPERATION_RECORD);
    }

    private LogHrOperationRecord(String alias, Table<LogHrOperationRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogHrOperationRecord(String alias, Table<LogHrOperationRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "HR操作记录表");
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
    public Identity<LogHrOperationRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_HR_OPERATION_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogHrOperationRecordRecord> getPrimaryKey() {
        return Keys.KEY_LOG_HR_OPERATION_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogHrOperationRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<LogHrOperationRecordRecord>>asList(Keys.KEY_LOG_HR_OPERATION_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogHrOperationRecord as(String alias) {
        return new LogHrOperationRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogHrOperationRecord rename(String name) {
        return new LogHrOperationRecord(name, null);
    }
}
