/*
 * This file is generated by jOOQ.
*/
package com.moseeker.consistencysuport.producer.db.consistencydb.tables;


import com.moseeker.consistencysuport.producer.db.consistencydb.Consistencydb;
import com.moseeker.consistencysuport.producer.db.consistencydb.Keys;
import com.moseeker.consistencysuport.producer.db.consistencydb.tables.records.ConsistencyMessageRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 消息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConsistencyMessage extends TableImpl<ConsistencyMessageRecord> {

    private static final long serialVersionUID = 479973584;

    /**
     * The reference instance of <code>consistencydb.consistency_message</code>
     */
    public static final ConsistencyMessage CONSISTENCY_MESSAGE = new ConsistencyMessage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConsistencyMessageRecord> getRecordType() {
        return ConsistencyMessageRecord.class;
    }

    /**
     * The column <code>consistencydb.consistency_message.message_id</code>. 消息编号
     */
    public final TableField<ConsistencyMessageRecord, String> MESSAGE_ID = createField("message_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "消息编号");

    /**
     * The column <code>consistencydb.consistency_message.name</code>. 业务名称
     */
    public final TableField<ConsistencyMessageRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "业务名称");

    /**
     * The column <code>consistencydb.consistency_message.create_time</code>. 创建时间
     */
    public final TableField<ConsistencyMessageRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>consistencydb.consistency_message.update_time</code>. 更新时间
     */
    public final TableField<ConsistencyMessageRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>consistencydb.consistency_message.version</code>. 版本
     */
    public final TableField<ConsistencyMessageRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "版本");

    /**
     * The column <code>consistencydb.consistency_message.retried</code>. 已经重试的次数
     */
    public final TableField<ConsistencyMessageRecord, Byte> RETRIED = createField("retried", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "已经重试的次数");

    /**
     * The column <code>consistencydb.consistency_message.last_retry_time</code>. 最后重试的时间
     */
    public final TableField<ConsistencyMessageRecord, Timestamp> LAST_RETRY_TIME = createField("last_retry_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后重试的时间");

    /**
     * The column <code>consistencydb.consistency_message.param</code>. 参数
     */
    public final TableField<ConsistencyMessageRecord, String> PARAM = createField("param", org.jooq.impl.SQLDataType.VARCHAR.length(3000), this, "参数");

    /**
     * The column <code>consistencydb.consistency_message.finish</code>. 是否完成, 0 未完成， 1 完成
     */
    public final TableField<ConsistencyMessageRecord, Byte> FINISH = createField("finish", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否完成, 0 未完成， 1 完成");

    /**
     * Create a <code>consistencydb.consistency_message</code> table reference
     */
    public ConsistencyMessage() {
        this("consistency_message", null);
    }

    /**
     * Create an aliased <code>consistencydb.consistency_message</code> table reference
     */
    public ConsistencyMessage(String alias) {
        this(alias, CONSISTENCY_MESSAGE);
    }

    private ConsistencyMessage(String alias, Table<ConsistencyMessageRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConsistencyMessage(String alias, Table<ConsistencyMessageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "消息表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Consistencydb.CONSISTENCYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConsistencyMessageRecord> getPrimaryKey() {
        return Keys.KEY_CONSISTENCY_MESSAGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConsistencyMessageRecord>> getKeys() {
        return Arrays.<UniqueKey<ConsistencyMessageRecord>>asList(Keys.KEY_CONSISTENCY_MESSAGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConsistencyMessage as(String alias) {
        return new ConsistencyMessage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConsistencyMessage rename(String name) {
        return new ConsistencyMessage(name, null);
    }
}
