/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.WebPlaDailyRecord;

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
 * 每日浏览申请统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WebPlaDaily extends TableImpl<WebPlaDailyRecord> {

    private static final long serialVersionUID = -750739063;

    /**
     * The reference instance of <code>analytics.web_pla_daily</code>
     */
    public static final WebPlaDaily WEB_PLA_DAILY = new WebPlaDaily();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WebPlaDailyRecord> getRecordType() {
        return WebPlaDailyRecord.class;
    }

    /**
     * The column <code>analytics.web_pla_daily.id</code>. primary key
     */
    public final TableField<WebPlaDailyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primary key");

    /**
     * The column <code>analytics.web_pla_daily.cid</code>.
     */
    public final TableField<WebPlaDailyRecord, Integer> CID = createField("cid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.web_pla_daily.create_time</code>.
     */
    public final TableField<WebPlaDailyRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>analytics.web_pla_daily.p_pv</code>. 职位页面浏览量
     */
    public final TableField<WebPlaDailyRecord, Integer> P_PV = createField("p_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位页面浏览量");

    /**
     * The column <code>analytics.web_pla_daily.p_uv</code>. 职位页面浏览用户量
     */
    public final TableField<WebPlaDailyRecord, Integer> P_UV = createField("p_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位页面浏览用户量");

    /**
     * The column <code>analytics.web_pla_daily.p_s_pv</code>. 职位页面转发浏览量
     */
    public final TableField<WebPlaDailyRecord, Integer> P_S_PV = createField("p_s_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位页面转发浏览量");

    /**
     * The column <code>analytics.web_pla_daily.p_s_uv</code>. 职位页面转发浏览用户量
     */
    public final TableField<WebPlaDailyRecord, Integer> P_S_UV = createField("p_s_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位页面转发浏览用户量");

    /**
     * The column <code>analytics.web_pla_daily.l_pv</code>. 职位列表页面浏览量
     */
    public final TableField<WebPlaDailyRecord, Integer> L_PV = createField("l_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位列表页面浏览量");

    /**
     * The column <code>analytics.web_pla_daily.l_uv</code>. 职位列表页面浏览用户量
     */
    public final TableField<WebPlaDailyRecord, Integer> L_UV = createField("l_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位列表页面浏览用户量");

    /**
     * The column <code>analytics.web_pla_daily.l_s_pv</code>. 职位列表页面转发浏览量
     */
    public final TableField<WebPlaDailyRecord, Integer> L_S_PV = createField("l_s_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位列表页面转发浏览量");

    /**
     * The column <code>analytics.web_pla_daily.l_s_uv</code>. 职位列表页面转发浏览用户量
     */
    public final TableField<WebPlaDailyRecord, Integer> L_S_UV = createField("l_s_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位列表页面转发浏览用户量");

    /**
     * The column <code>analytics.web_pla_daily.a_count</code>. 申请量
     */
    public final TableField<WebPlaDailyRecord, Integer> A_COUNT = createField("a_count", org.jooq.impl.SQLDataType.INTEGER, this, "申请量");

    /**
     * The column <code>analytics.web_pla_daily.a_user</code>. 申请人数
     */
    public final TableField<WebPlaDailyRecord, Integer> A_USER = createField("a_user", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "申请人数");

    /**
     * The column <code>analytics.web_pla_daily.a_s_count</code>. 转发产生的申请量
     */
    public final TableField<WebPlaDailyRecord, Integer> A_S_COUNT = createField("a_s_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "转发产生的申请量");

    /**
     * The column <code>analytics.web_pla_daily.a_s_user</code>. 转发产生的申请人数
     */
    public final TableField<WebPlaDailyRecord, Integer> A_S_USER = createField("a_s_user", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "转发产生的申请人数");

    /**
     * Create a <code>analytics.web_pla_daily</code> table reference
     */
    public WebPlaDaily() {
        this("web_pla_daily", null);
    }

    /**
     * Create an aliased <code>analytics.web_pla_daily</code> table reference
     */
    public WebPlaDaily(String alias) {
        this(alias, WEB_PLA_DAILY);
    }

    private WebPlaDaily(String alias, Table<WebPlaDailyRecord> aliased) {
        this(alias, aliased, null);
    }

    private WebPlaDaily(String alias, Table<WebPlaDailyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "每日浏览申请统计表");
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
    public Identity<WebPlaDailyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_WEB_PLA_DAILY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WebPlaDailyRecord> getPrimaryKey() {
        return Keys.KEY_WEB_PLA_DAILY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WebPlaDailyRecord>> getKeys() {
        return Arrays.<UniqueKey<WebPlaDailyRecord>>asList(Keys.KEY_WEB_PLA_DAILY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDaily as(String alias) {
        return new WebPlaDaily(alias, this);
    }

    /**
     * Rename this table
     */
    public WebPlaDaily rename(String name) {
        return new WebPlaDaily(name, null);
    }
}
