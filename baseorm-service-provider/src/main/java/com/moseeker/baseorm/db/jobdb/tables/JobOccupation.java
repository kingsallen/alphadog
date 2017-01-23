/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRecord;

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
 * 公司自定义职能表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobOccupation extends TableImpl<JobOccupationRecord> {

    private static final long serialVersionUID = 292204287;

    /**
     * The reference instance of <code>jobdb.job_occupation</code>
     */
    public static final JobOccupation JOB_OCCUPATION = new JobOccupation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobOccupationRecord> getRecordType() {
        return JobOccupationRecord.class;
    }

    /**
     * The column <code>jobdb.job_occupation.id</code>.
     */
    public final TableField<JobOccupationRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>jobdb.job_occupation.company_id</code>. hrdb.hr_company.id
     */
    public final TableField<JobOccupationRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER, this, "hrdb.hr_company.id");

    /**
     * The column <code>jobdb.job_occupation.status</code>. 职位自定义字段是否有效，0：无效；1：有效
     */
    public final TableField<JobOccupationRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "职位自定义字段是否有效，0：无效；1：有效");

    /**
     * The column <code>jobdb.job_occupation.name</code>. 自定义职能名称
     */
    public final TableField<JobOccupationRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(50).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定义职能名称");

    /**
     * The column <code>jobdb.job_occupation.update_time</code>. 数据更新时间
     */
    public final TableField<JobOccupationRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "数据更新时间");

    /**
     * The column <code>jobdb.job_occupation.create_time</code>. 创建时间
     */
    public final TableField<JobOccupationRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * Create a <code>jobdb.job_occupation</code> table reference
     */
    public JobOccupation() {
        this("job_occupation", null);
    }

    /**
     * Create an aliased <code>jobdb.job_occupation</code> table reference
     */
    public JobOccupation(String alias) {
        this(alias, JOB_OCCUPATION);
    }

    private JobOccupation(String alias, Table<JobOccupationRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobOccupation(String alias, Table<JobOccupationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "公司自定义职能表");
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
    public Identity<JobOccupationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_OCCUPATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobOccupationRecord> getPrimaryKey() {
        return Keys.KEY_JOB_OCCUPATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobOccupationRecord>> getKeys() {
        return Arrays.<UniqueKey<JobOccupationRecord>>asList(Keys.KEY_JOB_OCCUPATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobOccupation as(String alias) {
        return new JobOccupation(alias, this);
    }

    /**
     * Rename this table
     */
    public JobOccupation rename(String name) {
        return new JobOccupation(name, null);
    }
}
