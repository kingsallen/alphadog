/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 管理员通知联系人
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAdminnotificationMembers extends TableImpl<ConfigAdminnotificationMembersRecord> {

    private static final long serialVersionUID = 1999573203;

    /**
     * The reference instance of <code>configdb.config_adminnotification_members</code>
     */
    public static final ConfigAdminnotificationMembers CONFIG_ADMINNOTIFICATION_MEMBERS = new ConfigAdminnotificationMembers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigAdminnotificationMembersRecord> getRecordType() {
        return ConfigAdminnotificationMembersRecord.class;
    }

    /**
     * The column <code>configdb.config_adminnotification_members.id</code>.
     */
    public final TableField<ConfigAdminnotificationMembersRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>configdb.config_adminnotification_members.name</code>. 姓名
     */
    public final TableField<ConfigAdminnotificationMembersRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "姓名");

    /**
     * The column <code>configdb.config_adminnotification_members.mobilephone</code>. 接收通知的手机
     */
    public final TableField<ConfigAdminnotificationMembersRecord, String> MOBILEPHONE = createField("mobilephone", org.jooq.impl.SQLDataType.VARCHAR.length(15), this, "接收通知的手机");

    /**
     * The column <code>configdb.config_adminnotification_members.wechatopenid</code>. 接收通知的微信openid
     */
    public final TableField<ConfigAdminnotificationMembersRecord, String> WECHATOPENID = createField("wechatopenid", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "接收通知的微信openid");

    /**
     * The column <code>configdb.config_adminnotification_members.email</code>. 接收通知的email
     */
    public final TableField<ConfigAdminnotificationMembersRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "接收通知的email");

    /**
     * The column <code>configdb.config_adminnotification_members.status</code>. 1 有效 0 无效
     */
    public final TableField<ConfigAdminnotificationMembersRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "1 有效 0 无效");

    /**
     * The column <code>configdb.config_adminnotification_members.create_time</code>.
     */
    public final TableField<ConfigAdminnotificationMembersRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>configdb.config_adminnotification_members</code> table reference
     */
    public ConfigAdminnotificationMembers() {
        this("config_adminnotification_members", null);
    }

    /**
     * Create an aliased <code>configdb.config_adminnotification_members</code> table reference
     */
    public ConfigAdminnotificationMembers(String alias) {
        this(alias, CONFIG_ADMINNOTIFICATION_MEMBERS);
    }

    private ConfigAdminnotificationMembers(String alias, Table<ConfigAdminnotificationMembersRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigAdminnotificationMembers(String alias, Table<ConfigAdminnotificationMembersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "管理员通知联系人");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Configdb.CONFIGDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigAdminnotificationMembersRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigAdminnotificationMembersRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigAdminnotificationMembersRecord>>asList(Keys.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_PRIMARY, Keys.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_NAME, Keys.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_MOBILEPHONE, Keys.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_WECHATOPENID, Keys.KEY_CONFIG_ADMINNOTIFICATION_MEMBERS_EMAIL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationMembers as(String alias) {
        return new ConfigAdminnotificationMembers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfigAdminnotificationMembers rename(String name) {
        return new ConfigAdminnotificationMembers(name, null);
    }
}
