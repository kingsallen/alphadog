/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb.tables;


import com.moseeker.db.configdb.Configdb;
import com.moseeker.db.configdb.Keys;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationGroupRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 管理员通知群组
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigAdminnotificationGroup extends TableImpl<ConfigAdminnotificationGroupRecord> {

	private static final long serialVersionUID = 2056995874;

	/**
	 * The reference instance of <code>configdb.config_adminnotification_group</code>
	 */
	public static final ConfigAdminnotificationGroup CONFIG_ADMINNOTIFICATION_GROUP = new ConfigAdminnotificationGroup();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ConfigAdminnotificationGroupRecord> getRecordType() {
		return ConfigAdminnotificationGroupRecord.class;
	}

	/**
	 * The column <code>configdb.config_adminnotification_group.id</code>.
	 */
	public final TableField<ConfigAdminnotificationGroupRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>configdb.config_adminnotification_group.name</code>. 群组名称
	 */
	public final TableField<ConfigAdminnotificationGroupRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false), this, "群组名称");

	/**
	 * The column <code>configdb.config_adminnotification_group.create_time</code>.
	 */
	public final TableField<ConfigAdminnotificationGroupRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>configdb.config_adminnotification_group</code> table reference
	 */
	public ConfigAdminnotificationGroup() {
		this("config_adminnotification_group", null);
	}

	/**
	 * Create an aliased <code>configdb.config_adminnotification_group</code> table reference
	 */
	public ConfigAdminnotificationGroup(String alias) {
		this(alias, CONFIG_ADMINNOTIFICATION_GROUP);
	}

	private ConfigAdminnotificationGroup(String alias, Table<ConfigAdminnotificationGroupRecord> aliased) {
		this(alias, aliased, null);
	}

	private ConfigAdminnotificationGroup(String alias, Table<ConfigAdminnotificationGroupRecord> aliased, Field<?>[] parameters) {
		super(alias, Configdb.CONFIGDB, aliased, parameters, "管理员通知群组");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ConfigAdminnotificationGroupRecord> getPrimaryKey() {
		return Keys.KEY_CONFIG_ADMINNOTIFICATION_GROUP_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ConfigAdminnotificationGroupRecord>> getKeys() {
		return Arrays.<UniqueKey<ConfigAdminnotificationGroupRecord>>asList(Keys.KEY_CONFIG_ADMINNOTIFICATION_GROUP_PRIMARY, Keys.KEY_CONFIG_ADMINNOTIFICATION_GROUP_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ConfigAdminnotificationGroup as(String alias) {
		return new ConfigAdminnotificationGroup(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ConfigAdminnotificationGroup rename(String name) {
		return new ConfigAdminnotificationGroup(name, null);
	}
}
