/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables;


import com.moseeker.db.hrdb.Hrdb;
import com.moseeker.db.hrdb.Keys;
import com.moseeker.db.hrdb.tables.records.HrTopicRecord;

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
 * 雇主主题活动表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTopic extends TableImpl<HrTopicRecord> {

	private static final long serialVersionUID = 702039881;

	/**
	 * The reference instance of <code>hrDB.hr_topic</code>
	 */
	public static final HrTopic HR_TOPIC = new HrTopic();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrTopicRecord> getRecordType() {
		return HrTopicRecord.class;
	}

	/**
	 * The column <code>hrDB.hr_topic.id</code>.
	 */
	public final TableField<HrTopicRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrDB.hr_topic.company_id</code>. company.id, 部门ID
	 */
	public final TableField<HrTopicRecord, UInteger> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "company.id, 部门ID");

	/**
	 * The column <code>hrDB.hr_topic.share_title</code>. 分享标题
	 */
	public final TableField<HrTopicRecord, String> SHARE_TITLE = createField("share_title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "分享标题");

	/**
	 * The column <code>hrDB.hr_topic.share_logo</code>. 分享LOGO的相对路径
	 */
	public final TableField<HrTopicRecord, String> SHARE_LOGO = createField("share_logo", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "分享LOGO的相对路径");

	/**
	 * The column <code>hrDB.hr_topic.share_description</code>. 分享描述
	 */
	public final TableField<HrTopicRecord, String> SHARE_DESCRIPTION = createField("share_description", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "分享描述");

	/**
	 * The column <code>hrDB.hr_topic.style_id</code>. wx_group_user.id， 推荐者微信ID
	 */
	public final TableField<HrTopicRecord, UInteger> STYLE_ID = createField("style_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "wx_group_user.id， 推荐者微信ID");

	/**
	 * The column <code>hrDB.hr_topic.creator</code>. hr_account.id
	 */
	public final TableField<HrTopicRecord, UInteger> CREATOR = createField("creator", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "hr_account.id");

	/**
	 * The column <code>hrDB.hr_topic.disable</code>. 是否有效  0：有效 1：无效
	 */
	public final TableField<HrTopicRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否有效  0：有效 1：无效");

	/**
	 * The column <code>hrDB.hr_topic.create_time</code>.
	 */
	public final TableField<HrTopicRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrDB.hr_topic.update_time</code>.
	 */
	public final TableField<HrTopicRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>hrDB.hr_topic</code> table reference
	 */
	public HrTopic() {
		this("hr_topic", null);
	}

	/**
	 * Create an aliased <code>hrDB.hr_topic</code> table reference
	 */
	public HrTopic(String alias) {
		this(alias, HR_TOPIC);
	}

	private HrTopic(String alias, Table<HrTopicRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrTopic(String alias, Table<HrTopicRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "雇主主题活动表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrTopicRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_TOPIC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrTopicRecord> getPrimaryKey() {
		return Keys.KEY_HR_TOPIC_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrTopicRecord>> getKeys() {
		return Arrays.<UniqueKey<HrTopicRecord>>asList(Keys.KEY_HR_TOPIC_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrTopic as(String alias) {
		return new HrTopic(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrTopic rename(String name) {
		return new HrTopic(name, null);
	}
}
