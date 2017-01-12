/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;

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
 * 管理员通知事件列表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAdminnotificationEvents extends TableImpl<ConfigAdminnotificationEventsRecord> {

    private static final long serialVersionUID = 557724197;

    /**
     * The reference instance of <code>configdb.config_adminnotification_events</code>
     */
    public static final ConfigAdminnotificationEvents CONFIG_ADMINNOTIFICATION_EVENTS = new ConfigAdminnotificationEvents();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigAdminnotificationEventsRecord> getRecordType() {
        return ConfigAdminnotificationEventsRecord.class;
    }

    /**
     * The column <code>configdb.config_adminnotification_events.id</code>.
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>configdb.config_adminnotification_events.project_appid</code>. 项目appid
     */
    public final TableField<ConfigAdminnotificationEventsRecord, String> PROJECT_APPID = createField("project_appid", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "项目appid");

    /**
     * The column <code>configdb.config_adminnotification_events.event_key</code>. 事件标识符，大写英文
     */
    public final TableField<ConfigAdminnotificationEventsRecord, String> EVENT_KEY = createField("event_key", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "事件标识符，大写英文");

    /**
     * The column <code>configdb.config_adminnotification_events.event_name</code>. 事件名称
     */
    public final TableField<ConfigAdminnotificationEventsRecord, String> EVENT_NAME = createField("event_name", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "事件名称");

    /**
     * The column <code>configdb.config_adminnotification_events.event_desc</code>. 事件描述
     */
    public final TableField<ConfigAdminnotificationEventsRecord, String> EVENT_DESC = createField("event_desc", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false), this, "事件描述");

    /**
     * The column <code>configdb.config_adminnotification_events.threshold_value</code>. 触发几次后通知
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Integer> THRESHOLD_VALUE = createField("threshold_value", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "触发几次后通知");

    /**
     * The column <code>configdb.config_adminnotification_events.threshold_interval</code>. 单位秒，0表示不限制，  thresholdinterval和thresholdvalue搭配使用，表达 每分钟超过5次 报警。
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Integer> THRESHOLD_INTERVAL = createField("threshold_interval", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "单位秒，0表示不限制，  thresholdinterval和thresholdvalue搭配使用，表达 每分钟超过5次 报警。");

    /**
     * The column <code>configdb.config_adminnotification_events.enable_notifyby_email</code>. 是否email通知， 1是 0否
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Byte> ENABLE_NOTIFYBY_EMAIL = createField("enable_notifyby_email", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否email通知， 1是 0否");

    /**
     * The column <code>configdb.config_adminnotification_events.enable_notifyby_sms</code>. 是否短信通知， 1是 0否
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Byte> ENABLE_NOTIFYBY_SMS = createField("enable_notifyby_sms", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否短信通知， 1是 0否");

    /**
     * The column <code>configdb.config_adminnotification_events.enable_notifyby_wechattemplatemessage</code>. 是否微信模板消息通知， 1是 0否
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Byte> ENABLE_NOTIFYBY_WECHATTEMPLATEMESSAGE = createField("enable_notifyby_wechattemplatemessage", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否微信模板消息通知， 1是 0否");

    /**
     * The column <code>configdb.config_adminnotification_events.groupid</code>. 发送给哪个组
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Integer> GROUPID = createField("groupid", org.jooq.impl.SQLDataType.INTEGER, this, "发送给哪个组");

    /**
     * The column <code>configdb.config_adminnotification_events.create_time</code>.
     */
    public final TableField<ConfigAdminnotificationEventsRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>configdb.config_adminnotification_events</code> table reference
     */
    public ConfigAdminnotificationEvents() {
        this("config_adminnotification_events", null);
    }

    /**
     * Create an aliased <code>configdb.config_adminnotification_events</code> table reference
     */
    public ConfigAdminnotificationEvents(String alias) {
        this(alias, CONFIG_ADMINNOTIFICATION_EVENTS);
    }

    private ConfigAdminnotificationEvents(String alias, Table<ConfigAdminnotificationEventsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigAdminnotificationEvents(String alias, Table<ConfigAdminnotificationEventsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "管理员通知事件列表");
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
    public UniqueKey<ConfigAdminnotificationEventsRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_ADMINNOTIFICATION_EVENTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigAdminnotificationEventsRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigAdminnotificationEventsRecord>>asList(Keys.KEY_CONFIG_ADMINNOTIFICATION_EVENTS_PRIMARY, Keys.KEY_CONFIG_ADMINNOTIFICATION_EVENTS_EVENT_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationEvents as(String alias) {
        return new ConfigAdminnotificationEvents(alias, this);
    }

    /**
     * Rename this table
     */
    public ConfigAdminnotificationEvents rename(String name) {
        return new ConfigAdminnotificationEvents(name, null);
    }
}
