/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables;


import com.moseeker.baseorm.db.talentpooldb.Keys;
import com.moseeker.baseorm.db.talentpooldb.Talentpooldb;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileFilterRecord;

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
 * 简历筛选表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileFilter extends TableImpl<TalentpoolProfileFilterRecord> {

    private static final long serialVersionUID = -230180120;

    /**
     * The reference instance of <code>talentpooldb.talentpool_profile_filter</code>
     */
    public static final TalentpoolProfileFilter TALENTPOOL_PROFILE_FILTER = new TalentpoolProfileFilter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TalentpoolProfileFilterRecord> getRecordType() {
        return TalentpoolProfileFilterRecord.class;
    }

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.id</code>.
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.company_id</code>. 公司主键
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "公司主键");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.name</code>. 标签的名称
     */
    public final TableField<TalentpoolProfileFilterRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "标签的名称");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.origins</code>. 来源
     */
    public final TableField<TalentpoolProfileFilterRecord, String> ORIGINS = createField("origins", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "来源");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.work_years</code>. 工作年限 存储的是code
     */
    public final TableField<TalentpoolProfileFilterRecord, String> WORK_YEARS = createField("work_years", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "工作年限 存储的是code");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.city_name</code>. 现居住地
     */
    public final TableField<TalentpoolProfileFilterRecord, String> CITY_NAME = createField("city_name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "现居住地");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.city_code</code>. 城市的code
     */
    public final TableField<TalentpoolProfileFilterRecord, String> CITY_CODE = createField("city_code", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "城市的code");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.degree</code>. 学历 传code
     */
    public final TableField<TalentpoolProfileFilterRecord, String> DEGREE = createField("degree", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "学历 传code");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.past_position</code>. 曾经工作过的职位
     */
    public final TableField<TalentpoolProfileFilterRecord, String> PAST_POSITION = createField("past_position", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "曾经工作过的职位");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.in_last_job_search_position</code>. 是否只搜索最近一份工作 0否 1是
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> IN_LAST_JOB_SEARCH_POSITION = createField("in_last_job_search_position", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否只搜索最近一份工作 0否 1是");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.min_age</code>. 最小年龄
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> MIN_AGE = createField("min_age", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "最小年龄");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.max_age</code>. 最大年龄
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> MAX_AGE = createField("max_age", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "最大年龄");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.intention_city_name</code>. 期待工作地
     */
    public final TableField<TalentpoolProfileFilterRecord, String> INTENTION_CITY_NAME = createField("intention_city_name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "期待工作地");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.intention_city_code</code>. 期望城市的code
     */
    public final TableField<TalentpoolProfileFilterRecord, String> INTENTION_CITY_CODE = createField("intention_city_code", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "期望城市的code");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.intention_salary_code</code>. 期待薪资
     */
    public final TableField<TalentpoolProfileFilterRecord, String> INTENTION_SALARY_CODE = createField("intention_salary_code", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "期待薪资");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.sex</code>. 性别 0男，1女 2不限
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> SEX = createField("sex", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("2", org.jooq.impl.SQLDataType.INTEGER)), this, "性别 0男，1女 2不限");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.is_recommend</code>. 是否只看内推 0否，1是
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> IS_RECOMMEND = createField("is_recommend", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否只看内推 0否，1是");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.company_name</code>. 工作过的公司
     */
    public final TableField<TalentpoolProfileFilterRecord, String> COMPANY_NAME = createField("company_name", org.jooq.impl.SQLDataType.VARCHAR.length(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "工作过的公司");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.in_last_job_search_company</code>. 是否只搜最近一个公司 0否，1是
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> IN_LAST_JOB_SEARCH_COMPANY = createField("in_last_job_search_company", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否只搜最近一个公司 0否，1是");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.create_time</code>. 创建时间
     */
    public final TableField<TalentpoolProfileFilterRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.disable</code>. 1有效，0无效，2关闭
     */
    public final TableField<TalentpoolProfileFilterRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "1有效，0无效，2关闭");

    /**
     * The column <code>talentpooldb.talentpool_profile_filter.update_time</code>.
     */
    public final TableField<TalentpoolProfileFilterRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>talentpooldb.talentpool_profile_filter</code> table reference
     */
    public TalentpoolProfileFilter() {
        this("talentpool_profile_filter", null);
    }

    /**
     * Create an aliased <code>talentpooldb.talentpool_profile_filter</code> table reference
     */
    public TalentpoolProfileFilter(String alias) {
        this(alias, TALENTPOOL_PROFILE_FILTER);
    }

    private TalentpoolProfileFilter(String alias, Table<TalentpoolProfileFilterRecord> aliased) {
        this(alias, aliased, null);
    }

    private TalentpoolProfileFilter(String alias, Table<TalentpoolProfileFilterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "简历筛选表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Talentpooldb.TALENTPOOLDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TalentpoolProfileFilterRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TALENTPOOL_PROFILE_FILTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TalentpoolProfileFilterRecord> getPrimaryKey() {
        return Keys.KEY_TALENTPOOL_PROFILE_FILTER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TalentpoolProfileFilterRecord>> getKeys() {
        return Arrays.<UniqueKey<TalentpoolProfileFilterRecord>>asList(Keys.KEY_TALENTPOOL_PROFILE_FILTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolProfileFilter as(String alias) {
        return new TalentpoolProfileFilter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TalentpoolProfileFilter rename(String name) {
        return new TalentpoolProfileFilter(name, null);
    }
}
