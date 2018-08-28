/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogScraperRecordRecord;

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
 * c端导入日志明细表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogScraperRecord extends TableImpl<LogScraperRecordRecord> {

    private static final long serialVersionUID = -1277840880;

    /**
     * The reference instance of <code>logdb.log_scraper_record</code>
     */
    public static final LogScraperRecord LOG_SCRAPER_RECORD = new LogScraperRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogScraperRecordRecord> getRecordType() {
        return LogScraperRecordRecord.class;
    }

    /**
     * The column <code>logdb.log_scraper_record.id</code>.
     */
    public final TableField<LogScraperRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_scraper_record.channel</code>. 1   51job， 2 liepin， 3 zhaopin，4 linkedin
     */
    public final TableField<LogScraperRecordRecord, Integer> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "1   51job， 2 liepin， 3 zhaopin，4 linkedin");

    /**
     * The column <code>logdb.log_scraper_record.request</code>.
     */
    public final TableField<LogScraperRecordRecord, String> REQUEST = createField("request", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false), this, "");

    /**
     * The column <code>logdb.log_scraper_record.status</code>. 导入最终状态 0成功 1密码错 2验证码错 4 解析错误 5没有简历 100新建待处理
     */
    public final TableField<LogScraperRecordRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("100", org.jooq.impl.SQLDataType.TINYINT)), this, "导入最终状态 0成功 1密码错 2验证码错 4 解析错误 5没有简历 100新建待处理");

    /**
     * The column <code>logdb.log_scraper_record.create_time</code>.
     */
    public final TableField<LogScraperRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>logdb.log_scraper_record.fetched_resume</code>. 从第三方获取到的原始简历html
     */
    public final TableField<LogScraperRecordRecord, String> FETCHED_RESUME = createField("fetched_resume", org.jooq.impl.SQLDataType.CLOB, this, "从第三方获取到的原始简历html");

    /**
     * The column <code>logdb.log_scraper_record.parsed_resume</code>. 通过parser解析后的格式json
     */
    public final TableField<LogScraperRecordRecord, String> PARSED_RESUME = createField("parsed_resume", org.jooq.impl.SQLDataType.CLOB, this, "通过parser解析后的格式json");

    /**
     * The column <code>logdb.log_scraper_record.update_time</code>.
     */
    public final TableField<LogScraperRecordRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>logdb.log_scraper_record</code> table reference
     */
    public LogScraperRecord() {
        this("log_scraper_record", null);
    }

    /**
     * Create an aliased <code>logdb.log_scraper_record</code> table reference
     */
    public LogScraperRecord(String alias) {
        this(alias, LOG_SCRAPER_RECORD);
    }

    private LogScraperRecord(String alias, Table<LogScraperRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogScraperRecord(String alias, Table<LogScraperRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "c端导入日志明细表");
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
    public Identity<LogScraperRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_SCRAPER_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogScraperRecordRecord> getPrimaryKey() {
        return Keys.KEY_LOG_SCRAPER_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogScraperRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<LogScraperRecordRecord>>asList(Keys.KEY_LOG_SCRAPER_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogScraperRecord as(String alias) {
        return new LogScraperRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogScraperRecord rename(String name) {
        return new LogScraperRecord(name, null);
    }
}
