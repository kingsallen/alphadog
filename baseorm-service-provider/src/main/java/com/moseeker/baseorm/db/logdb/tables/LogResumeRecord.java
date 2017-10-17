/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogResumeRecord extends TableImpl<LogResumeRecordRecord> {

    private static final long serialVersionUID = 1863233138;

    /**
     * The reference instance of <code>logdb.log_resume_record</code>
     */
    public static final LogResumeRecord LOG_RESUME_RECORD = new LogResumeRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogResumeRecordRecord> getRecordType() {
        return LogResumeRecordRecord.class;
    }

    /**
     * The column <code>logdb.log_resume_record.id</code>.
     */
    public final TableField<LogResumeRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_resume_record.user_id</code>.
     */
    public final TableField<LogResumeRecordRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>logdb.log_resume_record.file_name</code>.
     */
    public final TableField<LogResumeRecordRecord, String> FILE_NAME = createField("file_name", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "");

    /**
     * The column <code>logdb.log_resume_record.error_log</code>.
     */
    public final TableField<LogResumeRecordRecord, String> ERROR_LOG = createField("error_log", org.jooq.impl.SQLDataType.VARCHAR.length(200).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>logdb.log_resume_record.field_value</code>.
     */
    public final TableField<LogResumeRecordRecord, String> FIELD_VALUE = createField("field_value", org.jooq.impl.SQLDataType.VARCHAR.length(2000).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>logdb.log_resume_record.result_data</code>.
     */
    public final TableField<LogResumeRecordRecord, String> RESULT_DATA = createField("result_data", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>logdb.log_resume_record.create_time</code>.
     */
    public final TableField<LogResumeRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>logdb.log_resume_record</code> table reference
     */
    public LogResumeRecord() {
        this("log_resume_record", null);
    }

    /**
     * Create an aliased <code>logdb.log_resume_record</code> table reference
     */
    public LogResumeRecord(String alias) {
        this(alias, LOG_RESUME_RECORD);
    }

    private LogResumeRecord(String alias, Table<LogResumeRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogResumeRecord(String alias, Table<LogResumeRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
    public Identity<LogResumeRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_RESUME_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogResumeRecordRecord> getPrimaryKey() {
        return Keys.KEY_LOG_RESUME_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogResumeRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<LogResumeRecordRecord>>asList(Keys.KEY_LOG_RESUME_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogResumeRecord as(String alias) {
        return new LogResumeRecord(alias, this);
    }

    /**
     * Rename this table
     */
    public LogResumeRecord rename(String name) {
        return new LogResumeRecord(name, null);
    }
}
