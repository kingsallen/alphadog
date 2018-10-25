/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistorydbProfileAttachmentRecord;

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
 * Profile的简历附件
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistorydbProfileAttachment extends TableImpl<HistorydbProfileAttachmentRecord> {

    private static final long serialVersionUID = 1936207812;

    /**
     * The reference instance of <code>historydb.historydb_profile_attachment</code>
     */
    public static final HistorydbProfileAttachment HISTORYDB_PROFILE_ATTACHMENT = new HistorydbProfileAttachment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistorydbProfileAttachmentRecord> getRecordType() {
        return HistorydbProfileAttachmentRecord.class;
    }

    /**
     * The column <code>historydb.historydb_profile_attachment.id</code>. 主key
     */
    public final TableField<HistorydbProfileAttachmentRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>historydb.historydb_profile_attachment.profile_id</code>. profile.id
     */
    public final TableField<HistorydbProfileAttachmentRecord, Integer> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "profile.id");

    /**
     * The column <code>historydb.historydb_profile_attachment.name</code>. 附件名称
     */
    public final TableField<HistorydbProfileAttachmentRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "附件名称");

    /**
     * The column <code>historydb.historydb_profile_attachment.path</code>. 附件存储路径
     */
    public final TableField<HistorydbProfileAttachmentRecord, String> PATH = createField("path", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "附件存储路径");

    /**
     * The column <code>historydb.historydb_profile_attachment.description</code>. 附件描述
     */
    public final TableField<HistorydbProfileAttachmentRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "附件描述");

    /**
     * The column <code>historydb.historydb_profile_attachment.create_time</code>. 创建时间
     */
    public final TableField<HistorydbProfileAttachmentRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>historydb.historydb_profile_attachment.update_time</code>. 更新时间
     */
    public final TableField<HistorydbProfileAttachmentRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>historydb.historydb_profile_attachment</code> table reference
     */
    public HistorydbProfileAttachment() {
        this("historydb_profile_attachment", null);
    }

    /**
     * Create an aliased <code>historydb.historydb_profile_attachment</code> table reference
     */
    public HistorydbProfileAttachment(String alias) {
        this(alias, HISTORYDB_PROFILE_ATTACHMENT);
    }

    private HistorydbProfileAttachment(String alias, Table<HistorydbProfileAttachmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistorydbProfileAttachment(String alias, Table<HistorydbProfileAttachmentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Profile的简历附件");
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
    public Identity<HistorydbProfileAttachmentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HISTORYDB_PROFILE_ATTACHMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistorydbProfileAttachmentRecord> getPrimaryKey() {
        return Keys.KEY_HISTORYDB_PROFILE_ATTACHMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistorydbProfileAttachmentRecord>> getKeys() {
        return Arrays.<UniqueKey<HistorydbProfileAttachmentRecord>>asList(Keys.KEY_HISTORYDB_PROFILE_ATTACHMENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistorydbProfileAttachment as(String alias) {
        return new HistorydbProfileAttachment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HistorydbProfileAttachment rename(String name) {
        return new HistorydbProfileAttachment(name, null);
    }
}
