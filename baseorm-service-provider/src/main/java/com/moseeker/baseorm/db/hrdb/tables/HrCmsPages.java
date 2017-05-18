/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsPagesRecord;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.jooq.*;
import org.jooq.impl.TableImpl;


/**
 * 微信端新jd配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCmsPages extends TableImpl<HrCmsPagesRecord> {

    private static final long serialVersionUID = 1456956051;

    /**
     * The reference instance of <code>hrdb.hr_cms_pages</code>
     */
    public static final HrCmsPages HR_CMS_PAGES = new HrCmsPages();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrCmsPagesRecord> getRecordType() {
        return HrCmsPagesRecord.class;
    }

    /**
     * The column <code>hrdb.hr_cms_pages.id</code>.
     */
    public final TableField<HrCmsPagesRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_cms_pages.config_id</code>.
     */
    public final TableField<HrCmsPagesRecord, Integer> CONFIG_ID = createField("config_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>hrdb.hr_cms_pages.type</code>. 0,默认值暂无意义，1为企业首页(config_id为company_id)2，团队详情（config_id为team_id） ，3，详情职位详情(config_id为team_id)
     */
    public final TableField<HrCmsPagesRecord, Integer> TYPE = createField("type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0,默认值暂无意义，1为企业首页(config_id为company_id)2，团队详情（config_id为team_id） ，3，详情职位详情(config_id为team_id)");

    /**
     * The column <code>hrdb.hr_cms_pages.disable</code>. 状态 0 是有效 1是无效
     */
    public final TableField<HrCmsPagesRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态 0 是有效 1是无效");

    /**
     * The column <code>hrdb.hr_cms_pages.create_time</code>.
     */
    public final TableField<HrCmsPagesRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_cms_pages.update_time</code>.
     */
    public final TableField<HrCmsPagesRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>hrdb.hr_cms_pages</code> table reference
     */
    public HrCmsPages() {
        this("hr_cms_pages", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_cms_pages</code> table reference
     */
    public HrCmsPages(String alias) {
        this(alias, HR_CMS_PAGES);
    }

    private HrCmsPages(String alias, Table<HrCmsPagesRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrCmsPages(String alias, Table<HrCmsPagesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "微信端新jd配置表");
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
    public Identity<HrCmsPagesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_CMS_PAGES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrCmsPagesRecord> getPrimaryKey() {
        return Keys.KEY_HR_CMS_PAGES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrCmsPagesRecord>> getKeys() {
        return Arrays.<UniqueKey<HrCmsPagesRecord>>asList(Keys.KEY_HR_CMS_PAGES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCmsPages as(String alias) {
        return new HrCmsPages(alias, this);
    }

    /**
     * Rename this table
     */
    public HrCmsPages rename(String name) {
        return new HrCmsPages(name, null);
    }
}
