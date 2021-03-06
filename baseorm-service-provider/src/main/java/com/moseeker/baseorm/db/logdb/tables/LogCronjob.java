/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogCronjobRecord;

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
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogCronjob extends TableImpl<LogCronjobRecord> {

    private static final long serialVersionUID = -1609069201;

    /**
     * The reference instance of <code>logdb.log_cronjob</code>
     */
    public static final LogCronjob LOG_CRONJOB = new LogCronjob();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogCronjobRecord> getRecordType() {
        return LogCronjobRecord.class;
    }

    /**
     * The column <code>logdb.log_cronjob.id</code>.
     */
    public final TableField<LogCronjobRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_cronjob.cronjob_id</code>.
     */
    public final TableField<LogCronjobRecord, Integer> CRONJOB_ID = createField("cronjob_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_cronjob.start</code>. 开始时间
     */
    public final TableField<LogCronjobRecord, Timestamp> START = createField("start", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "开始时间");

    /**
     * The column <code>logdb.log_cronjob.end</code>. 结束时间
     */
    public final TableField<LogCronjobRecord, Timestamp> END = createField("end", org.jooq.impl.SQLDataType.TIMESTAMP, this, "结束时间");

    /**
     * The column <code>logdb.log_cronjob.result</code>. 运行结果 1 失败, 0 成功
     */
    public final TableField<LogCronjobRecord, Integer> RESULT = createField("result", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "运行结果 1 失败, 0 成功");

    /**
     * Create a <code>logdb.log_cronjob</code> table reference
     */
    public LogCronjob() {
        this("log_cronjob", null);
    }

    /**
     * Create an aliased <code>logdb.log_cronjob</code> table reference
     */
    public LogCronjob(String alias) {
        this(alias, LOG_CRONJOB);
    }

    private LogCronjob(String alias, Table<LogCronjobRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogCronjob(String alias, Table<LogCronjobRecord> aliased, Field<?>[] parameters) {
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
    public Identity<LogCronjobRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_CRONJOB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogCronjobRecord> getPrimaryKey() {
        return Keys.KEY_LOG_CRONJOB_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogCronjobRecord>> getKeys() {
        return Arrays.<UniqueKey<LogCronjobRecord>>asList(Keys.KEY_LOG_CRONJOB_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogCronjob as(String alias) {
        return new LogCronjob(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogCronjob rename(String name) {
        return new LogCronjob(name, null);
    }
}
