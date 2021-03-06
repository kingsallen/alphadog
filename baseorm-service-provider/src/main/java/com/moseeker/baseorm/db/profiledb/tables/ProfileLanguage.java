/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables;


import com.moseeker.baseorm.db.profiledb.Keys;
import com.moseeker.baseorm.db.profiledb.Profiledb;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;

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
 * Profile的语言
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileLanguage extends TableImpl<ProfileLanguageRecord> {

    private static final long serialVersionUID = -1014382926;

    /**
     * The reference instance of <code>profiledb.profile_language</code>
     */
    public static final ProfileLanguage PROFILE_LANGUAGE = new ProfileLanguage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileLanguageRecord> getRecordType() {
        return ProfileLanguageRecord.class;
    }

    /**
     * The column <code>profiledb.profile_language.id</code>. 主key
     */
    public final TableField<ProfileLanguageRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>profiledb.profile_language.profile_id</code>. profile.id
     */
    public final TableField<ProfileLanguageRecord, Integer> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "profile.id");

    /**
     * The column <code>profiledb.profile_language.name</code>. 语言
     */
    public final TableField<ProfileLanguageRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "语言");

    /**
     * The column <code>profiledb.profile_language.level</code>. 掌握程度, dict_constant.parent_code:3108
     */
    public final TableField<ProfileLanguageRecord, Byte> LEVEL = createField("level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "掌握程度, dict_constant.parent_code:3108");

    /**
     * The column <code>profiledb.profile_language.create_time</code>. 创建时间
     */
    public final TableField<ProfileLanguageRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>profiledb.profile_language.update_time</code>. 更新时间
     */
    public final TableField<ProfileLanguageRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>profiledb.profile_language</code> table reference
     */
    public ProfileLanguage() {
        this("profile_language", null);
    }

    /**
     * Create an aliased <code>profiledb.profile_language</code> table reference
     */
    public ProfileLanguage(String alias) {
        this(alias, PROFILE_LANGUAGE);
    }

    private ProfileLanguage(String alias, Table<ProfileLanguageRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProfileLanguage(String alias, Table<ProfileLanguageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Profile的语言");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Profiledb.PROFILEDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProfileLanguageRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PROFILE_LANGUAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProfileLanguageRecord> getPrimaryKey() {
        return Keys.KEY_PROFILE_LANGUAGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProfileLanguageRecord>> getKeys() {
        return Arrays.<UniqueKey<ProfileLanguageRecord>>asList(Keys.KEY_PROFILE_LANGUAGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileLanguage as(String alias) {
        return new ProfileLanguage(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileLanguage rename(String name) {
        return new ProfileLanguage(name, null);
    }
}
