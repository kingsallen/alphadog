/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionInterviewProcessRelRecord;

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
 * 职位和面试流程中间关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionInterviewProcessRel extends TableImpl<JobPositionInterviewProcessRelRecord> {

    private static final long serialVersionUID = 2058087673;

    /**
     * The reference instance of <code>jobdb.job_position_interview_process_rel</code>
     */
    public static final JobPositionInterviewProcessRel JOB_POSITION_INTERVIEW_PROCESS_REL = new JobPositionInterviewProcessRel();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobPositionInterviewProcessRelRecord> getRecordType() {
        return JobPositionInterviewProcessRelRecord.class;
    }

    /**
     * The column <code>jobdb.job_position_interview_process_rel.id</code>. 序列ID
     */
    public final TableField<JobPositionInterviewProcessRelRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "序列ID");

    /**
     * The column <code>jobdb.job_position_interview_process_rel.position_id</code>. 职位ID
     */
    public final TableField<JobPositionInterviewProcessRelRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职位ID");

    /**
     * The column <code>jobdb.job_position_interview_process_rel.interview_process_id</code>. 面试流程ID
     */
    public final TableField<JobPositionInterviewProcessRelRecord, Integer> INTERVIEW_PROCESS_ID = createField("interview_process_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "面试流程ID");

    /**
     * The column <code>jobdb.job_position_interview_process_rel.disabled</code>. 状态 0 有效  1 无效
     */
    public final TableField<JobPositionInterviewProcessRelRecord, Integer> DISABLED = createField("disabled", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态 0 有效  1 无效");

    /**
     * The column <code>jobdb.job_position_interview_process_rel.create_time</code>. 创建时间
     */
    public final TableField<JobPositionInterviewProcessRelRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>jobdb.job_position_interview_process_rel.update_time</code>. 更新时间
     */
    public final TableField<JobPositionInterviewProcessRelRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>jobdb.job_position_interview_process_rel</code> table reference
     */
    public JobPositionInterviewProcessRel() {
        this("job_position_interview_process_rel", null);
    }

    /**
     * Create an aliased <code>jobdb.job_position_interview_process_rel</code> table reference
     */
    public JobPositionInterviewProcessRel(String alias) {
        this(alias, JOB_POSITION_INTERVIEW_PROCESS_REL);
    }

    private JobPositionInterviewProcessRel(String alias, Table<JobPositionInterviewProcessRelRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobPositionInterviewProcessRel(String alias, Table<JobPositionInterviewProcessRelRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "职位和面试流程中间关系表");
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
    public Identity<JobPositionInterviewProcessRelRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_POSITION_INTERVIEW_PROCESS_REL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobPositionInterviewProcessRelRecord> getPrimaryKey() {
        return Keys.KEY_JOB_POSITION_INTERVIEW_PROCESS_REL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobPositionInterviewProcessRelRecord>> getKeys() {
        return Arrays.<UniqueKey<JobPositionInterviewProcessRelRecord>>asList(Keys.KEY_JOB_POSITION_INTERVIEW_PROCESS_REL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionInterviewProcessRel as(String alias) {
        return new JobPositionInterviewProcessRel(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JobPositionInterviewProcessRel rename(String name) {
        return new JobPositionInterviewProcessRel(name, null);
    }
}
