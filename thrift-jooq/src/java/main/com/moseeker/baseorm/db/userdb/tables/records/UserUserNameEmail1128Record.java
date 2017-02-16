/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserUserNameEmail1128;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserUserNameEmail1128Record extends UpdatableRecordImpl<UserUserNameEmail1128Record> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = -277908385;

    /**
     * Setter for <code>userdb.user_user_name_email1128.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_user_name_email1128.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_user_name_email1128.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_user_name_email1128.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>userdb.user_user_name_email1128.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_user_name_email1128.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
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
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserUserNameEmail1128.USER_USER_NAME_EMAIL1128.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return UserUserNameEmail1128.USER_USER_NAME_EMAIL1128.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserUserNameEmail1128.USER_USER_NAME_EMAIL1128.EMAIL;
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
    public String value3() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserUserNameEmail1128Record value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserUserNameEmail1128Record value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserUserNameEmail1128Record value3(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserUserNameEmail1128Record values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserUserNameEmail1128Record
     */
    public UserUserNameEmail1128Record() {
        super(UserUserNameEmail1128.USER_USER_NAME_EMAIL1128);
    }

    /**
     * Create a detached, initialised UserUserNameEmail1128Record
     */
    public UserUserNameEmail1128Record(Integer id, String name, String email) {
        super(UserUserNameEmail1128.USER_USER_NAME_EMAIL1128);

        set(0, id);
        set(1, name);
        set(2, email);
    }
}
