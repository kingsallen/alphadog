/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;

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
 * 第三方账号和hr表关联表，账号分配表，取消分配将status置为0，重新分配不修改记录而是新加分配记录
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccountHr extends TableImpl<HrThirdPartyAccountHrRecord> {

	private static final long serialVersionUID = -1225057228;

	/**
	 * The reference instance of <code>hrdb.hr_third_party_account_hr</code>
	 */
	public static final HrThirdPartyAccountHr HR_THIRD_PARTY_ACCOUNT_HR = new HrThirdPartyAccountHr();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrThirdPartyAccountHrRecord> getRecordType() {
		return HrThirdPartyAccountHrRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.id</code>.
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.third_party_account_id</code>. 第三方账号ID
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Integer> THIRD_PARTY_ACCOUNT_ID = createField("third_party_account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "第三方账号ID");

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.hr_account_id</code>. hr账号ID
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Integer> HR_ACCOUNT_ID = createField("hr_account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "hr账号ID");

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.status</code>. 绑定状态：0：取消分配，1：已分配
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "绑定状态：0：取消分配，1：已分配");

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.create_time</code>. 分配账号的时间
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "分配账号的时间");

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.update_time</code>. 取消分配账号的时间
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "取消分配账号的时间");

	/**
	 * The column <code>hrdb.hr_third_party_account_hr.channel</code>. 1=51job,2=猎聘,3=智联,4=linkedin
	 */
	public final TableField<HrThirdPartyAccountHrRecord, Short> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.SMALLINT.defaulted(true), this, "1=51job,2=猎聘,3=智联,4=linkedin");

	/**
	 * Create a <code>hrdb.hr_third_party_account_hr</code> table reference
	 */
	public HrThirdPartyAccountHr() {
		this("hr_third_party_account_hr", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_third_party_account_hr</code> table reference
	 */
	public HrThirdPartyAccountHr(String alias) {
		this(alias, HR_THIRD_PARTY_ACCOUNT_HR);
	}

	private HrThirdPartyAccountHr(String alias, Table<HrThirdPartyAccountHrRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrThirdPartyAccountHr(String alias, Table<HrThirdPartyAccountHrRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "第三方账号和hr表关联表，账号分配表，取消分配将status置为0，重新分配不修改记录而是新加分配记录");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrThirdPartyAccountHrRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_THIRD_PARTY_ACCOUNT_HR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrThirdPartyAccountHrRecord> getPrimaryKey() {
		return Keys.KEY_HR_THIRD_PARTY_ACCOUNT_HR_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrThirdPartyAccountHrRecord>> getKeys() {
		return Arrays.<UniqueKey<HrThirdPartyAccountHrRecord>>asList(Keys.KEY_HR_THIRD_PARTY_ACCOUNT_HR_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyAccountHr as(String alias) {
		return new HrThirdPartyAccountHr(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrThirdPartyAccountHr rename(String name) {
		return new HrThirdPartyAccountHr(name, null);
	}
}