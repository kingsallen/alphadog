/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables.records;


import com.moseeker.baseorm.db.analytics.tables.StmPvPid;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmPvPidRecord extends TableRecordImpl<StmPvPidRecord> implements Record5<Long, String, Timestamp, String, String> {

    private static final long serialVersionUID = -326565857;

    /**
     * Setter for <code>analytics.stm_pv_pid.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>analytics.stm_pv_pid.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>analytics.stm_pv_pid.ab_group</code>.
     */
    public void setAbGroup(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>analytics.stm_pv_pid.ab_group</code>.
     */
    public String getAbGroup() {
        return (String) get(1);
    }

    /**
     * Setter for <code>analytics.stm_pv_pid.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>analytics.stm_pv_pid.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>analytics.stm_pv_pid.ip</code>.
     */
    public void setIp(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>analytics.stm_pv_pid.ip</code>.
     */
    public String getIp() {
        return (String) get(3);
    }

    /**
     * Setter for <code>analytics.stm_pv_pid.pid</code>.
     */
    public void setPid(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>analytics.stm_pv_pid.pid</code>.
     */
    public String getPid() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, Timestamp, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, Timestamp, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return StmPvPid.STM_PV_PID.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return StmPvPid.STM_PV_PID.AB_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return StmPvPid.STM_PV_PID.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return StmPvPid.STM_PV_PID.IP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return StmPvPid.STM_PV_PID.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getAbGroup();
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
    public String value4() {
        return getIp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmPvPidRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmPvPidRecord value2(String value) {
        setAbGroup(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmPvPidRecord value3(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmPvPidRecord value4(String value) {
        setIp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmPvPidRecord value5(String value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmPvPidRecord values(Long value1, String value2, Timestamp value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StmPvPidRecord
     */
    public StmPvPidRecord() {
        super(StmPvPid.STM_PV_PID);
    }

    /**
     * Create a detached, initialised StmPvPidRecord
     */
    public StmPvPidRecord(Long id, String abGroup, Timestamp createTime, String ip, String pid) {
        super(StmPvPid.STM_PV_PID);

        set(0, id);
        set(1, abGroup);
        set(2, createTime);
        set(3, ip);
        set(4, pid);
    }
}
