/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserWxViewer;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户浏览者记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserWxViewerRecord extends UpdatableRecordImpl<UserWxViewerRecord> implements Record4<Integer, Integer, String, Integer> {

    private static final long serialVersionUID = 1463240278;

    /**
     * Setter for <code>userdb.user_wx_viewer.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_wx_viewer.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_wx_viewer.sysuser_id</code>.
     */
    public void setSysuserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_wx_viewer.sysuser_id</code>.
     */
    public Integer getSysuserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_wx_viewer.idcode</code>.
     */
    public void setIdcode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_wx_viewer.idcode</code>.
     */
    public String getIdcode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>userdb.user_wx_viewer.client_type</code>.
     */
    public void setClientType(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_wx_viewer.client_type</code>.
     */
    public Integer getClientType() {
        return (Integer) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserWxViewer.USER_WX_VIEWER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserWxViewer.USER_WX_VIEWER.SYSUSER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserWxViewer.USER_WX_VIEWER.IDCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserWxViewer.USER_WX_VIEWER.CLIENT_TYPE;
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
        return getSysuserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getIdcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getClientType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxViewerRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxViewerRecord value2(Integer value) {
        setSysuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxViewerRecord value3(String value) {
        setIdcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxViewerRecord value4(Integer value) {
        setClientType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxViewerRecord values(Integer value1, Integer value2, String value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserWxViewerRecord
     */
    public UserWxViewerRecord() {
        super(UserWxViewer.USER_WX_VIEWER);
    }

    /**
     * Create a detached, initialised UserWxViewerRecord
     */
    public UserWxViewerRecord(Integer id, Integer sysuserId, String idcode, Integer clientType) {
        super(UserWxViewer.USER_WX_VIEWER);

        set(0, id);
        set(1, sysuserId);
        set(2, idcode);
        set(3, clientType);
    }
}
