/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables;


import com.moseeker.baseorm.db.logdb.Keys;
import com.moseeker.baseorm.db.logdb.Logdb;
import com.moseeker.baseorm.db.logdb.tables.records.LogMeetmobotRecomRecord;

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
 * 记录meetmobot的推荐日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogMeetmobotRecom extends TableImpl<LogMeetmobotRecomRecord> {

    private static final long serialVersionUID = 546771420;

    /**
     * The reference instance of <code>logdb.log_meetmobot_recom</code>
     */
    public static final LogMeetmobotRecom LOG_MEETMOBOT_RECOM = new LogMeetmobotRecom();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LogMeetmobotRecomRecord> getRecordType() {
        return LogMeetmobotRecomRecord.class;
    }

    /**
     * The column <code>logdb.log_meetmobot_recom.id</code>.
     */
    public final TableField<LogMeetmobotRecomRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>logdb.log_meetmobot_recom.recom_params</code>. 推荐的查询条件
     */
    public final TableField<LogMeetmobotRecomRecord, String> RECOM_PARAMS = createField("recom_params", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐的查询条件");

    /**
     * The column <code>logdb.log_meetmobot_recom.recom_code</code>. 推荐生成的唯一标识符
     */
    public final TableField<LogMeetmobotRecomRecord, String> RECOM_CODE = createField("recom_code", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐生成的唯一标识符");

    /**
     * The column <code>logdb.log_meetmobot_recom.algorithm_name</code>. 推荐算法
     */
    public final TableField<LogMeetmobotRecomRecord, String> ALGORITHM_NAME = createField("algorithm_name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐算法");

    /**
     * The column <code>logdb.log_meetmobot_recom.pids</code>. 推荐获得的职位id，逗号隔开
     */
    public final TableField<LogMeetmobotRecomRecord, String> PIDS = createField("pids", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐获得的职位id，逗号隔开");

    /**
     * The column <code>logdb.log_meetmobot_recom.create_time</code>. 推荐记录生成的时间
     */
    public final TableField<LogMeetmobotRecomRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "推荐记录生成的时间");

    /**
     * Create a <code>logdb.log_meetmobot_recom</code> table reference
     */
    public LogMeetmobotRecom() {
        this("log_meetmobot_recom", null);
    }

    /**
     * Create an aliased <code>logdb.log_meetmobot_recom</code> table reference
     */
    public LogMeetmobotRecom(String alias) {
        this(alias, LOG_MEETMOBOT_RECOM);
    }

    private LogMeetmobotRecom(String alias, Table<LogMeetmobotRecomRecord> aliased) {
        this(alias, aliased, null);
    }

    private LogMeetmobotRecom(String alias, Table<LogMeetmobotRecomRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "记录meetmobot的推荐日志");
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
    public Identity<LogMeetmobotRecomRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOG_MEETMOBOT_RECOM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LogMeetmobotRecomRecord> getPrimaryKey() {
        return Keys.KEY_LOG_MEETMOBOT_RECOM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LogMeetmobotRecomRecord>> getKeys() {
        return Arrays.<UniqueKey<LogMeetmobotRecomRecord>>asList(Keys.KEY_LOG_MEETMOBOT_RECOM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogMeetmobotRecom as(String alias) {
        return new LogMeetmobotRecom(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LogMeetmobotRecom rename(String name) {
        return new LogMeetmobotRecom(name, null);
    }
}
