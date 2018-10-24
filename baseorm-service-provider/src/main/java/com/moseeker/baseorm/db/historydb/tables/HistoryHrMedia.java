/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryHrMediaRecord;

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
public class HistoryHrMedia extends TableImpl<HistoryHrMediaRecord> {

    private static final long serialVersionUID = -534951918;

    /**
     * The reference instance of <code>historydb.history_hr_media</code>
     */
    public static final HistoryHrMedia HISTORY_HR_MEDIA = new HistoryHrMedia();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryHrMediaRecord> getRecordType() {
        return HistoryHrMediaRecord.class;
    }

    /**
     * The column <code>historydb.history_hr_media.id</code>.
     */
    public final TableField<HistoryHrMediaRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_hr_media.longtext</code>. 描述
     */
    public final TableField<HistoryHrMediaRecord, String> LONGTEXT = createField("longtext", org.jooq.impl.SQLDataType.VARCHAR.length(2048).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "描述");

    /**
     * The column <code>historydb.history_hr_media.attrs</code>. 客户属性，可选 字段
     */
    public final TableField<HistoryHrMediaRecord, String> ATTRS = createField("attrs", org.jooq.impl.SQLDataType.VARCHAR.length(1024).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "客户属性，可选 字段");

    /**
     * The column <code>historydb.history_hr_media.title</code>. 模板名称
     */
    public final TableField<HistoryHrMediaRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板名称");

    /**
     * The column <code>historydb.history_hr_media.sub_title</code>. 模板子名称
     */
    public final TableField<HistoryHrMediaRecord, String> SUB_TITLE = createField("sub_title", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板子名称");

    /**
     * The column <code>historydb.history_hr_media.link</code>. 模板链接
     */
    public final TableField<HistoryHrMediaRecord, String> LINK = createField("link", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板链接");

    /**
     * The column <code>historydb.history_hr_media.res_id</code>. 资源hr_resource.id
     */
    public final TableField<HistoryHrMediaRecord, Integer> RES_ID = createField("res_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "资源hr_resource.id");

    /**
     * Create a <code>historydb.history_hr_media</code> table reference
     */
    public HistoryHrMedia() {
        this("history_hr_media", null);
    }

    /**
     * Create an aliased <code>historydb.history_hr_media</code> table reference
     */
    public HistoryHrMedia(String alias) {
        this(alias, HISTORY_HR_MEDIA);
    }

    private HistoryHrMedia(String alias, Table<HistoryHrMediaRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryHrMedia(String alias, Table<HistoryHrMediaRecord> aliased, Field<?>[] parameters) {
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
    public Identity<HistoryHrMediaRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HISTORY_HR_MEDIA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryHrMediaRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_HR_MEDIA_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryHrMediaRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryHrMediaRecord>>asList(Keys.KEY_HISTORY_HR_MEDIA_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrMedia as(String alias) {
        return new HistoryHrMedia(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HistoryHrMedia rename(String name) {
        return new HistoryHrMedia(name, null);
    }
}
