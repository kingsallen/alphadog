/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.tables.records.StmPvPidRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class StmPvPid extends TableImpl<StmPvPidRecord> {

    private static final long serialVersionUID = 1379016284;

    /**
     * The reference instance of <code>analytics.stm_pv_pid</code>
     */
    public static final StmPvPid STM_PV_PID = new StmPvPid();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StmPvPidRecord> getRecordType() {
        return StmPvPidRecord.class;
    }

    /**
     * The column <code>analytics.stm_pv_pid.id</code>.
     */
    public final TableField<StmPvPidRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>analytics.stm_pv_pid.ab_group</code>.
     */
    public final TableField<StmPvPidRecord, String> AB_GROUP = createField("ab_group", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>analytics.stm_pv_pid.create_time</code>.
     */
    public final TableField<StmPvPidRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>analytics.stm_pv_pid.ip</code>.
     */
    public final TableField<StmPvPidRecord, String> IP = createField("ip", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>analytics.stm_pv_pid.pid</code>.
     */
    public final TableField<StmPvPidRecord, String> PID = createField("pid", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>analytics.stm_pv_pid</code> table reference
     */
    public StmPvPid() {
        this("stm_pv_pid", null);
    }

    /**
     * Create an aliased <code>analytics.stm_pv_pid</code> table reference
     */
    public StmPvPid(String alias) {
        this(alias, STM_PV_PID);
    }

    private StmPvPid(String alias, Table<StmPvPidRecord> aliased) {
        this(alias, aliased, null);
    }

    private StmPvPid(String alias, Table<StmPvPidRecord> aliased, Field<?>[] parameters) {
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
    public StmPvPid as(String alias) {
        return new StmPvPid(alias, this);
    }

    /**
     * Rename this table
     */
    public StmPvPid rename(String name) {
        return new StmPvPid(name, null);
    }
}
