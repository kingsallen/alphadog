/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables;


import com.moseeker.db.profiledb.Keys;
import com.moseeker.db.profiledb.Profiledb;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;

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
import org.jooq.types.UByte;
import org.jooq.types.UInteger;


/**
 * Profile的求职意向
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileIntention extends TableImpl<ProfileIntentionRecord> {

	private static final long serialVersionUID = -289029641;

	/**
	 * The reference instance of <code>profileDB.profile_intention</code>
	 */
	public static final ProfileIntention PROFILE_INTENTION = new ProfileIntention();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ProfileIntentionRecord> getRecordType() {
		return ProfileIntentionRecord.class;
	}

	/**
	 * The column <code>profileDB.profile_intention.id</code>. 主key
	 */
	public final TableField<ProfileIntentionRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>profileDB.profile_intention.profile_id</code>. profile.id
	 */
	public final TableField<ProfileIntentionRecord, UInteger> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "profile.id");

	/**
	 * The column <code>profileDB.profile_intention.workstate</code>. 当前是否在职状态, 0:未填写 1: 在职，看看新机会, 2: 在职，急寻新工作, 3:在职，暂无跳槽打算, 4:离职，正在找工作, 5:应届毕业生
	 */
	public final TableField<ProfileIntentionRecord, UByte> WORKSTATE = createField("workstate", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaulted(true), this, "当前是否在职状态, 0:未填写 1: 在职，看看新机会, 2: 在职，急寻新工作, 3:在职，暂无跳槽打算, 4:离职，正在找工作, 5:应届毕业生");

	/**
	 * The column <code>profileDB.profile_intention.salary_type</code>. 薪资类型，0:没选择, 1:年薪, 2:月薪, 3:日薪, 4:时薪
	 */
	public final TableField<ProfileIntentionRecord, UByte> SALARY_TYPE = createField("salary_type", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaulted(true), this, "薪资类型，0:没选择, 1:年薪, 2:月薪, 3:日薪, 4:时薪");

	/**
	 * The column <code>profileDB.profile_intention.salary_code</code>. 薪资code
	 */
	public final TableField<ProfileIntentionRecord, UByte> SALARY_CODE = createField("salary_code", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaulted(true), this, "薪资code");

	/**
	 * The column <code>profileDB.profile_intention.tag</code>. 关键词，单个tag最多100个字符，以#隔开
	 */
	public final TableField<ProfileIntentionRecord, String> TAG = createField("tag", org.jooq.impl.SQLDataType.VARCHAR.length(1010).nullable(false).defaulted(true), this, "关键词，单个tag最多100个字符，以#隔开");

	/**
	 * The column <code>profileDB.profile_intention.consider_venture_company_opportunities</code>. 是否考虑创业公司机会 0：未填写 1:考虑 2:不考虑
	 */
	public final TableField<ProfileIntentionRecord, Byte> CONSIDER_VENTURE_COMPANY_OPPORTUNITIES = createField("consider_venture_company_opportunities", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否考虑创业公司机会 0：未填写 1:考虑 2:不考虑");

	/**
	 * The column <code>profileDB.profile_intention.create_time</code>. 创建时间
	 */
	public final TableField<ProfileIntentionRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>profileDB.profile_intention.update_time</code>. 更新时间
	 */
	public final TableField<ProfileIntentionRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "更新时间");

	/**
	 * Create a <code>profileDB.profile_intention</code> table reference
	 */
	public ProfileIntention() {
		this("profile_intention", null);
	}

	/**
	 * Create an aliased <code>profileDB.profile_intention</code> table reference
	 */
	public ProfileIntention(String alias) {
		this(alias, PROFILE_INTENTION);
	}

	private ProfileIntention(String alias, Table<ProfileIntentionRecord> aliased) {
		this(alias, aliased, null);
	}

	private ProfileIntention(String alias, Table<ProfileIntentionRecord> aliased, Field<?>[] parameters) {
		super(alias, Profiledb.PROFILEDB, aliased, parameters, "Profile的求职意向");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ProfileIntentionRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_PROFILE_INTENTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ProfileIntentionRecord> getPrimaryKey() {
		return Keys.KEY_PROFILE_INTENTION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ProfileIntentionRecord>> getKeys() {
		return Arrays.<UniqueKey<ProfileIntentionRecord>>asList(Keys.KEY_PROFILE_INTENTION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileIntention as(String alias) {
		return new ProfileIntention(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ProfileIntention rename(String name) {
		return new ProfileIntention(name, null);
	}
}
