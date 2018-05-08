/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.daos;


import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationEvents;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationEventsRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 管理员通知事件列表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAdminnotificationEventsDao extends DAOImpl<ConfigAdminnotificationEventsRecord, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents, Integer> {

    /**
     * Create a new ConfigAdminnotificationEventsDao without any configuration
     */
    public ConfigAdminnotificationEventsDao() {
        super(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents.class);
    }

    /**
     * Create a new ConfigAdminnotificationEventsDao with an attached configuration
     */
    public ConfigAdminnotificationEventsDao(Configuration configuration) {
        super(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS, com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchById(Integer... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents fetchOneById(Integer value) {
        return fetchOne(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ID, value);
    }

    /**
     * Fetch records that have <code>project_appid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByProjectAppid(String... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.PROJECT_APPID, values);
    }

    /**
     * Fetch records that have <code>event_key IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByEventKey(String... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_KEY, values);
    }

    /**
     * Fetch a unique record that has <code>event_key = value</code>
     */
    public com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents fetchOneByEventKey(String value) {
        return fetchOne(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_KEY, value);
    }

    /**
     * Fetch records that have <code>event_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByEventName(String... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_NAME, values);
    }

    /**
     * Fetch records that have <code>event_desc IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByEventDesc(String... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.EVENT_DESC, values);
    }

    /**
     * Fetch records that have <code>threshold_value IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByThresholdValue(Integer... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.THRESHOLD_VALUE, values);
    }

    /**
     * Fetch records that have <code>threshold_interval IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByThresholdInterval(Integer... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.THRESHOLD_INTERVAL, values);
    }

    /**
     * Fetch records that have <code>enable_notifyby_email IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByEnableNotifybyEmail(Byte... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_EMAIL, values);
    }

    /**
     * Fetch records that have <code>enable_notifyby_sms IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByEnableNotifybySms(Byte... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_SMS, values);
    }

    /**
     * Fetch records that have <code>enable_notifyby_wechattemplatemessage IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByEnableNotifybyWechattemplatemessage(Byte... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.ENABLE_NOTIFYBY_WECHATTEMPLATEMESSAGE, values);
    }

    /**
     * Fetch records that have <code>groupid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByGroupid(Integer... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.GROUPID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.configdb.tables.pojos.ConfigAdminnotificationEvents> fetchByCreateTime(Timestamp... values) {
        return fetch(ConfigAdminnotificationEvents.CONFIG_ADMINNOTIFICATION_EVENTS.CREATE_TIME, values);
    }
}