/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationChannelRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAdminnotificationChannel extends TableImpl<ConfigAdminnotificationChannelRecord> {

    private static final long serialVersionUID = -620682041;

    /**
     * The reference instance of <code>configdb.config_adminnotification_channel</code>
     */
    public static final ConfigAdminnotificationChannel CONFIG_ADMINNOTIFICATION_CHANNEL = new ConfigAdminnotificationChannel();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigAdminnotificationChannelRecord> getRecordType() {
        return ConfigAdminnotificationChannelRecord.class;
    }

    /**
     * The column <code>configdb.config_adminnotification_channel.id</code>.
     */
    public final TableField<ConfigAdminnotificationChannelRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>configdb.config_adminnotification_channel.envent_id</code>.
     */
    public final TableField<ConfigAdminnotificationChannelRecord, Integer> ENVENT_ID = createField("envent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>configdb.config_adminnotification_channel.channel</code>.
     */
    public final TableField<ConfigAdminnotificationChannelRecord, String> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "");

    /**
     * The column <code>configdb.config_adminnotification_channel.create_time</code>.
     */
    public final TableField<ConfigAdminnotificationChannelRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>configdb.config_adminnotification_channel</code> table reference
     */
    public ConfigAdminnotificationChannel() {
        this("config_adminnotification_channel", null);
    }

    /**
     * Create an aliased <code>configdb.config_adminnotification_channel</code> table reference
     */
    public ConfigAdminnotificationChannel(String alias) {
        this(alias, CONFIG_ADMINNOTIFICATION_CHANNEL);
    }

    private ConfigAdminnotificationChannel(String alias, Table<ConfigAdminnotificationChannelRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigAdminnotificationChannel(String alias, Table<ConfigAdminnotificationChannelRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
    public Identity<ConfigAdminnotificationChannelRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CONFIG_ADMINNOTIFICATION_CHANNEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigAdminnotificationChannelRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_ADMINNOTIFICATION_CHANNEL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigAdminnotificationChannelRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigAdminnotificationChannelRecord>>asList(Keys.KEY_CONFIG_ADMINNOTIFICATION_CHANNEL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationChannel as(String alias) {
        return new ConfigAdminnotificationChannel(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfigAdminnotificationChannel rename(String name) {
        return new ConfigAdminnotificationChannel(name, null);
    }
}
