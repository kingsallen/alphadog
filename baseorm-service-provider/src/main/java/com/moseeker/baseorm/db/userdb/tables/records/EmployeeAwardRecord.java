/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.EmployeeAward;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EmployeeAwardRecord extends UpdatableRecordImpl<EmployeeAwardRecord> implements Record6<Integer, Integer, Integer, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -445163784;

    /**
     * Setter for <code>userdb.employee_award.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.employee_award.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.employee_award.company_id</code>.
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.employee_award.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.employee_award.employee_id</code>.
     */
    public void setEmployeeId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.employee_award.employee_id</code>.
     */
    public Integer getEmployeeId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.employee_award.award</code>.
     */
    public void setAward(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.employee_award.award</code>.
     */
    public String getAward() {
        return (String) get(3);
    }

    /**
     * Setter for <code>userdb.employee_award.creat_time</code>.
     */
    public void setCreatTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.employee_award.creat_time</code>.
     */
    public Timestamp getCreatTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>userdb.employee_award.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.employee_award.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, String, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, String, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return EmployeeAward.EMPLOYEE_AWARD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return EmployeeAward.EMPLOYEE_AWARD.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return EmployeeAward.EMPLOYEE_AWARD.EMPLOYEE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return EmployeeAward.EMPLOYEE_AWARD.AWARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return EmployeeAward.EMPLOYEE_AWARD.CREAT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return EmployeeAward.EMPLOYEE_AWARD.UPDATE_TIME;
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
    public Integer value2() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getEmployeeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getAward();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreatTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord value3(Integer value) {
        setEmployeeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord value4(String value) {
        setAward(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord value5(Timestamp value) {
        setCreatTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeAwardRecord values(Integer value1, Integer value2, Integer value3, String value4, Timestamp value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EmployeeAwardRecord
     */
    public EmployeeAwardRecord() {
        super(EmployeeAward.EMPLOYEE_AWARD);
    }

    /**
     * Create a detached, initialised EmployeeAwardRecord
     */
    public EmployeeAwardRecord(Integer id, Integer companyId, Integer employeeId, String award, Timestamp creatTime, Timestamp updateTime) {
        super(EmployeeAward.EMPLOYEE_AWARD);

        set(0, id);
        set(1, companyId);
        set(2, employeeId);
        set(3, award);
        set(4, creatTime);
        set(5, updateTime);
    }
}
