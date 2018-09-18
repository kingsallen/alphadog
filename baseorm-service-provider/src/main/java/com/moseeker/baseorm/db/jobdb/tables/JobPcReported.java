/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcReportedRecord;

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
 * 被举报职位数据表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPcReported extends TableImpl<JobPcReportedRecord> {

    private static final long serialVersionUID = -1947558679;

    /**
     * The reference instance of <code>jobdb.job_pc_reported</code>
     */
    public static final JobPcReported JOB_PC_REPORTED = new JobPcReported();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobPcReportedRecord> getRecordType() {
        return JobPcReportedRecord.class;
    }

    /**
     * The column <code>jobdb.job_pc_reported.id</code>. 自增主键
     */
    public final TableField<JobPcReportedRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "自增主键");

    /**
     * The column <code>jobdb.job_pc_reported.user_id</code>. 用户id
     */
    public final TableField<JobPcReportedRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "用户id");

    /**
     * The column <code>jobdb.job_pc_reported.position_id</code>. 职位id
     */
    public final TableField<JobPcReportedRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职位id");

    /**
     * The column <code>jobdb.job_pc_reported.type</code>. 举报原因：0：公司信息不真实， 1：职位实际已停止招聘， 2：其他
     */
    public final TableField<JobPcReportedRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "举报原因：0：公司信息不真实， 1：职位实际已停止招聘， 2：其他");

    /**
     * The column <code>jobdb.job_pc_reported.description</code>. 举报详情描述信息
     */
    public final TableField<JobPcReportedRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(400).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "举报详情描述信息");

    /**
     * The column <code>jobdb.job_pc_reported.create_time</code>. 举报时间
     */
    public final TableField<JobPcReportedRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "举报时间");

    /**
     * The column <code>jobdb.job_pc_reported.update_time</code>. 更新时间
     */
    public final TableField<JobPcReportedRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>jobdb.job_pc_reported</code> table reference
     */
    public JobPcReported() {
        this("job_pc_reported", null);
    }

    /**
     * Create an aliased <code>jobdb.job_pc_reported</code> table reference
     */
    public JobPcReported(String alias) {
        this(alias, JOB_PC_REPORTED);
    }

    private JobPcReported(String alias, Table<JobPcReportedRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobPcReported(String alias, Table<JobPcReportedRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "被举报职位数据表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Jobdb.JOBDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JobPcReportedRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_PC_REPORTED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobPcReportedRecord> getPrimaryKey() {
        return Keys.KEY_JOB_PC_REPORTED_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobPcReportedRecord>> getKeys() {
        return Arrays.<UniqueKey<JobPcReportedRecord>>asList(Keys.KEY_JOB_PC_REPORTED_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPcReported as(String alias) {
        return new JobPcReported(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JobPcReported rename(String name) {
        return new JobPcReported(name, null);
    }
}
