/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigOmsSwitchManagementRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class ConfigOmsSwitchManagement extends TableImpl<ConfigOmsSwitchManagementRecord> {

    private static final long serialVersionUID = -1332102459;

    /**
     * The reference instance of <code>configdb.config_oms_switch_management</code>
     */
    public static final ConfigOmsSwitchManagement CONFIG_OMS_SWITCH_MANAGEMENT = new ConfigOmsSwitchManagement();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigOmsSwitchManagementRecord> getRecordType() {
        return ConfigOmsSwitchManagementRecord.class;
    }

    /**
     * The column <code>configdb.config_oms_switch_management.id</code>. primaryKey
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primaryKey");

    /**
     * The column <code>configdb.config_oms_switch_management.company_id</code>. 公司id hrdb.HrCompany.id
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "公司id hrdb.HrCompany.id");

    /**
     * The column <code>configdb.config_oms_switch_management.module_name</code>. 各产品定义标识 1:我是员工，2:粉丝智能推荐，3:meet mobot(问卷调查)，4:员工智能推荐，5:社招，6:校招，7.人脉雷达，8.老员工回聘， 9.五百强
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Integer> MODULE_NAME = createField("module_name", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "各产品定义标识 1:我是员工，2:粉丝智能推荐，3:meet mobot(问卷调查)，4:员工智能推荐，5:社招，6:校招，7.人脉雷达，8.老员工回聘， 9.五百强");

    /**
     * The column <code>configdb.config_oms_switch_management.module_param</code>. 各产品所需附加参数，json格式 (只用于oms后端配置的参数)
     */
    public final TableField<ConfigOmsSwitchManagementRecord, String> MODULE_PARAM = createField("module_param", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "各产品所需附加参数，json格式 (只用于oms后端配置的参数)");

    /**
     * The column <code>configdb.config_oms_switch_management.is_valid</code>. 是否可用 0：不可用，1：可用
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Byte> IS_VALID = createField("is_valid", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可用 0：不可用，1：可用");

    /**
     * The column <code>configdb.config_oms_switch_management.version</code>. 版本号
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Integer> VERSION = createField("version", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "版本号");

    /**
     * The column <code>configdb.config_oms_switch_management.create_time</code>. 创建时间
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>configdb.config_oms_switch_management.update_time</code>. 更新时间
     */
    public final TableField<ConfigOmsSwitchManagementRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>configdb.config_oms_switch_management</code> table reference
     */
    public ConfigOmsSwitchManagement() {
        this("config_oms_switch_management", null);
    }

    /**
     * Create an aliased <code>configdb.config_oms_switch_management</code> table reference
     */
    public ConfigOmsSwitchManagement(String alias) {
        this(alias, CONFIG_OMS_SWITCH_MANAGEMENT);
    }

    private ConfigOmsSwitchManagement(String alias, Table<ConfigOmsSwitchManagementRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigOmsSwitchManagement(String alias, Table<ConfigOmsSwitchManagementRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Configdb.CONFIGDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ConfigOmsSwitchManagementRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CONFIG_OMS_SWITCH_MANAGEMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigOmsSwitchManagementRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_OMS_SWITCH_MANAGEMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigOmsSwitchManagementRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigOmsSwitchManagementRecord>>asList(Keys.KEY_CONFIG_OMS_SWITCH_MANAGEMENT_PRIMARY, Keys.KEY_CONFIG_OMS_SWITCH_MANAGEMENT_MODULE_NAME和COMPANY_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigOmsSwitchManagement as(String alias) {
        return new ConfigOmsSwitchManagement(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfigOmsSwitchManagement rename(String name) {
        return new ConfigOmsSwitchManagement(name, null);
    }
}
