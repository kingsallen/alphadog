/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryHrOperationRecordRecord;

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
 * 15年的一批hr申请状态操作记录老数据，在进行数据处理时，其中一些数据目前无法处理，暂时移到历史表中，这些数据中包括申请状态是hr操作记录包括1的，
 * 
 * hr申请记录无法梳理出合理的流程的记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryHrOperationRecord extends TableImpl<HistoryHrOperationRecordRecord> {

    private static final long serialVersionUID = -760207089;

    /**
     * The reference instance of <code>historydb.history_hr_operation_record</code>
     */
    public static final HistoryHrOperationRecord HISTORY_HR_OPERATION_RECORD = new HistoryHrOperationRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryHrOperationRecordRecord> getRecordType() {
        return HistoryHrOperationRecordRecord.class;
    }

    /**
     * The column <code>historydb.history_hr_operation_record.id</code>.
     */
    public final TableField<HistoryHrOperationRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_hr_operation_record.admin_id</code>. hr_account.id
     */
    public final TableField<HistoryHrOperationRecordRecord, Long> ADMIN_ID = createField("admin_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "hr_account.id");

    /**
     * The column <code>historydb.history_hr_operation_record.company_id</code>. company.id
     */
    public final TableField<HistoryHrOperationRecordRecord, Long> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "company.id");

    /**
     * The column <code>historydb.history_hr_operation_record.app_id</code>. job_application.id
     */
    public final TableField<HistoryHrOperationRecordRecord, Long> APP_ID = createField("app_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "job_application.id");

    /**
     * The column <code>historydb.history_hr_operation_record.status_id</code>. hr_points_conf.id
     */
    public final TableField<HistoryHrOperationRecordRecord, Long> STATUS_ID = createField("status_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "hr_points_conf.id");

    /**
     * The column <code>historydb.history_hr_operation_record.opt_time</code>. 操作时间
     */
    public final TableField<HistoryHrOperationRecordRecord, Timestamp> OPT_TIME = createField("opt_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "操作时间");

    /**
     * The column <code>historydb.history_hr_operation_record.operate_tpl_id</code>. config_sys_points_conf_tpl.id
     */
    public final TableField<HistoryHrOperationRecordRecord, Integer> OPERATE_TPL_ID = createField("operate_tpl_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "config_sys_points_conf_tpl.id");

    /**
     * Create a <code>historydb.history_hr_operation_record</code> table reference
     */
    public HistoryHrOperationRecord() {
        this("history_hr_operation_record", null);
    }

    /**
     * Create an aliased <code>historydb.history_hr_operation_record</code> table reference
     */
    public HistoryHrOperationRecord(String alias) {
        this(alias, HISTORY_HR_OPERATION_RECORD);
    }

    private HistoryHrOperationRecord(String alias, Table<HistoryHrOperationRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryHrOperationRecord(String alias, Table<HistoryHrOperationRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "15年的一批hr申请状态操作记录老数据，在进行数据处理时，其中一些数据目前无法处理，暂时移到历史表中，这些数据中包括申请状态是hr操作记录包括1的，\r\nhr申请记录无法梳理出合理的流程的记录");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Historydb.HISTORYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HistoryHrOperationRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HISTORY_HR_OPERATION_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryHrOperationRecordRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_HR_OPERATION_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryHrOperationRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryHrOperationRecordRecord>>asList(Keys.KEY_HISTORY_HR_OPERATION_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrOperationRecord as(String alias) {
        return new HistoryHrOperationRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HistoryHrOperationRecord rename(String name) {
        return new HistoryHrOperationRecord(name, null);
    }
}