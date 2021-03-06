/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;

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
 * 自定义简历副本记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobResumeOther extends TableImpl<JobResumeOtherRecord> {

    private static final long serialVersionUID = 1699888482;

    /**
     * The reference instance of <code>jobdb.job_resume_other</code>
     */
    public static final JobResumeOther JOB_RESUME_OTHER = new JobResumeOther();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobResumeOtherRecord> getRecordType() {
        return JobResumeOtherRecord.class;
    }

    /**
     * The column <code>jobdb.job_resume_other.app_id</code>. job_application.id
     */
    public final TableField<JobResumeOtherRecord, Integer> APP_ID = createField("app_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "job_application.id");

    /**
     * The column <code>jobdb.job_resume_other.other</code>. 自定义字段
     */
    public final TableField<JobResumeOtherRecord, String> OTHER = createField("other", org.jooq.impl.SQLDataType.CLOB, this, "自定义字段");

    /**
     * The column <code>jobdb.job_resume_other.create_time</code>. 创建时间
     */
    public final TableField<JobResumeOtherRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>jobdb.job_resume_other.update_time</code>. 更新时间
     */
    public final TableField<JobResumeOtherRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>jobdb.job_resume_other</code> table reference
     */
    public JobResumeOther() {
        this("job_resume_other", null);
    }

    /**
     * Create an aliased <code>jobdb.job_resume_other</code> table reference
     */
    public JobResumeOther(String alias) {
        this(alias, JOB_RESUME_OTHER);
    }

    private JobResumeOther(String alias, Table<JobResumeOtherRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobResumeOther(String alias, Table<JobResumeOtherRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "自定义简历副本记录表");
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
    public UniqueKey<JobResumeOtherRecord> getPrimaryKey() {
        return Keys.KEY_JOB_RESUME_OTHER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobResumeOtherRecord>> getKeys() {
        return Arrays.<UniqueKey<JobResumeOtherRecord>>asList(Keys.KEY_JOB_RESUME_OTHER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobResumeOther as(String alias) {
        return new JobResumeOther(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JobResumeOther rename(String name) {
        return new JobResumeOther(name, null);
    }
}
