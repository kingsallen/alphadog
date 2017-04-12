/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysH5StyleTplRecord;

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
 * 大岂H5模板表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysH5StyleTpl extends TableImpl<ConfigSysH5StyleTplRecord> {

    private static final long serialVersionUID = -1974010588;

    /**
     * The reference instance of <code>configdb.config_sys_h5_style_tpl</code>
     */
    public static final ConfigSysH5StyleTpl CONFIG_SYS_H5_STYLE_TPL = new ConfigSysH5StyleTpl();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigSysH5StyleTplRecord> getRecordType() {
        return ConfigSysH5StyleTplRecord.class;
    }

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.id</code>.
     */
    public final TableField<ConfigSysH5StyleTplRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.name</code>. 模板名称
     */
    public final TableField<ConfigSysH5StyleTplRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板名称");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.logo</code>. 模板LOGO的相对路径
     */
    public final TableField<ConfigSysH5StyleTplRecord, String> LOGO = createField("logo", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板LOGO的相对路径");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.hr_tpl</code>. 模板风格名称，只允许英文，hr预览用
     */
    public final TableField<ConfigSysH5StyleTplRecord, String> HR_TPL = createField("hr_tpl", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板风格名称，只允许英文，hr预览用");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.wx_tpl</code>. 模板风格名称，只允许英文，微信端用
     */
    public final TableField<ConfigSysH5StyleTplRecord, String> WX_TPL = createField("wx_tpl", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板风格名称，只允许英文，微信端用");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.priority</code>. 排序优先级
     */
    public final TableField<ConfigSysH5StyleTplRecord, Short> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("10", org.jooq.impl.SQLDataType.SMALLINT)), this, "排序优先级");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.disable</code>. 是否有效  0：有效 1：无效
     */
    public final TableField<ConfigSysH5StyleTplRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否有效  0：有效 1：无效");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.create_time</code>.
     */
    public final TableField<ConfigSysH5StyleTplRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>configdb.config_sys_h5_style_tpl.update_time</code>.
     */
    public final TableField<ConfigSysH5StyleTplRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>configdb.config_sys_h5_style_tpl</code> table reference
     */
    public ConfigSysH5StyleTpl() {
        this("config_sys_h5_style_tpl", null);
    }

    /**
     * Create an aliased <code>configdb.config_sys_h5_style_tpl</code> table reference
     */
    public ConfigSysH5StyleTpl(String alias) {
        this(alias, CONFIG_SYS_H5_STYLE_TPL);
    }

    private ConfigSysH5StyleTpl(String alias, Table<ConfigSysH5StyleTplRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigSysH5StyleTpl(String alias, Table<ConfigSysH5StyleTplRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "大岂H5模板表");
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
    public Identity<ConfigSysH5StyleTplRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CONFIG_SYS_H5_STYLE_TPL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigSysH5StyleTplRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_SYS_H5_STYLE_TPL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigSysH5StyleTplRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigSysH5StyleTplRecord>>asList(Keys.KEY_CONFIG_SYS_H5_STYLE_TPL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigSysH5StyleTpl as(String alias) {
        return new ConfigSysH5StyleTpl(alias, this);
    }

    /**
     * Rename this table
     */
    public ConfigSysH5StyleTpl rename(String name) {
        return new ConfigSysH5StyleTpl(name, null);
    }
}