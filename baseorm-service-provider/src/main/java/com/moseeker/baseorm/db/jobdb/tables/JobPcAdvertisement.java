/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcAdvertisementRecord;

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
 * 首页广告位数据表设计
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPcAdvertisement extends TableImpl<JobPcAdvertisementRecord> {

    private static final long serialVersionUID = 1443769656;

    /**
     * The reference instance of <code>jobdb.job_pc_advertisement</code>
     */
    public static final JobPcAdvertisement JOB_PC_ADVERTISEMENT = new JobPcAdvertisement();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JobPcAdvertisementRecord> getRecordType() {
        return JobPcAdvertisementRecord.class;
    }

    /**
     * The column <code>jobdb.job_pc_advertisement.id</code>. 自增主键
     */
    public final TableField<JobPcAdvertisementRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "自增主键");

    /**
     * The column <code>jobdb.job_pc_advertisement.img</code>. 图片地址
     */
    public final TableField<JobPcAdvertisementRecord, String> IMG = createField("img", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "图片地址");

    /**
     * The column <code>jobdb.job_pc_advertisement.name</code>. 推荐模块名称
     */
    public final TableField<JobPcAdvertisementRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐模块名称");

    /**
     * The column <code>jobdb.job_pc_advertisement.href</code>. 广告位链接地址
     */
    public final TableField<JobPcAdvertisementRecord, String> HREF = createField("href", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "广告位链接地址");

    /**
     * The column <code>jobdb.job_pc_advertisement.status</code>. 是否可以用，0：不可用 1：可用
     */
    public final TableField<JobPcAdvertisementRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可以用，0：不可用 1：可用");

    /**
     * The column <code>jobdb.job_pc_advertisement.description</code>. 广告位说明
     */
    public final TableField<JobPcAdvertisementRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(200).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "广告位说明");

    /**
     * The column <code>jobdb.job_pc_advertisement.create_time</code>. 创建时间
     */
    public final TableField<JobPcAdvertisementRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>jobdb.job_pc_advertisement.update_time</code>. 更新时间
     */
    public final TableField<JobPcAdvertisementRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>jobdb.job_pc_advertisement</code> table reference
     */
    public JobPcAdvertisement() {
        this("job_pc_advertisement", null);
    }

    /**
     * Create an aliased <code>jobdb.job_pc_advertisement</code> table reference
     */
    public JobPcAdvertisement(String alias) {
        this(alias, JOB_PC_ADVERTISEMENT);
    }

    private JobPcAdvertisement(String alias, Table<JobPcAdvertisementRecord> aliased) {
        this(alias, aliased, null);
    }

    private JobPcAdvertisement(String alias, Table<JobPcAdvertisementRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "首页广告位数据表设计");
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
    public Identity<JobPcAdvertisementRecord, Integer> getIdentity() {
        return Keys.IDENTITY_JOB_PC_ADVERTISEMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JobPcAdvertisementRecord> getPrimaryKey() {
        return Keys.KEY_JOB_PC_ADVERTISEMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JobPcAdvertisementRecord>> getKeys() {
        return Arrays.<UniqueKey<JobPcAdvertisementRecord>>asList(Keys.KEY_JOB_PC_ADVERTISEMENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobPcAdvertisement as(String alias) {
        return new JobPcAdvertisement(alias, this);
    }
}
