/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.records;


import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationGroup;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 管理员通知群组
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAdminnotificationGroupRecord extends UpdatableRecordImpl<ConfigAdminnotificationGroupRecord> implements Record3<Integer, String, Timestamp> {

    private static final long serialVersionUID = -1340000178;

    /**
     * Setter for <code>configdb.config_adminnotification_group.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>configdb.config_adminnotification_group.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>configdb.config_adminnotification_group.name</code>. 群组名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>configdb.config_adminnotification_group.name</code>. 群组名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>configdb.config_adminnotification_group.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>configdb.config_adminnotification_group.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Timestamp> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Timestamp> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP.CREATE_TIME;
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationGroupRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationGroupRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationGroupRecord value3(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigAdminnotificationGroupRecord values(Integer value1, String value2, Timestamp value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ConfigAdminnotificationGroupRecord
     */
    public ConfigAdminnotificationGroupRecord() {
        super(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP);
    }

    /**
     * Create a detached, initialised ConfigAdminnotificationGroupRecord
     */
    public ConfigAdminnotificationGroupRecord(Integer id, String name, Timestamp createTime) {
        super(ConfigAdminnotificationGroup.CONFIG_ADMINNOTIFICATION_GROUP);

        set(0, id);
        set(1, name);
        set(2, createTime);
    }
}
