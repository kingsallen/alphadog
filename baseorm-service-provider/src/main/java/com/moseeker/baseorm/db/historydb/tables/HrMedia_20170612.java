/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HrMedia_20170612Record;

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
 * 模板媒体表，存储模板渲染的媒体信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrMedia_20170612 extends TableImpl<HrMedia_20170612Record> {

    private static final long serialVersionUID = 711290452;

    /**
     * The reference instance of <code>historydb.hr_media_20170612</code>
     */
    public static final HrMedia_20170612 HR_MEDIA_20170612 = new HrMedia_20170612();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrMedia_20170612Record> getRecordType() {
        return HrMedia_20170612Record.class;
    }

    /**
     * The column <code>historydb.hr_media_20170612.id</code>.
     */
    public final TableField<HrMedia_20170612Record, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.hr_media_20170612.longtext</code>. 描述
     */
    public final TableField<HrMedia_20170612Record, String> LONGTEXT = createField("longtext", org.jooq.impl.SQLDataType.VARCHAR.length(2048).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "描述");

    /**
     * The column <code>historydb.hr_media_20170612.attrs</code>. 客户属性，可选 字段
     */
    public final TableField<HrMedia_20170612Record, String> ATTRS = createField("attrs", org.jooq.impl.SQLDataType.VARCHAR.length(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "客户属性，可选 字段");

    /**
     * The column <code>historydb.hr_media_20170612.title</code>. 模板名称
     */
    public final TableField<HrMedia_20170612Record, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板名称");

    /**
     * The column <code>historydb.hr_media_20170612.sub_title</code>. 模板子名称
     */
    public final TableField<HrMedia_20170612Record, String> SUB_TITLE = createField("sub_title", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板子名称");

    /**
     * The column <code>historydb.hr_media_20170612.link</code>. 模板链接
     */
    public final TableField<HrMedia_20170612Record, String> LINK = createField("link", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板链接");

    /**
     * The column <code>historydb.hr_media_20170612.res_id</code>. 资源hr_resource.id
     */
    public final TableField<HrMedia_20170612Record, Integer> RES_ID = createField("res_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "资源hr_resource.id");

    /**
     * Create a <code>historydb.hr_media_20170612</code> table reference
     */
    public HrMedia_20170612() {
        this("hr_media_20170612", null);
    }

    /**
     * Create an aliased <code>historydb.hr_media_20170612</code> table reference
     */
    public HrMedia_20170612(String alias) {
        this(alias, HR_MEDIA_20170612);
    }

    private HrMedia_20170612(String alias, Table<HrMedia_20170612Record> aliased) {
        this(alias, aliased, null);
    }

    private HrMedia_20170612(String alias, Table<HrMedia_20170612Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "模板媒体表，存储模板渲染的媒体信息");
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
    public Identity<HrMedia_20170612Record, Integer> getIdentity() {
        return Keys.IDENTITY_HR_MEDIA_20170612;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrMedia_20170612Record> getPrimaryKey() {
        return Keys.KEY_HR_MEDIA_20170612_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrMedia_20170612Record>> getKeys() {
        return Arrays.<UniqueKey<HrMedia_20170612Record>>asList(Keys.KEY_HR_MEDIA_20170612_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612 as(String alias) {
        return new HrMedia_20170612(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrMedia_20170612 rename(String name) {
        return new HrMedia_20170612(name, null);
    }
}
