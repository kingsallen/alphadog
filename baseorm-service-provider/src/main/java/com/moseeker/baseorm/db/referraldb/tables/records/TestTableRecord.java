/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.records;


import com.moseeker.baseorm.db.referraldb.tables.TestTable;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 测试
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TestTableRecord extends UpdatableRecordImpl<TestTableRecord> implements Record5<Integer, String, Integer, Integer, Integer> {

    private static final long serialVersionUID = -326407289;

    /**
     * Setter for <code>referraldb.test_table.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>referraldb.test_table.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>referraldb.test_table.employeeid</code>. 员工ID
     */
    public void setEmployeeid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>referraldb.test_table.employeeid</code>. 员工ID
     */
    public String getEmployeeid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>referraldb.test_table.company_id</code>.
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>referraldb.test_table.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>referraldb.test_table.role_id</code>. sys_role.id
     */
    public void setRoleId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>referraldb.test_table.role_id</code>. sys_role.id
     */
    public Integer getRoleId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>referraldb.test_table.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public void setWxuserId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>referraldb.test_table.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public Integer getWxuserId() {
        return (Integer) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, Integer, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, Integer, Integer, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return TestTable.TEST_TABLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TestTable.TEST_TABLE.EMPLOYEEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return TestTable.TEST_TABLE.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return TestTable.TEST_TABLE.ROLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return TestTable.TEST_TABLE.WXUSER_ID;
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
        return getEmployeeid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getRoleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getWxuserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestTableRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestTableRecord value2(String value) {
        setEmployeeid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestTableRecord value3(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestTableRecord value4(Integer value) {
        setRoleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestTableRecord value5(Integer value) {
        setWxuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestTableRecord values(Integer value1, String value2, Integer value3, Integer value4, Integer value5) {
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
     * Create a detached TestTableRecord
     */
    public TestTableRecord() {
        super(TestTable.TEST_TABLE);
    }

    /**
     * Create a detached, initialised TestTableRecord
     */
    public TestTableRecord(Integer id, String employeeid, Integer companyId, Integer roleId, Integer wxuserId) {
        super(TestTable.TEST_TABLE);

        set(0, id);
        set(1, employeeid);
        set(2, companyId);
        set(3, roleId);
        set(4, wxuserId);
    }
}
