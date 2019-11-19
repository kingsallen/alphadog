/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChannelCategoryRecord;

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
 * 渠道类型表,1: 招聘网站,2 微信招聘 3 内部推荐 4 猎头公司 5 内部自有 6 其他渠道
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrChannelCategory extends TableImpl<HrChannelCategoryRecord> {

    private static final long serialVersionUID = -716105088;

    /**
     * The reference instance of <code>hrdb.hr_channel_category</code>
     */
    public static final HrChannelCategory HR_CHANNEL_CATEGORY = new HrChannelCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrChannelCategoryRecord> getRecordType() {
        return HrChannelCategoryRecord.class;
    }

    /**
     * The column <code>hrdb.hr_channel_category.id</code>. 主键,渠道id
     */
    public final TableField<HrChannelCategoryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主键,渠道id");

    /**
     * The column <code>hrdb.hr_channel_category.name</code>. 渠道类型名称
     */
    public final TableField<HrChannelCategoryRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "渠道类型名称");

    /**
     * Create a <code>hrdb.hr_channel_category</code> table reference
     */
    public HrChannelCategory() {
        this("hr_channel_category", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_channel_category</code> table reference
     */
    public HrChannelCategory(String alias) {
        this(alias, HR_CHANNEL_CATEGORY);
    }

    private HrChannelCategory(String alias, Table<HrChannelCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrChannelCategory(String alias, Table<HrChannelCategoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "渠道类型表,1: 招聘网站,2 微信招聘 3 内部推荐 4 猎头公司 5 内部自有 6 其他渠道");
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
    public Identity<HrChannelCategoryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_CHANNEL_CATEGORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrChannelCategoryRecord> getPrimaryKey() {
        return Keys.KEY_HR_CHANNEL_CATEGORY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrChannelCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<HrChannelCategoryRecord>>asList(Keys.KEY_HR_CHANNEL_CATEGORY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChannelCategory as(String alias) {
        return new HrChannelCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrChannelCategory rename(String name) {
        return new HrChannelCategory(name, null);
    }
}
