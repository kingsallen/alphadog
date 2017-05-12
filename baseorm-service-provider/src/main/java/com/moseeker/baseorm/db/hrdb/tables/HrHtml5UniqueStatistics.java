/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5UniqueStatisticsRecord;

import java.sql.Date;
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
 * 专题传播统计去重信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHtml5UniqueStatistics extends TableImpl<HrHtml5UniqueStatisticsRecord> {

    private static final long serialVersionUID = 857025569;

    /**
     * The reference instance of <code>hrdb.hr_html5_unique_statistics</code>
     */
    public static final HrHtml5UniqueStatistics HR_HTML5_UNIQUE_STATISTICS = new HrHtml5UniqueStatistics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrHtml5UniqueStatisticsRecord> getRecordType() {
        return HrHtml5UniqueStatisticsRecord.class;
    }

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.id</code>. primary key
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primary key");

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.topic_id</code>. wx_topic.id
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Integer> TOPIC_ID = createField("topic_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "wx_topic.id");

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.company_id</code>. company.id
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "company.id");

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.view_num_uv</code>. 浏览人数
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Integer> VIEW_NUM_UV = createField("view_num_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "浏览人数");

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.recom_view_num_uv</code>. 推荐浏览人数
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Integer> RECOM_VIEW_NUM_UV = createField("recom_view_num_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "推荐浏览人数");

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.create_date</code>. 创建日期
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "创建日期");

    /**
     * The column <code>hrdb.hr_html5_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public final TableField<HrHtml5UniqueStatisticsRecord, Integer> INFO_TYPE = createField("info_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0: 日数据，1：周数据，2：月数据");

    /**
     * Create a <code>hrdb.hr_html5_unique_statistics</code> table reference
     */
    public HrHtml5UniqueStatistics() {
        this("hr_html5_unique_statistics", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_html5_unique_statistics</code> table reference
     */
    public HrHtml5UniqueStatistics(String alias) {
        this(alias, HR_HTML5_UNIQUE_STATISTICS);
    }

    private HrHtml5UniqueStatistics(String alias, Table<HrHtml5UniqueStatisticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrHtml5UniqueStatistics(String alias, Table<HrHtml5UniqueStatisticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "专题传播统计去重信息表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Hrdb.HRDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HrHtml5UniqueStatisticsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_HTML5_UNIQUE_STATISTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrHtml5UniqueStatisticsRecord> getPrimaryKey() {
        return Keys.KEY_HR_HTML5_UNIQUE_STATISTICS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrHtml5UniqueStatisticsRecord>> getKeys() {
        return Arrays.<UniqueKey<HrHtml5UniqueStatisticsRecord>>asList(Keys.KEY_HR_HTML5_UNIQUE_STATISTICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5UniqueStatistics as(String alias) {
        return new HrHtml5UniqueStatistics(alias, this);
    }

    /**
     * Rename this table
     */
    public HrHtml5UniqueStatistics rename(String name) {
        return new HrHtml5UniqueStatistics(name, null);
    }
}
