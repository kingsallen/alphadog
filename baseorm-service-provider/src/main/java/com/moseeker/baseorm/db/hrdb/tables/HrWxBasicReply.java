/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxBasicReplyRecord;

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
 * 微信文本回复表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxBasicReply extends TableImpl<HrWxBasicReplyRecord> {

	private static final long serialVersionUID = 742960850;

	/**
	 * The reference instance of <code>hrdb.hr_wx_basic_reply</code>
	 */
	public static final HrWxBasicReply HR_WX_BASIC_REPLY = new HrWxBasicReply();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrWxBasicReplyRecord> getRecordType() {
		return HrWxBasicReplyRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_wx_basic_reply.id</code>.
	 */
	public final TableField<HrWxBasicReplyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_wx_basic_reply.rid</code>.
	 */
	public final TableField<HrWxBasicReplyRecord, Integer> RID = createField("rid", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_wx_basic_reply.content</code>. 文本回复内容
	 */
	public final TableField<HrWxBasicReplyRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "文本回复内容");

	/**
	 * Create a <code>hrdb.hr_wx_basic_reply</code> table reference
	 */
	public HrWxBasicReply() {
		this("hr_wx_basic_reply", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_wx_basic_reply</code> table reference
	 */
	public HrWxBasicReply(String alias) {
		this(alias, HR_WX_BASIC_REPLY);
	}

	private HrWxBasicReply(String alias, Table<HrWxBasicReplyRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrWxBasicReply(String alias, Table<HrWxBasicReplyRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "微信文本回复表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrWxBasicReplyRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_WX_BASIC_REPLY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrWxBasicReplyRecord> getPrimaryKey() {
		return Keys.KEY_HR_WX_BASIC_REPLY_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrWxBasicReplyRecord>> getKeys() {
		return Arrays.<UniqueKey<HrWxBasicReplyRecord>>asList(Keys.KEY_HR_WX_BASIC_REPLY_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxBasicReply as(String alias) {
		return new HrWxBasicReply(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrWxBasicReply rename(String name) {
		return new HrWxBasicReply(name, null);
	}
}
