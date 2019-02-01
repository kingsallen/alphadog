/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.records;


import com.moseeker.baseorm.db.configdb.tables.ConfigOmsSwitchManagement;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class ConfigOmsSwitchManagementRecord extends UpdatableRecordImpl<ConfigOmsSwitchManagementRecord> implements Record8<Integer, Integer, Integer, String, Byte, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1905685744;

    /**
     * Setter for <code>configdb.config_oms_switch_management.id</code>. primaryKey
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.id</code>. primaryKey
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.company_id</code>. 公司id hrdb.HrCompany.id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.company_id</code>. 公司id hrdb.HrCompany.id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.module_name</code>. 各产品定义标识 1:我是员工，2:粉丝智能推荐，3:meet mobot(问卷调查)，4:员工智能推荐，5:社招，6:校招，7.人脉雷达，8.老员工回聘， 9.五百强，10.多ip访问
     */
    public void setModuleName(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.module_name</code>. 各产品定义标识 1:我是员工，2:粉丝智能推荐，3:meet mobot(问卷调查)，4:员工智能推荐，5:社招，6:校招，7.人脉雷达，8.老员工回聘， 9.五百强，10.多ip访问
     */
    public Integer getModuleName() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.module_param</code>. 各产品所需附加参数，json格式 (只用于oms后端配置的参数)
     */
    public void setModuleParam(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.module_param</code>. 各产品所需附加参数，json格式 (只用于oms后端配置的参数)
     */
    public String getModuleParam() {
        return (String) get(3);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.is_valid</code>. 是否可用 0：不可用，1：可用
     */
    public void setIsValid(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.is_valid</code>. 是否可用 0：不可用，1：可用
     */
    public Byte getIsValid() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.version</code>. 版本号
     */
    public void setVersion(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.version</code>. 版本号
     */
    public Integer getVersion() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>configdb.config_oms_switch_management.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>configdb.config_oms_switch_management.update_time</code>. 更新时间
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
    public Row8<Integer, Integer, Integer, String, Byte, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Integer, String, Byte, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_PARAM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.IS_VALID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT.UPDATE_TIME;
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
        return getModuleName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getModuleParam();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getIsValid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getVersion();
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
    public ConfigOmsSwitchManagementRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value3(Integer value) {
        setModuleName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value4(String value) {
        setModuleParam(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value5(Byte value) {
        setIsValid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value6(Integer value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagementRecord values(Integer value1, Integer value2, Integer value3, String value4, Byte value5, Integer value6, Timestamp value7, Timestamp value8) {
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
     * Create a detached ConfigOmsSwitchManagementRecord
     */
    public ConfigOmsSwitchManagementRecord() {
        super(ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT);
    }

    /**
     * Create a detached, initialised ConfigOmsSwitchManagementRecord
     */
    public ConfigOmsSwitchManagementRecord(Integer id, Integer companyId, Integer moduleName, String moduleParam, Byte isValid, Integer version, Timestamp createTime, Timestamp updateTime) {
        super(ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT);

        set(0, id);
        set(1, companyId);
        set(2, moduleName);
        set(3, moduleParam);
        set(4, isValid);
        set(5, version);
        set(6, createTime);
        set(7, updateTime);
    }
}
