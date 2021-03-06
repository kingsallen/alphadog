/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobCustomRecord;

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
 * 职位自定义字段配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobCustom extends TableImpl<JobCustomRecord> {

    private static final long serialVersionUID = -404017494;

    /**
     * The reference instance of <code>jobdb.job_custom</code>
     */
    public static final JobCustom JOB_CUSTOM = new JobCustom();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobCustomRecord> getRecordType() {
        return JobCustomRecord.class;
    }

    /**
     * The column <code>jobdb.job_custom.id</code>.
     */
    public final TableField<JobCustomRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>jobdb.job_custom.company_id</code>. hr_company.id
     */
    public final TableField<JobCustomRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "hr_company.id");

    /**
     * The column <code>jobdb.job_custom.status</code>. 职位自定义字段是否有效，0：无效；1：有效
     */
    public final TableField<JobCustomRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "职位自定义字段是否有效，0：无效；1：有效");

    /**
     * The column <code>jobdb.job_custom.name</code>. 职位自定义字段值
     */
    public final TableField<JobCustomRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职位自定义字段值");

    /**
     * The column <code>jobdb.job_custom.type</code>. 职位自定义字段类型，1：select；2：text；3：radio；4：checkbox
     */
    public final TableField<JobCustomRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "职位自定义字段类型，1：select；2：text；3：radio；4：checkbox");

    /**
     * The column <code>jobdb.job_custom.create_time</code>. 创建时间
     */
    public final TableField<JobCustomRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>jobdb.job_custom.update_time</code>. 修改时间
     */
    public final TableField<JobCustomRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * Create a <code>jobdb.job_custom</code> table reference
     */
    public JobCustom() {
        this("job_custom", null);
    }

    /**
     * Create an aliased <code>jobdb.job_custom</code> table reference
     */
    public JobCustom(String alias) {
        this(alias, JOB_CUSTOM);
    }

    private JobCustom(String alias, Table<JobCustomRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobCustom(String alias, Table<JobCustomRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "职位自定义字段配置表");
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
    public Identity<JobCustomRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_CUSTOM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobCustomRecord> getPrimaryKey() {
        return Keys.KEY_JOB_CUSTOM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobCustomRecord>> getKeys() {
        return Arrays.<UniqueKey<JobCustomRecord>>asList(Keys.KEY_JOB_CUSTOM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobCustom as(String alias) {
        return new JobCustom(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JobCustom rename(String name) {
        return new JobCustom(name, null);
    }
}
