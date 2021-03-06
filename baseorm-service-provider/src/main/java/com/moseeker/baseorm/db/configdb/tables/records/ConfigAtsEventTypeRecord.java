/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.records;


import com.moseeker.baseorm.db.configdb.tables.ConfigAtsEventType;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 事件状态管理表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAtsEventTypeRecord extends UpdatableRecordImpl<ConfigAtsEventTypeRecord> implements Record8<Integer, Byte, String, Byte, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -531350779;

    /**
     * Setter for <code>configdb.config_ats_event_type.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.event_type</code>. 事件类型
     */
    public void setEventType(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.event_type</code>. 事件类型
     */
    public Byte getEventType() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.event_name</code>. 事件名称
     */
    public void setEventName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.event_name</code>. 事件名称
     */
    public String getEventName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.status_code</code>. 状态code
     */
    public void setStatusCode(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.status_code</code>. 状态code
     */
    public Byte getStatusCode() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.status_filter_text</code>. 状态显示文案（筛选）
     */
    public void setStatusFilterText(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.status_filter_text</code>. 状态显示文案（筛选）
     */
    public String getStatusFilterText() {
        return (String) get(4);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.status_list_text</code>. 状态显示文案（列表）
     */
    public void setStatusListText(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.status_list_text</code>. 状态显示文案（列表）
     */
    public String getStatusListText() {
        return (String) get(5);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>configdb.config_ats_event_type.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>configdb.config_ats_event_type.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Byte, String, Byte, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Byte, String, Byte, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.EVENT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.EVENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.STATUS_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.STATUS_FILTER_TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.STATUS_LIST_TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value2() {
        return getEventType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getEventName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getStatusCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getStatusFilterText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getStatusListText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value2(Byte value) {
        setEventType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value3(String value) {
        setEventName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value4(Byte value) {
        setStatusCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value5(String value) {
        setStatusFilterText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value6(String value) {
        setStatusListText(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAtsEventTypeRecord values(Integer value1, Byte value2, String value3, Byte value4, String value5, String value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ConfigAtsEventTypeRecord
     */
    public ConfigAtsEventTypeRecord() {
        super(ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE);
    }

    /**
     * Create a detached, initialised ConfigAtsEventTypeRecord
     */
    public ConfigAtsEventTypeRecord(Integer id, Byte eventType, String eventName, Byte statusCode, String statusFilterText, String statusListText, Timestamp createTime, Timestamp updateTime) {
        super(ConfigAtsEventType.CONFIG_ATS_EVENT_TYPE);

        set(0, id);
        set(1, eventType);
        set(2, eventName);
        set(3, statusCode);
        set(4, statusFilterText);
        set(5, statusListText);
        set(6, createTime);
        set(7, updateTime);
    }
}
