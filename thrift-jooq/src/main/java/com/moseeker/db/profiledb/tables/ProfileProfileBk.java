/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables;


import com.moseeker.db.profiledb.Keys;
import com.moseeker.db.profiledb.Profiledb;
import com.moseeker.db.profiledb.tables.records.ProfileProfileBkRecord;

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
 * 用户profile表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileProfileBk extends TableImpl<ProfileProfileBkRecord> {

	private static final long serialVersionUID = -1859438521;

	/**
	 * The reference instance of <code>profiledb.profile_profile_bk</code>
	 */
	public static final ProfileProfileBk PROFILE_PROFILE_BK = new ProfileProfileBk();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ProfileProfileBkRecord> getRecordType() {
		return ProfileProfileBkRecord.class;
	}

	/**
	 * The column <code>profiledb.profile_profile_bk.id</code>. 主key
	 */
	public final TableField<ProfileProfileBkRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>profiledb.profile_profile_bk.uuid</code>. profile的uuid标识,与主键一一对应
	 */
	public final TableField<ProfileProfileBkRecord, String> UUID = createField("uuid", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaulted(true), this, "profile的uuid标识,与主键一一对应");

	/**
	 * The column <code>profiledb.profile_profile_bk.lang</code>. profile语言 1:chinese 2:english
	 */
	public final TableField<ProfileProfileBkRecord, UByte> LANG = createField("lang", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaulted(true), this, "profile语言 1:chinese 2:english");

	/**
	 * The column <code>profiledb.profile_profile_bk.source</code>. Profile的创建来源, 0:未知, 或者mongo合并来的 1:微信企业端(正常), 2:微信企业端(我要投递), 3:微信企业端(我感兴趣), 4:微信聚合端(正常), 5:微信聚合端(我要投递), 6:微信聚合端(我感兴趣), 100:微信企业端Email申请, 101:微信聚合端Email申请, 150:微信企业端导入, 151:微信聚合端导入, 152:PC导入, 200:PC(正常添加) 201:PC(我要投递) 202: PC(我感兴趣)
	 */
	public final TableField<ProfileProfileBkRecord, UInteger> SOURCE = createField("source", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "Profile的创建来源, 0:未知, 或者mongo合并来的 1:微信企业端(正常), 2:微信企业端(我要投递), 3:微信企业端(我感兴趣), 4:微信聚合端(正常), 5:微信聚合端(我要投递), 6:微信聚合端(我感兴趣), 100:微信企业端Email申请, 101:微信聚合端Email申请, 150:微信企业端导入, 151:微信聚合端导入, 152:PC导入, 200:PC(正常添加) 201:PC(我要投递) 202: PC(我感兴趣)");

	/**
	 * The column <code>profiledb.profile_profile_bk.completeness</code>. Profile完整度
	 */
	public final TableField<ProfileProfileBkRecord, UByte> COMPLETENESS = createField("completeness", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaulted(true), this, "Profile完整度");

	/**
	 * The column <code>profiledb.profile_profile_bk.user_id</code>. 用户ID
	 */
	public final TableField<ProfileProfileBkRecord, UInteger> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "用户ID");

	/**
	 * The column <code>profiledb.profile_profile_bk.disable</code>. 是否有效，0：无效 1：有效
	 */
	public final TableField<ProfileProfileBkRecord, UByte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINTUNSIGNED.nullable(false).defaulted(true), this, "是否有效，0：无效 1：有效");

	/**
	 * The column <code>profiledb.profile_profile_bk.create_time</code>. 创建时间
	 */
	public final TableField<ProfileProfileBkRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>profiledb.profile_profile_bk.update_time</code>. 更新时间
	 */
	public final TableField<ProfileProfileBkRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "更新时间");

	/**
	 * Create a <code>profiledb.profile_profile_bk</code> table reference
	 */
	public ProfileProfileBk() {
		this("profile_profile_bk", null);
	}

	/**
	 * Create an aliased <code>profiledb.profile_profile_bk</code> table reference
	 */
	public ProfileProfileBk(String alias) {
		this(alias, PROFILE_PROFILE_BK);
	}

	private ProfileProfileBk(String alias, Table<ProfileProfileBkRecord> aliased) {
		this(alias, aliased, null);
	}

	private ProfileProfileBk(String alias, Table<ProfileProfileBkRecord> aliased, Field<?>[] parameters) {
		super(alias, Profiledb.PROFILEDB, aliased, parameters, "用户profile表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ProfileProfileBkRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_PROFILE_PROFILE_BK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ProfileProfileBkRecord> getPrimaryKey() {
		return Keys.KEY_PROFILE_PROFILE_BK_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ProfileProfileBkRecord>> getKeys() {
		return Arrays.<UniqueKey<ProfileProfileBkRecord>>asList(Keys.KEY_PROFILE_PROFILE_BK_PRIMARY, Keys.KEY_PROFILE_PROFILE_BK_UK_PROFILE_USERID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileProfileBk as(String alias) {
		return new ProfileProfileBk(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ProfileProfileBk rename(String name) {
		return new ProfileProfileBk(name, null);
	}
}