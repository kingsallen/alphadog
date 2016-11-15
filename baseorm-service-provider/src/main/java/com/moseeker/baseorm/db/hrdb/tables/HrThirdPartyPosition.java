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
import org.jooq.types.UInteger;

import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;


/**
 * 第三方渠道同步的职位
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyPosition extends TableImpl<HrThirdPartyPositionRecord> {

	private static final long serialVersionUID = 143202898;

	/**
	 * The reference instance of <code>hrdb.hr_third_party_position</code>
	 */
	public static final HrThirdPartyPosition HR_THIRD_PARTY_POSITION = new HrThirdPartyPosition();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrThirdPartyPositionRecord> getRecordType() {
		return HrThirdPartyPositionRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_third_party_position.id</code>.
	 */
	public final TableField<HrThirdPartyPositionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_third_party_position.position_id</code>. jobdb.job_position.id
	 */
	public final TableField<HrThirdPartyPositionRecord, UInteger> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "jobdb.job_position.id");

	/**
	 * The column <code>hrdb.hr_third_party_position.third_part_position_id</code>. 第三方渠道编号
	 */
	public final TableField<HrThirdPartyPositionRecord, String> THIRD_PART_POSITION_ID = createField("third_part_position_id", org.jooq.impl.SQLDataType.VARCHAR.length(40).defaulted(true), this, "第三方渠道编号");

	/**
	 * The column <code>hrdb.hr_third_party_position.channel</code>. 1=51job,2=猎聘,3=智联,4=linkedin
	 */
	public final TableField<HrThirdPartyPositionRecord, Short> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.SMALLINT.defaulted(true), this, "1=51job,2=猎聘,3=智联,4=linkedin");

	/**
	 * The column <code>hrdb.hr_third_party_position.is_synchronization</code>. 是否同步:0=未同步,1=同步,2=同步中，3=同步失败
	 */
	public final TableField<HrThirdPartyPositionRecord, Short> IS_SYNCHRONIZATION = createField("is_synchronization", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaulted(true), this, "是否同步:0=未同步,1=同步,2=同步中，3=同步失败");

	/**
	 * The column <code>hrdb.hr_third_party_position.is_refresh</code>. 是否刷新:0=未刷新,1=刷新,2=刷新中
	 */
	public final TableField<HrThirdPartyPositionRecord, Short> IS_REFRESH = createField("is_refresh", org.jooq.impl.SQLDataType.SMALLINT.defaulted(true), this, "是否刷新:0=未刷新,1=刷新,2=刷新中");

	/**
	 * The column <code>hrdb.hr_third_party_position.sync_time</code>. 职位同步时间
	 */
	public final TableField<HrThirdPartyPositionRecord, Timestamp> SYNC_TIME = createField("sync_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "职位同步时间");

	/**
	 * The column <code>hrdb.hr_third_party_position.refresh_time</code>. 职位刷新时间
	 */
	public final TableField<HrThirdPartyPositionRecord, Timestamp> REFRESH_TIME = createField("refresh_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "职位刷新时间");

	/**
	 * The column <code>hrdb.hr_third_party_position.update_time</code>. 数据更新时间
	 */
	public final TableField<HrThirdPartyPositionRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "数据更新时间");

	/**
	 * Create a <code>hrdb.hr_third_party_position</code> table reference
	 */
	public HrThirdPartyPosition() {
		this("hr_third_party_position", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_third_party_position</code> table reference
	 */
	public HrThirdPartyPosition(String alias) {
		this(alias, HR_THIRD_PARTY_POSITION);
	}

	private HrThirdPartyPosition(String alias, Table<HrThirdPartyPositionRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrThirdPartyPosition(String alias, Table<HrThirdPartyPositionRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "第三方渠道同步的职位");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrThirdPartyPositionRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_THIRD_PARTY_POSITION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrThirdPartyPositionRecord> getPrimaryKey() {
		return Keys.KEY_HR_THIRD_PARTY_POSITION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrThirdPartyPositionRecord>> getKeys() {
		return Arrays.<UniqueKey<HrThirdPartyPositionRecord>>asList(Keys.KEY_HR_THIRD_PARTY_POSITION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrThirdPartyPosition as(String alias) {
		return new HrThirdPartyPosition(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrThirdPartyPosition rename(String name) {
		return new HrThirdPartyPosition(name, null);
	}
}
