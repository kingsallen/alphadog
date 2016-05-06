/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables;


import com.moseeker.db.profiledb.Keys;
import com.moseeker.db.profiledb.Profiledb;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;

import java.sql.Date;
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
 * Profile的工作经历
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileWorkexp extends TableImpl<ProfileWorkexpRecord> {

	private static final long serialVersionUID = 401926410;

	/**
	 * The reference instance of <code>profileDB.profile_workexp</code>
	 */
	public static final ProfileWorkexp PROFILE_WORKEXP = new ProfileWorkexp();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ProfileWorkexpRecord> getRecordType() {
		return ProfileWorkexpRecord.class;
	}

	/**
	 * The column <code>profileDB.profile_workexp.id</code>. 主key
	 */
	public final TableField<ProfileWorkexpRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>profileDB.profile_workexp.profile_id</code>. profile.id
	 */
	public final TableField<ProfileWorkexpRecord, UInteger> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "profile.id");

	/**
	 * The column <code>profileDB.profile_workexp.start</code>. 起止时间-起 yyyy-mm-dd
	 */
	public final TableField<ProfileWorkexpRecord, Date> START = createField("start", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "起止时间-起 yyyy-mm-dd");

	/**
	 * The column <code>profileDB.profile_workexp.end</code>. 起止时间-止 yyyy-mm-dd
	 */
	public final TableField<ProfileWorkexpRecord, Date> END = createField("end", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "起止时间-止 yyyy-mm-dd");

	/**
	 * The column <code>profileDB.profile_workexp.end_until_now</code>. 是否至今 0：否 1：是
	 */
	public final TableField<ProfileWorkexpRecord, Byte> END_UNTIL_NOW = createField("end_until_now", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否至今 0：否 1：是");

	/**
	 * The column <code>profileDB.profile_workexp.salary_type</code>. 年薪 0:未选择 1: 6万以下, 2: 6万-8万, 3: 8万-12万, 4: 12万-20万, 5: 20万-30万, 6: 30万以上
	 */
	public final TableField<ProfileWorkexpRecord, Byte> SALARY_TYPE = createField("salary_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "年薪 0:未选择 1: 6万以下, 2: 6万-8万, 3: 8万-12万, 4: 12万-20万, 5: 20万-30万, 6: 30万以上");

	/**
	 * The column <code>profileDB.profile_workexp.salary_code</code>. 年薪 0:未选择 1: 6万以下, 2: 6万-8万, 3: 8万-12万, 4: 12万-20万, 5: 20万-30万, 6: 30万以上
	 */
	public final TableField<ProfileWorkexpRecord, Byte> SALARY_CODE = createField("salary_code", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "年薪 0:未选择 1: 6万以下, 2: 6万-8万, 3: 8万-12万, 4: 12万-20万, 5: 20万-30万, 6: 30万以上");

	/**
	 * The column <code>profileDB.profile_workexp.industry_code</code>. 行业字典编码
	 */
	public final TableField<ProfileWorkexpRecord, Byte> INDUSTRY_CODE = createField("industry_code", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "行业字典编码");

	/**
	 * The column <code>profileDB.profile_workexp.industry_name</code>. 行业名称
	 */
	public final TableField<ProfileWorkexpRecord, String> INDUSTRY_NAME = createField("industry_name", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaulted(true), this, "行业名称");

	/**
	 * The column <code>profileDB.profile_workexp.company</code>. 公司名称
	 */
	public final TableField<ProfileWorkexpRecord, String> COMPANY = createField("company", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "公司名称");

	/**
	 * The column <code>profileDB.profile_workexp.department</code>. 部门名称
	 */
	public final TableField<ProfileWorkexpRecord, String> DEPARTMENT = createField("department", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "部门名称");

	/**
	 * The column <code>profileDB.profile_workexp.position_code</code>. 职位字典编码
	 */
	public final TableField<ProfileWorkexpRecord, Byte> POSITION_CODE = createField("position_code", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "职位字典编码");

	/**
	 * The column <code>profileDB.profile_workexp.position_name</code>. 职位名称
	 */
	public final TableField<ProfileWorkexpRecord, String> POSITION_NAME = createField("position_name", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaulted(true), this, "职位名称");

	/**
	 * The column <code>profileDB.profile_workexp.description</code>. 教育描述
	 */
	public final TableField<ProfileWorkexpRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaulted(true), this, "教育描述");

	/**
	 * The column <code>profileDB.profile_workexp.work_type</code>. 工作类型 0:未选择 1:全职 2:兼职 3:合同工
	 */
	public final TableField<ProfileWorkexpRecord, Byte> WORK_TYPE = createField("work_type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "工作类型 0:未选择 1:全职 2:兼职 3:合同工");

	/**
	 * The column <code>profileDB.profile_workexp.scale</code>. 公司规模 0:未选择 1:10人以内?
	 */
	public final TableField<ProfileWorkexpRecord, Byte> SCALE = createField("scale", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "公司规模 0:未选择 1:10人以内?");

	/**
	 * The column <code>profileDB.profile_workexp.create_time</code>. 创建时间
	 */
	public final TableField<ProfileWorkexpRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>profileDB.profile_workexp.update_time</code>. 更新时间
	 */
	public final TableField<ProfileWorkexpRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "更新时间");

	/**
	 * Create a <code>profileDB.profile_workexp</code> table reference
	 */
	public ProfileWorkexp() {
		this("profile_workexp", null);
	}

	/**
	 * Create an aliased <code>profileDB.profile_workexp</code> table reference
	 */
	public ProfileWorkexp(String alias) {
		this(alias, PROFILE_WORKEXP);
	}

	private ProfileWorkexp(String alias, Table<ProfileWorkexpRecord> aliased) {
		this(alias, aliased, null);
	}

	private ProfileWorkexp(String alias, Table<ProfileWorkexpRecord> aliased, Field<?>[] parameters) {
		super(alias, Profiledb.PROFILEDB, aliased, parameters, "Profile的工作经历");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ProfileWorkexpRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_PROFILE_WORKEXP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ProfileWorkexpRecord> getPrimaryKey() {
		return Keys.KEY_PROFILE_WORKEXP_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ProfileWorkexpRecord>> getKeys() {
		return Arrays.<UniqueKey<ProfileWorkexpRecord>>asList(Keys.KEY_PROFILE_WORKEXP_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileWorkexp as(String alias) {
		return new ProfileWorkexp(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ProfileWorkexp rename(String name) {
		return new ProfileWorkexp(name, null);
	}
}
