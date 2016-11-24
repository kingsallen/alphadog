/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.jobdb.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionShareTplConfRecord;


/**
 * 职位分享描述配置模板
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionShareTplConf extends TableImpl<JobPositionShareTplConfRecord> {

	private static final long serialVersionUID = -2061026243;

	/**
	 * The reference instance of <code>jobdb.job_position_share_tpl_conf</code>
	 */
	public static final JobPositionShareTplConf JOB_POSITION_SHARE_TPL_CONF = new JobPositionShareTplConf();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JobPositionShareTplConfRecord> getRecordType() {
		return JobPositionShareTplConfRecord.class;
	}

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.id</code>. PK
	 */
	public final TableField<JobPositionShareTplConfRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "PK");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.type</code>. 模板分类 1：雇主分享模板 2：员工分享模板
	 */
	public final TableField<JobPositionShareTplConfRecord, Short> TYPE = createField("type", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaulted(true), this, "模板分类 1：雇主分享模板 2：员工分享模板");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.name</code>. 模板名称
	 */
	public final TableField<JobPositionShareTplConfRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaulted(true), this, "模板名称");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.title</code>. 分享标题
	 */
	public final TableField<JobPositionShareTplConfRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "分享标题");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.description</code>. 分享简介
	 */
	public final TableField<JobPositionShareTplConfRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "分享简介");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.disable</code>. 是否禁用0：可用， 1：禁用
	 */
	public final TableField<JobPositionShareTplConfRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否禁用0：可用， 1：禁用");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.remark</code>. 备注
	 */
	public final TableField<JobPositionShareTplConfRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(128).defaulted(true), this, "备注");

	/**
	 * The column <code>jobdb.job_position_share_tpl_conf.priority</code>. 优先级
	 */
	public final TableField<JobPositionShareTplConfRecord, Byte> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "优先级");

	/**
	 * Create a <code>jobdb.job_position_share_tpl_conf</code> table reference
	 */
	public JobPositionShareTplConf() {
		this("job_position_share_tpl_conf", null);
	}

	/**
	 * Create an aliased <code>jobdb.job_position_share_tpl_conf</code> table reference
	 */
	public JobPositionShareTplConf(String alias) {
		this(alias, JOB_POSITION_SHARE_TPL_CONF);
	}

	private JobPositionShareTplConf(String alias, Table<JobPositionShareTplConfRecord> aliased) {
		this(alias, aliased, null);
	}

	private JobPositionShareTplConf(String alias, Table<JobPositionShareTplConfRecord> aliased, Field<?>[] parameters) {
		super(alias, Jobdb.JOBDB, aliased, parameters, "职位分享描述配置模板");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<JobPositionShareTplConfRecord, Integer> getIdentity() {
		return Keys.IDENTITY_JOB_POSITION_SHARE_TPL_CONF;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JobPositionShareTplConfRecord> getPrimaryKey() {
		return Keys.KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JobPositionShareTplConfRecord>> getKeys() {
		return Arrays.<UniqueKey<JobPositionShareTplConfRecord>>asList(Keys.KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionShareTplConf as(String alias) {
		return new JobPositionShareTplConf(alias, this);
	}

	/**
	 * Rename this table
	 */
	public JobPositionShareTplConf rename(String name) {
		return new JobPositionShareTplConf(name, null);
	}
}
