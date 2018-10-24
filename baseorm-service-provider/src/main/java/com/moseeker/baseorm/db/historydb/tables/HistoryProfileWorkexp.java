/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryProfileWorkexpRecord;

import java.sql.Date;
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
 * Profile的工作经历 历史表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryProfileWorkexp extends TableImpl<HistoryProfileWorkexpRecord> {

    private static final long serialVersionUID = 1442247730;

    /**
     * The reference instance of <code>historydb.history_profile_workexp</code>
     */
    public static final HistoryProfileWorkexp HISTORY_PROFILE_WORKEXP = new HistoryProfileWorkexp();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryProfileWorkexpRecord> getRecordType() {
        return HistoryProfileWorkexpRecord.class;
    }

    /**
     * The column <code>historydb.history_profile_workexp.id</code>. 主key
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>historydb.history_profile_workexp.profile_id</code>. profile.id
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "profile.id");

    /**
     * The column <code>historydb.history_profile_workexp.start</code>. 起止时间-起 yyyy-mm-dd
     */
    public final TableField<HistoryProfileWorkexpRecord, Date> START = createField("start", org.jooq.impl.SQLDataType.DATE, this, "起止时间-起 yyyy-mm-dd");

    /**
     * The column <code>historydb.history_profile_workexp.end</code>. 起止时间-止 yyyy-mm-dd
     */
    public final TableField<HistoryProfileWorkexpRecord, Date> END = createField("end", org.jooq.impl.SQLDataType.DATE, this, "起止时间-止 yyyy-mm-dd");

    /**
     * The column <code>historydb.history_profile_workexp.end_until_now</code>. 是否至今 0：否 1：是
     */
    public final TableField<HistoryProfileWorkexpRecord, Byte> END_UNTIL_NOW = createField("end_until_now", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否至今 0：否 1：是");

    /**
     * The column <code>historydb.history_profile_workexp.salary_code</code>. 薪资code
     */
    public final TableField<HistoryProfileWorkexpRecord, Byte> SALARY_CODE = createField("salary_code", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "薪资code");

    /**
     * The column <code>historydb.history_profile_workexp.industry_code</code>. 行业字典编码
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> INDUSTRY_CODE = createField("industry_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "行业字典编码");

    /**
     * The column <code>historydb.history_profile_workexp.industry_name</code>. 行业名称
     */
    public final TableField<HistoryProfileWorkexpRecord, String> INDUSTRY_NAME = createField("industry_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "行业名称");

    /**
     * The column <code>historydb.history_profile_workexp.company_id</code>. 公司ID, hr_company.id
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "公司ID, hr_company.id");

    /**
     * The column <code>historydb.history_profile_workexp.department_name</code>. 部门名称
     */
    public final TableField<HistoryProfileWorkexpRecord, String> DEPARTMENT_NAME = createField("department_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "部门名称");

    /**
     * The column <code>historydb.history_profile_workexp.position_code</code>. 职能字典编码
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> POSITION_CODE = createField("position_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职能字典编码");

    /**
     * The column <code>historydb.history_profile_workexp.position_name</code>. 职能字典名称
     */
    public final TableField<HistoryProfileWorkexpRecord, String> POSITION_NAME = createField("position_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职能字典名称");

    /**
     * The column <code>historydb.history_profile_workexp.description</code>. 工作描述
     */
    public final TableField<HistoryProfileWorkexpRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "工作描述");

    /**
     * The column <code>historydb.history_profile_workexp.type</code>. 工作类型 0:没选择 1:全职 2:兼职 3:实习
     */
    public final TableField<HistoryProfileWorkexpRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "工作类型 0:没选择 1:全职 2:兼职 3:实习");

    /**
     * The column <code>historydb.history_profile_workexp.city_code</code>. 工作地点（城市），字典编码
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> CITY_CODE = createField("city_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "工作地点（城市），字典编码");

    /**
     * The column <code>historydb.history_profile_workexp.city_name</code>. 工作地点（城市）名称
     */
    public final TableField<HistoryProfileWorkexpRecord, String> CITY_NAME = createField("city_name", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "工作地点（城市）名称");

    /**
     * The column <code>historydb.history_profile_workexp.report_to</code>. 汇报对象
     */
    public final TableField<HistoryProfileWorkexpRecord, String> REPORT_TO = createField("report_to", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "汇报对象");

    /**
     * The column <code>historydb.history_profile_workexp.underlings</code>. 下属人数, 0:没有下属
     */
    public final TableField<HistoryProfileWorkexpRecord, Integer> UNDERLINGS = createField("underlings", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "下属人数, 0:没有下属");

    /**
     * The column <code>historydb.history_profile_workexp.reference</code>. 证明人
     */
    public final TableField<HistoryProfileWorkexpRecord, String> REFERENCE = createField("reference", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "证明人");

    /**
     * The column <code>historydb.history_profile_workexp.resign_reason</code>. 离职原因
     */
    public final TableField<HistoryProfileWorkexpRecord, String> RESIGN_REASON = createField("resign_reason", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "离职原因");

    /**
     * The column <code>historydb.history_profile_workexp.achievement</code>. 主要业绩
     */
    public final TableField<HistoryProfileWorkexpRecord, String> ACHIEVEMENT = createField("achievement", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "主要业绩");

    /**
     * The column <code>historydb.history_profile_workexp.create_time</code>. 创建时间
     */
    public final TableField<HistoryProfileWorkexpRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>historydb.history_profile_workexp.update_time</code>. 更新时间
     */
    public final TableField<HistoryProfileWorkexpRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>historydb.history_profile_workexp.job</code>. 所处职位
     */
    public final TableField<HistoryProfileWorkexpRecord, String> JOB = createField("job", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "所处职位");

    /**
     * Create a <code>historydb.history_profile_workexp</code> table reference
     */
    public HistoryProfileWorkexp() {
        this("history_profile_workexp", null);
    }

    /**
     * Create an aliased <code>historydb.history_profile_workexp</code> table reference
     */
    public HistoryProfileWorkexp(String alias) {
        this(alias, HISTORY_PROFILE_WORKEXP);
    }

    private HistoryProfileWorkexp(String alias, Table<HistoryProfileWorkexpRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryProfileWorkexp(String alias, Table<HistoryProfileWorkexpRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Profile的工作经历 历史表");
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
    public Identity<HistoryProfileWorkexpRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HISTORY_PROFILE_WORKEXP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryProfileWorkexpRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_PROFILE_WORKEXP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryProfileWorkexpRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryProfileWorkexpRecord>>asList(Keys.KEY_HISTORY_PROFILE_WORKEXP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryProfileWorkexp as(String alias) {
        return new HistoryProfileWorkexp(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HistoryProfileWorkexp rename(String name) {
        return new HistoryProfileWorkexp(name, null);
    }
}
