/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.analytics.tables;


import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.Keys;
import com.moseeker.db.analytics.tables.records.WebHrpCoreRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WebHrpCore extends TableImpl<WebHrpCoreRecord> {

    private static final long serialVersionUID = 295292783;

    /**
     * The reference instance of <code>analytics.web_hrp_core</code>
     */
    public static final WebHrpCore WEB_HRP_CORE = new WebHrpCore();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WebHrpCoreRecord> getRecordType() {
        return WebHrpCoreRecord.class;
    }

    /**
     * The column <code>analytics.web_hrp_core.id</code>.
     */
    public final TableField<WebHrpCoreRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.web_hrp_core.create_time</code>.
     */
    public final TableField<WebHrpCoreRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>analytics.web_hrp_core.sum</code>. 企业总数
     */
    public final TableField<WebHrpCoreRecord, Integer> SUM = createField("sum", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "企业总数");

    /**
     * The column <code>analytics.web_hrp_core.day_active</code>. 日活
     */
    public final TableField<WebHrpCoreRecord, Integer> DAY_ACTIVE = createField("day_active", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "日活");

    /**
     * The column <code>analytics.web_hrp_core.month_active</code>. 月活
     */
    public final TableField<WebHrpCoreRecord, Integer> MONTH_ACTIVE = createField("month_active", org.jooq.impl.SQLDataType.INTEGER, this, "月活");

    /**
     * The column <code>analytics.web_hrp_core.yesterday_position</code>. 昨日职位总量
     */
    public final TableField<WebHrpCoreRecord, Integer> YESTERDAY_POSITION = createField("yesterday_position", org.jooq.impl.SQLDataType.INTEGER, this, "昨日职位总量");

    /**
     * The column <code>analytics.web_hrp_core.new_position</code>. 今日职位新增
     */
    public final TableField<WebHrpCoreRecord, Integer> NEW_POSITION = createField("new_position", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "今日职位新增");

    /**
     * The column <code>analytics.web_hrp_core.today_spv</code>. 今日职位转发pv
     */
    public final TableField<WebHrpCoreRecord, Integer> TODAY_SPV = createField("today_spv", org.jooq.impl.SQLDataType.INTEGER, this, "今日职位转发pv");

    /**
     * The column <code>analytics.web_hrp_core.yesterday_application</code>. 昨日申请总量
     */
    public final TableField<WebHrpCoreRecord, Integer> YESTERDAY_APPLICATION = createField("yesterday_application", org.jooq.impl.SQLDataType.INTEGER, this, "昨日申请总量");

    /**
     * The column <code>analytics.web_hrp_core.new_application</code>. 今日新增申请
     */
    public final TableField<WebHrpCoreRecord, Integer> NEW_APPLICATION = createField("new_application", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "今日新增申请");

    /**
     * The column <code>analytics.web_hrp_core.new_register</code>. 企业号今日新增求职者
     */
    public final TableField<WebHrpCoreRecord, Integer> NEW_REGISTER = createField("new_register", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "企业号今日新增求职者");

    /**
     * The column <code>analytics.web_hrp_core.oauth_c</code>. 企业号一键登录oauth量
     */
    public final TableField<WebHrpCoreRecord, Integer> OAUTH_C = createField("oauth_c", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "企业号一键登录oauth量");

    /**
     * The column <code>analytics.web_hrp_core.cared</code>. 我感兴趣按钮点击量
     */
    public final TableField<WebHrpCoreRecord, Integer> CARED = createField("cared", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "我感兴趣按钮点击量");

    /**
     * The column <code>analytics.web_hrp_core.phones</code>. 留手机号数量
     */
    public final TableField<WebHrpCoreRecord, Integer> PHONES = createField("phones", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "留手机号数量");

    /**
     * The column <code>analytics.web_hrp_core.effective_position</code>.
     */
    public final TableField<WebHrpCoreRecord, Integer> EFFECTIVE_POSITION = createField("effective_position", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * Create a <code>analytics.web_hrp_core</code> table reference
     */
    public WebHrpCore() {
        this("web_hrp_core", null);
    }

    /**
     * Create an aliased <code>analytics.web_hrp_core</code> table reference
     */
    public WebHrpCore(String alias) {
        this(alias, WEB_HRP_CORE);
    }

    private WebHrpCore(String alias, Table<WebHrpCoreRecord> aliased) {
        this(alias, aliased, null);
    }

    private WebHrpCore(String alias, Table<WebHrpCoreRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Analytics.ANALYTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<WebHrpCoreRecord, Integer> getIdentity() {
        return Keys.IDENTITY_WEB_HRP_CORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WebHrpCoreRecord> getPrimaryKey() {
        return Keys.KEY_WEB_HRP_CORE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WebHrpCoreRecord>> getKeys() {
        return Arrays.<UniqueKey<WebHrpCoreRecord>>asList(Keys.KEY_WEB_HRP_CORE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebHrpCore as(String alias) {
        return new WebHrpCore(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WebHrpCore rename(String name) {
        return new WebHrpCore(name, null);
    }
}
