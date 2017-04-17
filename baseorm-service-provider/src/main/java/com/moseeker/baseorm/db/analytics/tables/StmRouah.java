/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StmRouahRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmRouah extends TableImpl<StmRouahRecord> {

    private static final long serialVersionUID = 1829905859;

    /**
     * The reference instance of <code>analytics.stm_rouah</code>
     */
    public static final StmRouah STM_ROUAH = new StmRouah();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StmRouahRecord> getRecordType() {
        return StmRouahRecord.class;
    }

    /**
     * The column <code>analytics.stm_rouah.id</code>.
     */
    public final TableField<StmRouahRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.stm_rouah.create_time</code>.
     */
    public final TableField<StmRouahRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>analytics.stm_rouah.oauth_source</code>.
     */
    public final TableField<StmRouahRecord, Integer> OAUTH_SOURCE = createField("oauth_source", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.stm_rouah.pid</code>.
     */
    public final TableField<StmRouahRecord, Integer> PID = createField("pid", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.stm_rouah.referer</code>.
     */
    public final TableField<StmRouahRecord, String> REFERER = createField("referer", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>analytics.stm_rouah</code> table reference
     */
    public StmRouah() {
        this("stm_rouah", null);
    }

    /**
     * Create an aliased <code>analytics.stm_rouah</code> table reference
     */
    public StmRouah(String alias) {
        this(alias, STM_ROUAH);
    }

    private StmRouah(String alias, Table<StmRouahRecord> aliased) {
        this(alias, aliased, null);
    }

    private StmRouah(String alias, Table<StmRouahRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
    public UniqueKey<StmRouahRecord> getPrimaryKey() {
        return Keys.KEY_STM_ROUAH_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StmRouahRecord>> getKeys() {
        return Arrays.<UniqueKey<StmRouahRecord>>asList(Keys.KEY_STM_ROUAH_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmRouah as(String alias) {
        return new StmRouah(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StmRouah rename(String name) {
        return new StmRouah(name, null);
    }
}
