/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserSysAuthGroup;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * sysplat用户权限
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserSysAuthGroupRecord extends UpdatableRecordImpl<UserSysAuthGroupRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = -1088803237;

    /**
     * Setter for <code>userdb.user_sys_auth_group.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_sys_auth_group.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_sys_auth_group.name</code>. 权限分组名
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_sys_auth_group.name</code>. 权限分组名
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>userdb.user_sys_auth_group.authcode</code>. 权限二进制值的十进制表示
     */
    public void setAuthcode(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_sys_auth_group.authcode</code>. 权限二进制值的十进制表示
     */
    public Integer getAuthcode() {
        return (Integer) get(2);
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
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserSysAuthGroup.USER_SYS_AUTH_GROUP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return UserSysAuthGroup.USER_SYS_AUTH_GROUP.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserSysAuthGroup.USER_SYS_AUTH_GROUP.AUTHCODE;
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
    public Integer value3() {
        return getAuthcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSysAuthGroupRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSysAuthGroupRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSysAuthGroupRecord value3(Integer value) {
        setAuthcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSysAuthGroupRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserSysAuthGroupRecord
     */
    public UserSysAuthGroupRecord() {
        super(UserSysAuthGroup.USER_SYS_AUTH_GROUP);
    }

    /**
     * Create a detached, initialised UserSysAuthGroupRecord
     */
    public UserSysAuthGroupRecord(Integer id, String name, Integer authcode) {
        super(UserSysAuthGroup.USER_SYS_AUTH_GROUP);

        set(0, id);
        set(1, name);
        set(2, authcode);
    }
}
