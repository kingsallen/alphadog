/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationCommunicationCenterRecord;

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
 * 职位申请备注沟通信息总览关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobApplicationCommunicationCenter extends TableImpl<JobApplicationCommunicationCenterRecord> {

    private static final long serialVersionUID = 558646124;

    /**
     * The reference instance of <code>jobdb.job_application_communication_center</code>
     */
    public static final JobApplicationCommunicationCenter JOB_APPLICATION_COMMUNICATION_CENTER = new JobApplicationCommunicationCenter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobApplicationCommunicationCenterRecord> getRecordType() {
        return JobApplicationCommunicationCenterRecord.class;
    }

    /**
     * The column <code>jobdb.job_application_communication_center.id</code>. primaryKey
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primaryKey");

    /**
     * The column <code>jobdb.job_application_communication_center.communication_type</code>. 沟通类型:1.备注(job_application_remark),2.沟通记录(job_application_communication_record)
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Byte> COMMUNICATION_TYPE = createField("communication_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "沟通类型:1.备注(job_application_remark),2.沟通记录(job_application_communication_record)");

    /**
     * The column <code>jobdb.job_application_communication_center.communication_id</code>. 沟通对应id,对应的沟通类型存储对应的沟通id
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Integer> COMMUNICATION_ID = createField("communication_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "沟通对应id,对应的沟通类型存储对应的沟通id");

    /**
     * The column <code>jobdb.job_application_communication_center.create_time</code>. 创建时间
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>jobdb.job_application_communication_center.update_time</code>. 更新时间
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>jobdb.job_application_communication_center.job_application_id</code>. 职位申请id job_application.id
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Integer> JOB_APPLICATION_ID = createField("job_application_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职位申请id job_application.id");

    /**
     * The column <code>jobdb.job_application_communication_center.communication_status</code>. 沟通状态  configdb.config_ats_event_type
     */
    public final TableField<JobApplicationCommunicationCenterRecord, Byte> COMMUNICATION_STATUS = createField("communication_status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "沟通状态  configdb.config_ats_event_type");

    /**
     * Create a <code>jobdb.job_application_communication_center</code> table reference
     */
    public JobApplicationCommunicationCenter() {
        this("job_application_communication_center", null);
    }

    /**
     * Create an aliased <code>jobdb.job_application_communication_center</code> table reference
     */
    public JobApplicationCommunicationCenter(String alias) {
        this(alias, JOB_APPLICATION_COMMUNICATION_CENTER);
    }

    private JobApplicationCommunicationCenter(String alias, Table<JobApplicationCommunicationCenterRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobApplicationCommunicationCenter(String alias, Table<JobApplicationCommunicationCenterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "职位申请备注沟通信息总览关系");
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
    public Identity<JobApplicationCommunicationCenterRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_APPLICATION_COMMUNICATION_CENTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobApplicationCommunicationCenterRecord> getPrimaryKey() {
        return Keys.KEY_JOB_APPLICATION_COMMUNICATION_CENTER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobApplicationCommunicationCenterRecord>> getKeys() {
        return Arrays.<UniqueKey<JobApplicationCommunicationCenterRecord>>asList(Keys.KEY_JOB_APPLICATION_COMMUNICATION_CENTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobApplicationCommunicationCenter as(String alias) {
        return new JobApplicationCommunicationCenter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JobApplicationCommunicationCenter rename(String name) {
        return new JobApplicationCommunicationCenter(name, null);
    }
}
