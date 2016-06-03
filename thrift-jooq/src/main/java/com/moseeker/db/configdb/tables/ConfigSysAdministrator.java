/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb.tables;


import com.moseeker.db.configdb.Configdb;
import com.moseeker.db.configdb.Keys;
import com.moseeker.db.configdb.tables.records.ConfigSysAdministratorRecord;

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
import org.jooq.types.UInteger;


/**
 * 大岂运维平台管理员表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysAdministrator extends TableImpl<ConfigSysAdministratorRecord> {

	private static final long serialVersionUID = -1565863910;

	/**
	 * The reference instance of <code>configdb.config_sys_administrator</code>
	 */
	public static final ConfigSysAdministrator CONFIG_SYS_ADMINISTRATOR = new ConfigSysAdministrator();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ConfigSysAdministratorRecord> getRecordType() {
		return ConfigSysAdministratorRecord.class;
	}

	/**
	 * The column <code>configdb.config_sys_administrator.id</code>.
	 */
	public final TableField<ConfigSysAdministratorRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>configdb.config_sys_administrator.name</code>.
	 */
	public final TableField<ConfigSysAdministratorRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>configdb.config_sys_administrator.email</code>.
	 */
	public final TableField<ConfigSysAdministratorRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>configdb.config_sys_administrator.mobile</code>.
	 */
	public final TableField<ConfigSysAdministratorRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>configdb.config_sys_administrator.password</code>.
	 */
	public final TableField<ConfigSysAdministratorRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

	/**
	 * The column <code>configdb.config_sys_administrator.is_disable</code>. 是否禁用，0：可用；1：禁用
	 */
	public final TableField<ConfigSysAdministratorRecord, Integer> IS_DISABLE = createField("is_disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "是否禁用，0：可用；1：禁用");

	/**
	 * The column <code>configdb.config_sys_administrator.login_count</code>. 登录次数
	 */
	public final TableField<ConfigSysAdministratorRecord, Integer> LOGIN_COUNT = createField("login_count", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "登录次数");

	/**
	 * The column <code>configdb.config_sys_administrator.last_login_time</code>. 最近登录时间
	 */
	public final TableField<ConfigSysAdministratorRecord, Timestamp> LAST_LOGIN_TIME = createField("last_login_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaulted(true), this, "最近登录时间");

	/**
	 * The column <code>configdb.config_sys_administrator.create_time</code>. 创建时间
	 */
	public final TableField<ConfigSysAdministratorRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * Create a <code>configdb.config_sys_administrator</code> table reference
	 */
	public ConfigSysAdministrator() {
		this("config_sys_administrator", null);
	}

	/**
	 * Create an aliased <code>configdb.config_sys_administrator</code> table reference
	 */
	public ConfigSysAdministrator(String alias) {
		this(alias, CONFIG_SYS_ADMINISTRATOR);
	}

	private ConfigSysAdministrator(String alias, Table<ConfigSysAdministratorRecord> aliased) {
		this(alias, aliased, null);
	}

	private ConfigSysAdministrator(String alias, Table<ConfigSysAdministratorRecord> aliased, Field<?>[] parameters) {
		super(alias, Configdb.CONFIGDB, aliased, parameters, "大岂运维平台管理员表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ConfigSysAdministratorRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_CONFIG_SYS_ADMINISTRATOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ConfigSysAdministratorRecord> getPrimaryKey() {
		return Keys.KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ConfigSysAdministratorRecord>> getKeys() {
		return Arrays.<UniqueKey<ConfigSysAdministratorRecord>>asList(Keys.KEY_CONFIG_SYS_ADMINISTRATOR_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigSysAdministrator as(String alias) {
		return new ConfigSysAdministrator(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ConfigSysAdministrator rename(String name) {
		return new ConfigSysAdministrator(name, null);
	}
}
