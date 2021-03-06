/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;

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
 * 用户设置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserSettings extends TableImpl<UserSettingsRecord> {

    private static final long serialVersionUID = 1688473624;

    /**
     * The reference instance of <code>userdb.user_settings</code>
     */
    public static final UserSettings USER_SETTINGS = new UserSettings();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSettingsRecord> getRecordType() {
        return UserSettingsRecord.class;
    }

    /**
     * The column <code>userdb.user_settings.id</code>. 主key
     */
    public final TableField<UserSettingsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>userdb.user_settings.user_id</code>. user_user.id, 用户id
     */
    public final TableField<UserSettingsRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "user_user.id, 用户id");

    /**
     * The column <code>userdb.user_settings.banner_url</code>. profile banner 的qiniu 相对url
     */
    public final TableField<UserSettingsRecord, String> BANNER_URL = createField("banner_url", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "profile banner 的qiniu 相对url");

    /**
     * The column <code>userdb.user_settings.privacy_policy</code>. 0:公开, 10:仅对hr公开, 20:完全保密
     */
    public final TableField<UserSettingsRecord, Byte> PRIVACY_POLICY = createField("privacy_policy", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:公开, 10:仅对hr公开, 20:完全保密");

    /**
     * Create a <code>userdb.user_settings</code> table reference
     */
    public UserSettings() {
        this("user_settings", null);
    }

    /**
     * Create an aliased <code>userdb.user_settings</code> table reference
     */
    public UserSettings(String alias) {
        this(alias, USER_SETTINGS);
    }

    private UserSettings(String alias, Table<UserSettingsRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSettings(String alias, Table<UserSettingsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户设置表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserSettingsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_SETTINGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserSettingsRecord> getPrimaryKey() {
        return Keys.KEY_USER_SETTINGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserSettingsRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSettingsRecord>>asList(Keys.KEY_USER_SETTINGS_PRIMARY, Keys.KEY_USER_SETTINGS_UID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSettings as(String alias) {
        return new UserSettings(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserSettings rename(String name) {
        return new UserSettings(name, null);
    }
}
