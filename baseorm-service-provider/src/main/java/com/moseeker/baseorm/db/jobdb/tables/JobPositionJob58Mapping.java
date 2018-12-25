/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionJob58MappingRecord;

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
 * 58同城职位映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionJob58Mapping extends TableImpl<JobPositionJob58MappingRecord> {

    private static final long serialVersionUID = -1540291435;

    /**
     * The reference instance of <code>jobdb.job_position_job58_mapping</code>
     */
    public static final JobPositionJob58Mapping JOB_POSITION_JOB58_MAPPING = new JobPositionJob58Mapping();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobPositionJob58MappingRecord> getRecordType() {
        return JobPositionJob58MappingRecord.class;
    }

    /**
     * The column <code>jobdb.job_position_job58_mapping.id</code>.
     */
    public final TableField<JobPositionJob58MappingRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>jobdb.job_position_job58_mapping.info_id</code>. 58职位id
     */
    public final TableField<JobPositionJob58MappingRecord, String> INFO_ID = createField("info_id", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "58职位id");

    /**
     * The column <code>jobdb.job_position_job58_mapping.position_id</code>. 仟寻职位id
     */
    public final TableField<JobPositionJob58MappingRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "仟寻职位id");

    /**
     * The column <code>jobdb.job_position_job58_mapping.url</code>. 58返回的url
     */
    public final TableField<JobPositionJob58MappingRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "58返回的url");

    /**
     * The column <code>jobdb.job_position_job58_mapping.state</code>. 职位状态 0 下架 1 正常
     */
    public final TableField<JobPositionJob58MappingRecord, Byte> STATE = createField("state", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "职位状态 0 下架 1 正常");

    /**
     * The column <code>jobdb.job_position_job58_mapping.open_id</code>. 用户在58的唯一标识
     */
    public final TableField<JobPositionJob58MappingRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR.length(255).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "用户在58的唯一标识");

    /**
     * The column <code>jobdb.job_position_job58_mapping.create_time</code>.
     */
    public final TableField<JobPositionJob58MappingRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>jobdb.job_position_job58_mapping.update_time</code>.
     */
    public final TableField<JobPositionJob58MappingRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>jobdb.job_position_job58_mapping</code> table reference
     */
    public JobPositionJob58Mapping() {
        this("job_position_job58_mapping", null);
    }

    /**
     * Create an aliased <code>jobdb.job_position_job58_mapping</code> table reference
     */
    public JobPositionJob58Mapping(String alias) {
        this(alias, JOB_POSITION_JOB58_MAPPING);
    }

    private JobPositionJob58Mapping(String alias, Table<JobPositionJob58MappingRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobPositionJob58Mapping(String alias, Table<JobPositionJob58MappingRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "58同城职位映射表");
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
    public Identity<JobPositionJob58MappingRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_POSITION_JOB58_MAPPING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobPositionJob58MappingRecord> getPrimaryKey() {
        return Keys.KEY_JOB_POSITION_JOB58_MAPPING_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobPositionJob58MappingRecord>> getKeys() {
        return Arrays.<UniqueKey<JobPositionJob58MappingRecord>>asList(Keys.KEY_JOB_POSITION_JOB58_MAPPING_PRIMARY, Keys.KEY_JOB_POSITION_JOB58_MAPPING_INFO_ID_INDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPositionJob58Mapping as(String alias) {
        return new JobPositionJob58Mapping(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JobPositionJob58Mapping rename(String name) {
        return new JobPositionJob58Mapping(name, null);
    }
}