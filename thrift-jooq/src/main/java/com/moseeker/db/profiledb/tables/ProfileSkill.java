/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profiledb.tables;


import com.moseeker.db.profiledb.Keys;
import com.moseeker.db.profiledb.Profiledb;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;

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
 * Profile的技能
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileSkill extends TableImpl<ProfileSkillRecord> {

	private static final long serialVersionUID = -985970440;

	/**
	 * The reference instance of <code>profileDB.profile_skill</code>
	 */
	public static final ProfileSkill PROFILE_SKILL = new ProfileSkill();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<ProfileSkillRecord> getRecordType() {
		return ProfileSkillRecord.class;
	}

	/**
	 * The column <code>profileDB.profile_skill.id</code>. 主key
	 */
	public final TableField<ProfileSkillRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>profileDB.profile_skill.profile_id</code>. profile.id
	 */
	public final TableField<ProfileSkillRecord, UInteger> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "profile.id");

	/**
	 * The column <code>profileDB.profile_skill.name</code>. 技能名称
	 */
	public final TableField<ProfileSkillRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaulted(true), this, "技能名称");

	/**
	 * The column <code>profileDB.profile_skill.level</code>. 掌握程度 0:未填写 1：掌握 2：熟练 3：精通
	 */
	public final TableField<ProfileSkillRecord, Byte> LEVEL = createField("level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "掌握程度 0:未填写 1：掌握 2：熟练 3：精通");

	/**
	 * The column <code>profileDB.profile_skill.month</code>. 使用/月
	 */
	public final TableField<ProfileSkillRecord, Byte> MONTH = createField("month", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "使用/月");

	/**
	 * The column <code>profileDB.profile_skill.create_time</code>. 创建时间
	 */
	public final TableField<ProfileSkillRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "创建时间");

	/**
	 * The column <code>profileDB.profile_skill.update_time</code>. 更新时间
	 */
	public final TableField<ProfileSkillRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "更新时间");

	/**
	 * Create a <code>profileDB.profile_skill</code> table reference
	 */
	public ProfileSkill() {
		this("profile_skill", null);
	}

	/**
	 * Create an aliased <code>profileDB.profile_skill</code> table reference
	 */
	public ProfileSkill(String alias) {
		this(alias, PROFILE_SKILL);
	}

	private ProfileSkill(String alias, Table<ProfileSkillRecord> aliased) {
		this(alias, aliased, null);
	}

	private ProfileSkill(String alias, Table<ProfileSkillRecord> aliased, Field<?>[] parameters) {
		super(alias, Profiledb.PROFILEDB, aliased, parameters, "Profile的技能");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<ProfileSkillRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_PROFILE_SKILL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<ProfileSkillRecord> getPrimaryKey() {
		return Keys.KEY_PROFILE_SKILL_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<ProfileSkillRecord>> getKeys() {
		return Arrays.<UniqueKey<ProfileSkillRecord>>asList(Keys.KEY_PROFILE_SKILL_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileSkill as(String alias) {
		return new ProfileSkill(alias, this);
	}

	/**
	 * Rename this table
	 */
	public ProfileSkill rename(String name) {
		return new ProfileSkill(name, null);
	}
}
