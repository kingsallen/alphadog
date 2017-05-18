/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleUniqueStatisticsRecord;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.jooq.*;
import org.jooq.impl.TableImpl;


/**
 * 微信图文传播去重信息统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrRuleUniqueStatistics extends TableImpl<HrRuleUniqueStatisticsRecord> {

    private static final long serialVersionUID = -698134421;

    /**
     * The reference instance of <code>hrdb.hr_rule_unique_statistics</code>
     */
    public static final HrRuleUniqueStatistics HR_RULE_UNIQUE_STATISTICS = new HrRuleUniqueStatistics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrRuleUniqueStatisticsRecord> getRecordType() {
        return HrRuleUniqueStatisticsRecord.class;
    }

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.id</code>. primary key
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primary key");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.wxrule_id</code>. wx_rule.id
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Integer> WXRULE_ID = createField("wxrule_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "wx_rule.id");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.menu_name</code>. 菜单名称
     */
    public final TableField<HrRuleUniqueStatisticsRecord, String> MENU_NAME = createField("menu_name", org.jooq.impl.SQLDataType.VARCHAR.length(999), this, "菜单名称");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.type</code>. 0: wx_rule, 1: menu
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0: wx_rule, 1: menu");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.company_id</code>. company.id
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "company.id");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.view_num_uv</code>. 浏览人数
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Integer> VIEW_NUM_UV = createField("view_num_uv", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "浏览人数");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.create_date</code>. 创建日期
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "创建日期");

    /**
     * The column <code>hrdb.hr_rule_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public final TableField<HrRuleUniqueStatisticsRecord, Integer> INFO_TYPE = createField("info_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0: 日数据，1：周数据，2：月数据");

    /**
     * Create a <code>hrdb.hr_rule_unique_statistics</code> table reference
     */
    public HrRuleUniqueStatistics() {
        this("hr_rule_unique_statistics", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_rule_unique_statistics</code> table reference
     */
    public HrRuleUniqueStatistics(String alias) {
        this(alias, HR_RULE_UNIQUE_STATISTICS);
    }

    private HrRuleUniqueStatistics(String alias, Table<HrRuleUniqueStatisticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrRuleUniqueStatistics(String alias, Table<HrRuleUniqueStatisticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "微信图文传播去重信息统计表");
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
    public Identity<HrRuleUniqueStatisticsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_RULE_UNIQUE_STATISTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrRuleUniqueStatisticsRecord> getPrimaryKey() {
        return Keys.KEY_HR_RULE_UNIQUE_STATISTICS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrRuleUniqueStatisticsRecord>> getKeys() {
        return Arrays.<UniqueKey<HrRuleUniqueStatisticsRecord>>asList(Keys.KEY_HR_RULE_UNIQUE_STATISTICS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatistics as(String alias) {
        return new HrRuleUniqueStatistics(alias, this);
    }

    /**
     * Rename this table
     */
    public HrRuleUniqueStatistics rename(String name) {
        return new HrRuleUniqueStatistics(name, null);
    }
}
