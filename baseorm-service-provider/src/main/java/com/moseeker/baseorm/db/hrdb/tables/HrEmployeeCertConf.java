/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


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

import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;


/**
 * 部门员工配置表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCertConf extends TableImpl<HrEmployeeCertConfRecord> {

	private static final long serialVersionUID = -1467649291;

	/**
	 * The reference instance of <code>hrdb.hr_employee_cert_conf</code>
	 */
	public static final HrEmployeeCertConf HR_EMPLOYEE_CERT_CONF = new HrEmployeeCertConf();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrEmployeeCertConfRecord> getRecordType() {
		return HrEmployeeCertConfRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.id</code>.
	 */
	public final TableField<HrEmployeeCertConfRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.company_id</code>. 所属公司 hr_company.id
	 */
	public final TableField<HrEmployeeCertConfRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "所属公司 hr_company.id");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.is_strict</code>. 是否严格0：严格，1：不严格
	 */
	public final TableField<HrEmployeeCertConfRecord, Byte> IS_STRICT = createField("is_strict", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否严格0：严格，1：不严格");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.email_suffix</code>. 邮箱后缀
	 */
	public final TableField<HrEmployeeCertConfRecord, String> EMAIL_SUFFIX = createField("email_suffix", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "邮箱后缀");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.create_time</code>. 创建时间
	 */
	public final TableField<HrEmployeeCertConfRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.update_time</code>. 修改时间
	 */
	public final TableField<HrEmployeeCertConfRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "修改时间");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.disable</code>. 是否启用 0：启用1：禁用
	 */
	public final TableField<HrEmployeeCertConfRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否启用 0：启用1：禁用");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.bd_add_group</code>. 用户绑定时需要加入员工组Flag, 0:需要添加到员工组 1:不需要添加到员工组
	 */
	public final TableField<HrEmployeeCertConfRecord, Byte> BD_ADD_GROUP = createField("bd_add_group", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "用户绑定时需要加入员工组Flag, 0:需要添加到员工组 1:不需要添加到员工组");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.bd_use_group_id</code>. 用户绑定时需要加入员工组的分组编号
	 */
	public final TableField<HrEmployeeCertConfRecord, Integer> BD_USE_GROUP_ID = createField("bd_use_group_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "用户绑定时需要加入员工组的分组编号");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.auth_mode</code>. 认证方式，0:不启用员工认证, 1:邮箱认证, 2:自定义认证, 3:姓名手机号认证, 4:邮箱自定义两种认证
	 */
	public final TableField<HrEmployeeCertConfRecord, Byte> AUTH_MODE = createField("auth_mode", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "认证方式，0:不启用员工认证, 1:邮箱认证, 2:自定义认证, 3:姓名手机号认证, 4:邮箱自定义两种认证");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.auth_code</code>. 认证码（6到20位， 字母和数字组成，区分大小写）  默认为空
	 */
	public final TableField<HrEmployeeCertConfRecord, String> AUTH_CODE = createField("auth_code", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaulted(true), this, "认证码（6到20位， 字母和数字组成，区分大小写）  默认为空");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.custom</code>. 配置的自定义认证名称
	 */
	public final TableField<HrEmployeeCertConfRecord, String> CUSTOM = createField("custom", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false).defaulted(true), this, "配置的自定义认证名称");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.questions</code>. 问答列表(json)
	 */
	public final TableField<HrEmployeeCertConfRecord, String> QUESTIONS = createField("questions", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaulted(true), this, "问答列表(json)");

	/**
	 * The column <code>hrdb.hr_employee_cert_conf.custom_hint</code>. 自定义认证提示
	 */
	public final TableField<HrEmployeeCertConfRecord, String> CUSTOM_HINT = createField("custom_hint", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "自定义认证提示");

	/**
	 * Create a <code>hrdb.hr_employee_cert_conf</code> table reference
	 */
	public HrEmployeeCertConf() {
		this("hr_employee_cert_conf", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_employee_cert_conf</code> table reference
	 */
	public HrEmployeeCertConf(String alias) {
		this(alias, HR_EMPLOYEE_CERT_CONF);
	}

	private HrEmployeeCertConf(String alias, Table<HrEmployeeCertConfRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrEmployeeCertConf(String alias, Table<HrEmployeeCertConfRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "部门员工配置表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrEmployeeCertConfRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_EMPLOYEE_CERT_CONF;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrEmployeeCertConfRecord> getPrimaryKey() {
		return Keys.KEY_HR_EMPLOYEE_CERT_CONF_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrEmployeeCertConfRecord>> getKeys() {
		return Arrays.<UniqueKey<HrEmployeeCertConfRecord>>asList(Keys.KEY_HR_EMPLOYEE_CERT_CONF_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrEmployeeCertConf as(String alias) {
		return new HrEmployeeCertConf(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrEmployeeCertConf rename(String name) {
		return new HrEmployeeCertConf(name, null);
	}
}
