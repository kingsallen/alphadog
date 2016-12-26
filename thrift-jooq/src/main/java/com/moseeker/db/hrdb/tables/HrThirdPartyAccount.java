/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables;


import com.moseeker.db.hrdb.Hrdb;
import com.moseeker.db.hrdb.Keys;
import com.moseeker.db.hrdb.tables.records.HrThirdPartyAccountRecord;

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
 * 第三方渠道帐号
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccount extends TableImpl<HrThirdPartyAccountRecord> {

	private static final long serialVersionUID = -1410222288;

	/**
	 * The reference instance of <code>hrdb.hr_third_party_account</code>
	 */
	public static final HrThirdPartyAccount HR_THIRD_PARTY_ACCOUNT = new HrThirdPartyAccount();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrThirdPartyAccountRecord> getRecordType() {
		return HrThirdPartyAccountRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_third_party_account.id</code>. 编号
	 */
	public final TableField<HrThirdPartyAccountRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "编号");

	/**
	 * The column <code>hrdb.hr_third_party_account.channel</code>. 1=51job,2=猎聘,3=智联,4=linkedin
	 */
	public final TableField<HrThirdPartyAccountRecord, Short> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.SMALLINT.defaulted(true), this, "1=51job,2=猎聘,3=智联,4=linkedin");

	/**
	 * The column <code>hrdb.hr_third_party_account.username</code>. 帐号
	 */
	public final TableField<HrThirdPartyAccountRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaulted(true), this, "帐号");

	/**
	 * The column <code>hrdb.hr_third_party_account.password</code>. 密码
	 */
	public final TableField<HrThirdPartyAccountRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), this, "密码");

	/**
	 * The column <code>hrdb.hr_third_party_account.membername</code>. 会员名称
	 */
	public final TableField<HrThirdPartyAccountRecord, String> MEMBERNAME = createField("membername", org.jooq.impl.SQLDataType.VARCHAR.length(60).defaulted(true), this, "会员名称");

	/**
	 * The column <code>hrdb.hr_third_party_account.binding</code>. 0=未绑定,1=绑定,2=绑定中，3=绑定失败
	 */
	public final TableField<HrThirdPartyAccountRecord, Short> BINDING = createField("binding", org.jooq.impl.SQLDataType.SMALLINT.defaulted(true), this, "0=未绑定,1=绑定,2=绑定中，3=绑定失败");

	/**
	 * The column <code>hrdb.hr_third_party_account.company_id</code>. hrdb.hr_company.id
	 */
	public final TableField<HrThirdPartyAccountRecord, UInteger> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "hrdb.hr_company.id");

	/**
	 * The column <code>hrdb.hr_third_party_account.remain_num</code>. 点数
	 */
	public final TableField<HrThirdPartyAccountRecord, UInteger> REMAIN_NUM = createField("remain_num", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "点数");

	/**
	 * The column <code>hrdb.hr_third_party_account.sync_time</code>. 同步时间
	 */
	public final TableField<HrThirdPartyAccountRecord, Timestamp> SYNC_TIME = createField("sync_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "同步时间");

	/**
	 * The column <code>hrdb.hr_third_party_account.update_time</code>. 数据更新时间
	 */
	public final TableField<HrThirdPartyAccountRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "数据更新时间");

	/**
	 * The column <code>hrdb.hr_third_party_account.create_time</code>. 创建时间
	 */
	public final TableField<HrThirdPartyAccountRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * Create a <code>hrdb.hr_third_party_account</code> table reference
	 */
	public HrThirdPartyAccount() {
		this("hr_third_party_account", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_third_party_account</code> table reference
	 */
	public HrThirdPartyAccount(String alias) {
		this(alias, HR_THIRD_PARTY_ACCOUNT);
	}

	private HrThirdPartyAccount(String alias, Table<HrThirdPartyAccountRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrThirdPartyAccount(String alias, Table<HrThirdPartyAccountRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "第三方渠道帐号");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrThirdPartyAccountRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_THIRD_PARTY_ACCOUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrThirdPartyAccountRecord> getPrimaryKey() {
		return Keys.KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrThirdPartyAccountRecord>> getKeys() {
		return Arrays.<UniqueKey<HrThirdPartyAccountRecord>>asList(Keys.KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY, Keys.KEY_HR_THIRD_PARTY_ACCOUNT_IDX_CHANNEL_COMPYID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccount as(String alias) {
		return new HrThirdPartyAccount(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrThirdPartyAccount rename(String name) {
		return new HrThirdPartyAccount(name, null);
	}
}
