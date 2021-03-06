/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StmEventTypeRecord;

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
 * 请求事件类型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmEventType extends TableImpl<StmEventTypeRecord> {

    private static final long serialVersionUID = 1023123855;

    /**
     * The reference instance of <code>analytics.stm_event_type</code>
     */
    public static final StmEventType STM_EVENT_TYPE = new StmEventType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StmEventTypeRecord> getRecordType() {
        return StmEventTypeRecord.class;
    }

    /**
     * The column <code>analytics.stm_event_type.id</code>. 主键
     */
    public final TableField<StmEventTypeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主键");

    /**
     * The column <code>analytics.stm_event_type.event</code>. 事件类型
     */
    public final TableField<StmEventTypeRecord, String> EVENT = createField("event", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "事件类型");

    /**
     * Create a <code>analytics.stm_event_type</code> table reference
     */
    public StmEventType() {
        this("stm_event_type", null);
    }

    /**
     * Create an aliased <code>analytics.stm_event_type</code> table reference
     */
    public StmEventType(String alias) {
        this(alias, STM_EVENT_TYPE);
    }

    private StmEventType(String alias, Table<StmEventTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private StmEventType(String alias, Table<StmEventTypeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "请求事件类型表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Analytics.ANALYTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StmEventTypeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_STM_EVENT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StmEventTypeRecord> getPrimaryKey() {
        return Keys.KEY_STM_EVENT_TYPE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StmEventTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<StmEventTypeRecord>>asList(Keys.KEY_STM_EVENT_TYPE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmEventType as(String alias) {
        return new StmEventType(alias, this);
    }

    /**
     * Rename this table
     */
    public StmEventType rename(String name) {
        return new StmEventType(name, null);
    }
}
