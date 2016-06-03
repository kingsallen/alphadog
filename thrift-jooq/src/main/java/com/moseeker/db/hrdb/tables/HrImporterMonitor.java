/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables;


import com.moseeker.db.hrdb.Hrdb;
import com.moseeker.db.hrdb.Keys;
import com.moseeker.db.hrdb.tables.records.HrImporterMonitorRecord;

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
 * 企业用户导入数据异步处理监控操作表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrImporterMonitor extends TableImpl<HrImporterMonitorRecord> {

	private static final long serialVersionUID = 1043963291;

	/**
	 * The reference instance of <code>hrdb.hr_importer_monitor</code>
	 */
	public static final HrImporterMonitor HR_IMPORTER_MONITOR = new HrImporterMonitor();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrImporterMonitorRecord> getRecordType() {
		return HrImporterMonitorRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_importer_monitor.id</code>.
	 */
	public final TableField<HrImporterMonitorRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_importer_monitor.company_id</code>. 公司ID，hr_company.id
	 */
	public final TableField<HrImporterMonitorRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "公司ID，hr_company.id");

	/**
	 * The column <code>hrdb.hr_importer_monitor.hraccount_id</code>. hr_account.id 账号编号
	 */
	public final TableField<HrImporterMonitorRecord, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "hr_account.id 账号编号");

	/**
	 * The column <code>hrdb.hr_importer_monitor.type</code>. 要导入的表：0：user_employee 1: job_position 2:hr_company
	 */
	public final TableField<HrImporterMonitorRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "要导入的表：0：user_employee 1: job_position 2:hr_company");

	/**
	 * The column <code>hrdb.hr_importer_monitor.file</code>. 导入文件的绝对路径
	 */
	public final TableField<HrImporterMonitorRecord, String> FILE = createField("file", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaulted(true), this, "导入文件的绝对路径");

	/**
	 * The column <code>hrdb.hr_importer_monitor.status</code>. 0：待处理 1：处理失败 2：处理成功
	 */
	public final TableField<HrImporterMonitorRecord, Short> STATUS = createField("status", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaulted(true), this, "0：待处理 1：处理失败 2：处理成功");

	/**
	 * The column <code>hrdb.hr_importer_monitor.message</code>. 操作提示信息
	 */
	public final TableField<HrImporterMonitorRecord, String> MESSAGE = createField("message", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "操作提示信息");

	/**
	 * The column <code>hrdb.hr_importer_monitor.create_time</code>.
	 */
	public final TableField<HrImporterMonitorRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_importer_monitor.update_time</code>.
	 */
	public final TableField<HrImporterMonitorRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_importer_monitor.name</code>. 导入的文件名
	 */
	public final TableField<HrImporterMonitorRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaulted(true), this, "导入的文件名");

	/**
	 * The column <code>hrdb.hr_importer_monitor.sys</code>. 1:mp, 2:hr
	 */
	public final TableField<HrImporterMonitorRecord, Byte> SYS = createField("sys", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "1:mp, 2:hr");

	/**
	 * Create a <code>hrdb.hr_importer_monitor</code> table reference
	 */
	public HrImporterMonitor() {
		this("hr_importer_monitor", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_importer_monitor</code> table reference
	 */
	public HrImporterMonitor(String alias) {
		this(alias, HR_IMPORTER_MONITOR);
	}

	private HrImporterMonitor(String alias, Table<HrImporterMonitorRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrImporterMonitor(String alias, Table<HrImporterMonitorRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "企业用户导入数据异步处理监控操作表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrImporterMonitorRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_IMPORTER_MONITOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrImporterMonitorRecord> getPrimaryKey() {
		return Keys.KEY_HR_IMPORTER_MONITOR_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrImporterMonitorRecord>> getKeys() {
		return Arrays.<UniqueKey<HrImporterMonitorRecord>>asList(Keys.KEY_HR_IMPORTER_MONITOR_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrImporterMonitor as(String alias) {
		return new HrImporterMonitor(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrImporterMonitor rename(String name) {
		return new HrImporterMonitor(name, null);
	}
}
