/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb.tables;


import com.moseeker.db.configdb.Configdb;
import com.moseeker.db.configdb.Keys;
import com.moseeker.db.configdb.tables.records.ConfigSysTemplateTypeRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 系统模板类型管理
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysTemplateType extends TableImpl<ConfigSysTemplateTypeRecord> {

	private static final long serialVersionUID = 226655163;

	/**
	 * The reference instance of <code>configDB.config_sys_template_type</code>
	 */
	public static final ConfigSysTemplateType CONFIG_SYS_TEMPLATE_TYPE = new ConfigSysTemplateType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ConfigSysTemplateTypeRecord> getRecordType() {
		return ConfigSysTemplateTypeRecord.class;
	}

	/**
	 * The column <code>configDB.config_sys_template_type.id</code>.
	 */
	public final TableField<ConfigSysTemplateTypeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>configDB.config_sys_template_type.name</code>. 类型名称
	 */
	public final TableField<ConfigSysTemplateTypeRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "类型名称");

	/**
	 * The column <code>configDB.config_sys_template_type.create_time</code>. 创建时间
	 */
	public final TableField<ConfigSysTemplateTypeRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>configDB.config_sys_template_type.update_time</code>. 修改时间
	 */
	public final TableField<ConfigSysTemplateTypeRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "修改时间");

	/**
	 * The column <code>configDB.config_sys_template_type.status</code>. 是否有效 0：有效1：无效
	 */
	public final TableField<ConfigSysTemplateTypeRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否有效 0：有效1：无效");

	/**
	 * Create a <code>configDB.config_sys_template_type</code> table reference
	 */
	public ConfigSysTemplateType() {
		this("config_sys_template_type", null);
	}

	/**
	 * Create an aliased <code>configDB.config_sys_template_type</code> table reference
	 */
	public ConfigSysTemplateType(String alias) {
		this(alias, CONFIG_SYS_TEMPLATE_TYPE);
	}

	private ConfigSysTemplateType(String alias, Table<ConfigSysTemplateTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private ConfigSysTemplateType(String alias, Table<ConfigSysTemplateTypeRecord> aliased, Field<?>[] parameters) {
		super(alias, Configdb.CONFIGDB, aliased, parameters, "系统模板类型管理");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ConfigSysTemplateTypeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_CONFIG_SYS_TEMPLATE_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ConfigSysTemplateTypeRecord> getPrimaryKey() {
		return Keys.KEY_CONFIG_SYS_TEMPLATE_TYPE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ConfigSysTemplateTypeRecord>> getKeys() {
		return Arrays.<UniqueKey<ConfigSysTemplateTypeRecord>>asList(Keys.KEY_CONFIG_SYS_TEMPLATE_TYPE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysTemplateType as(String alias) {
		return new ConfigSysTemplateType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ConfigSysTemplateType rename(String name) {
		return new ConfigSysTemplateType(name, null);
	}
}
