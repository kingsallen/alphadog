/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5StatisticsRecord;

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
 * 专题传播统计次数表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHtml5Statistics extends TableImpl<HrHtml5StatisticsRecord> {

    private static final long serialVersionUID = -1205647491;

    /**
     * The reference instance of <code>hrdb.hr_html5_statistics</code>
     */
    public static final HrHtml5Statistics HR_HTML5_STATISTICS = new HrHtml5Statistics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrHtml5StatisticsRecord> getRecordType() {
        return HrHtml5StatisticsRecord.class;
    }

    /**
     * The column <code>hrdb.hr_html5_statistics.id</code>. primary key
     */
    public final TableField<HrHtml5StatisticsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primary key");

    /**
     * The column <code>hrdb.hr_html5_statistics.topic_id</code>. wx_topic.id
     */
    public final TableField<HrHtml5StatisticsRecord, Integer> TOPIC_ID = createField("topic_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "wx_topic.id");

    /**
     * The column <code>hrdb.hr_html5_statistics.company_id</code>. company.id
     */
    public final TableField<HrHtml5StatisticsRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "company.id");

    /**
     * The column <code>hrdb.hr_html5_statistics.view_num_pv</code>. 浏览次数
     */
    public final TableField<HrHtml5StatisticsRecord, Integer> VIEW_NUM_PV = createField("view_num_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "浏览次数");

    /**
     * The column <code>hrdb.hr_html5_statistics.recom_view_num_pv</code>. 推荐浏览次数
     */
    public final TableField<HrHtml5StatisticsRecord, Integer> RECOM_VIEW_NUM_PV = createField("recom_view_num_pv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "推荐浏览次数");

    /**
     * The column <code>hrdb.hr_html5_statistics.create_date</code>. 创建日期
     */
    public final TableField<HrHtml5StatisticsRecord, Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "创建日期");

    /**
     * Create a <code>hrdb.hr_html5_statistics</code> table reference
     */
    public HrHtml5Statistics() {
        this("hr_html5_statistics", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_html5_statistics</code> table reference
     */
    public HrHtml5Statistics(String alias) {
        this(alias, HR_HTML5_STATISTICS);
    }

    private HrHtml5Statistics(String alias, Table<HrHtml5StatisticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrHtml5Statistics(String alias, Table<HrHtml5StatisticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "专题传播统计次数表");
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
    public Identity<HrHtml5StatisticsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_HTML5_STATISTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrHtml5StatisticsRecord> getPrimaryKey() {
        return Keys.KEY_HR_HTML5_STATISTICS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrHtml5StatisticsRecord>> getKeys() {
        return Arrays.<UniqueKey<HrHtml5StatisticsRecord>>asList(Keys.KEY_HR_HTML5_STATISTICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHtml5Statistics as(String alias) {
        return new HrHtml5Statistics(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrHtml5Statistics rename(String name) {
        return new HrHtml5Statistics(name, null);
    }
}
