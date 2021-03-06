/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserApp;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 应用
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAppRecord extends UpdatableRecordImpl<UserAppRecord> implements Record14<Integer, String, String, String, Byte, String, String, String, Byte, Byte, Timestamp, String, Timestamp, String> {

    private static final long serialVersionUID = 1284604175;

    /**
     * Setter for <code>userdb.user_app.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_app.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_app.app_name</code>. 应用权限名
     */
    public void setAppName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_app.app_name</code>. 应用权限名
     */
    public String getAppName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>userdb.user_app.app_desc</code>. 应用权限描述
     */
    public void setAppDesc(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_app.app_desc</code>. 应用权限描述
     */
    public String getAppDesc() {
        return (String) get(2);
    }

    /**
     * Setter for <code>userdb.user_app.ico_url</code>. 应用图标ico地址
     */
    public void setIcoUrl(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_app.ico_url</code>. 应用图标ico地址
     */
    public String getIcoUrl() {
        return (String) get(3);
    }

    /**
     * Setter for <code>userdb.user_app.app_order</code>. 应用显示顺序
     */
    public void setAppOrder(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_app.app_order</code>. 应用显示顺序
     */
    public Byte getAppOrder() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>userdb.user_app.link</code>. 老项目路由
     */
    public void setLink(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_app.link</code>. 老项目路由
     */
    public String getLink() {
        return (String) get(5);
    }

    /**
     * Setter for <code>userdb.user_app.inner_link</code>. 新项目路由
     */
    public void setInnerLink(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_app.inner_link</code>. 新项目路由
     */
    public String getInnerLink() {
        return (String) get(6);
    }

    /**
     * Setter for <code>userdb.user_app.label</code>. 多语言
     */
    public void setLabel(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_app.label</code>. 多语言
     */
    public String getLabel() {
        return (String) get(7);
    }

    /**
     * Setter for <code>userdb.user_app.app_type</code>. 应用类型 1:工作台 2:职位管理 3:招聘管理 4:微信招聘 5:内推管理 6:数据分析 7:设置中心
     */
    public void setAppType(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>userdb.user_app.app_type</code>. 应用类型 1:工作台 2:职位管理 3:招聘管理 4:微信招聘 5:内推管理 6:数据分析 7:设置中心
     */
    public Byte getAppType() {
        return (Byte) get(8);
    }

    /**
     * Setter for <code>userdb.user_app.set_type</code>. 设置类型 0:可设置  1:不可设置
     */
    public void setSetType(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>userdb.user_app.set_type</code>. 设置类型 0:可设置  1:不可设置
     */
    public Byte getSetType() {
        return (Byte) get(9);
    }

    /**
     * Setter for <code>userdb.user_app.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(10, value);
    }

    /**
     * Getter for <code>userdb.user_app.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(10);
    }

    /**
     * Setter for <code>userdb.user_app.creator</code>. 创建人
     */
    public void setCreator(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>userdb.user_app.creator</code>. 创建人
     */
    public String getCreator() {
        return (String) get(11);
    }

    /**
     * Setter for <code>userdb.user_app.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(12, value);
    }

    /**
     * Getter for <code>userdb.user_app.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(12);
    }

    /**
     * Setter for <code>userdb.user_app.updator</code>. 修改人
     */
    public void setUpdator(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>userdb.user_app.updator</code>. 修改人
     */
    public String getUpdator() {
        return (String) get(13);
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
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, Byte, String, String, String, Byte, Byte, Timestamp, String, Timestamp, String> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, Byte, String, String, String, Byte, Byte, Timestamp, String, Timestamp, String> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return UserApp.USER_APP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return UserApp.USER_APP.APP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UserApp.USER_APP.APP_DESC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UserApp.USER_APP.ICO_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return UserApp.USER_APP.APP_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UserApp.USER_APP.LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return UserApp.USER_APP.INNER_LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return UserApp.USER_APP.LABEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return UserApp.USER_APP.APP_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return UserApp.USER_APP.SET_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field11() {
        return UserApp.USER_APP.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return UserApp.USER_APP.CREATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field13() {
        return UserApp.USER_APP.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return UserApp.USER_APP.UPDATOR;
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
        return getAppName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAppDesc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIcoUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getAppOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getInnerLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getLabel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getAppType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getSetType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value11() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getCreator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value13() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getUpdator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value2(String value) {
        setAppName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value3(String value) {
        setAppDesc(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value4(String value) {
        setIcoUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value5(Byte value) {
        setAppOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value6(String value) {
        setLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value7(String value) {
        setInnerLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value8(String value) {
        setLabel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value9(Byte value) {
        setAppType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value10(Byte value) {
        setSetType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value11(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value12(String value) {
        setCreator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value13(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord value14(String value) {
        setUpdator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserAppRecord values(Integer value1, String value2, String value3, String value4, Byte value5, String value6, String value7, String value8, Byte value9, Byte value10, Timestamp value11, String value12, Timestamp value13, String value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserAppRecord
     */
    public UserAppRecord() {
        super(UserApp.USER_APP);
    }

    /**
     * Create a detached, initialised UserAppRecord
     */
    public UserAppRecord(Integer id, String appName, String appDesc, String icoUrl, Byte appOrder, String link, String innerLink, String label, Byte appType, Byte setType, Timestamp createTime, String creator, Timestamp updateTime, String updator) {
        super(UserApp.USER_APP);

        set(0, id);
        set(1, appName);
        set(2, appDesc);
        set(3, icoUrl);
        set(4, appOrder);
        set(5, link);
        set(6, innerLink);
        set(7, label);
        set(8, appType);
        set(9, setType);
        set(10, createTime);
        set(11, creator);
        set(12, updateTime);
        set(13, updator);
    }
}
