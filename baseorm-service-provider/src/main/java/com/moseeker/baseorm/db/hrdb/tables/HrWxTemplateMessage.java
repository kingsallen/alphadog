/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;

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
 * 微信模板消息
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxTemplateMessage extends TableImpl<HrWxTemplateMessageRecord> {

	private static final long serialVersionUID = 947867097;

	/**
	 * The reference instance of <code>hrdb.hr_wx_template_message</code>
	 */
	public static final HrWxTemplateMessage HR_WX_TEMPLATE_MESSAGE = new HrWxTemplateMessage();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrWxTemplateMessageRecord> getRecordType() {
		return HrWxTemplateMessageRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_wx_template_message.id</code>. 主key
	 */
	public final TableField<HrWxTemplateMessageRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>hrdb.hr_wx_template_message.sys_template_id</code>. 模板ID
	 */
	public final TableField<HrWxTemplateMessageRecord, Integer> SYS_TEMPLATE_ID = createField("sys_template_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "模板ID");

	/**
	 * The column <code>hrdb.hr_wx_template_message.wx_template_id</code>. 微信模板ID
	 */
	public final TableField<HrWxTemplateMessageRecord, String> WX_TEMPLATE_ID = createField("wx_template_id", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "微信模板ID");

	/**
	 * The column <code>hrdb.hr_wx_template_message.display</code>. 是否显示
	 */
	public final TableField<HrWxTemplateMessageRecord, UInteger> DISPLAY = createField("display", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "是否显示");

	/**
	 * The column <code>hrdb.hr_wx_template_message.priority</code>. 排序
	 */
	public final TableField<HrWxTemplateMessageRecord, UInteger> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "排序");

	/**
	 * The column <code>hrdb.hr_wx_template_message.wechat_id</code>. 所属公众号
	 */
	public final TableField<HrWxTemplateMessageRecord, UInteger> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "所属公众号");

	/**
	 * The column <code>hrdb.hr_wx_template_message.disable</code>. 是否可用
	 */
	public final TableField<HrWxTemplateMessageRecord, UInteger> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "是否可用");

	/**
	 * The column <code>hrdb.hr_wx_template_message.url</code>. 跳转URL
	 */
	public final TableField<HrWxTemplateMessageRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "跳转URL");

	/**
	 * The column <code>hrdb.hr_wx_template_message.topcolor</code>. 消息头部颜色
	 */
	public final TableField<HrWxTemplateMessageRecord, String> TOPCOLOR = createField("topcolor", org.jooq.impl.SQLDataType.VARCHAR.length(10).defaulted(true), this, "消息头部颜色");

	/**
	 * The column <code>hrdb.hr_wx_template_message.first</code>. 问候语
	 */
	public final TableField<HrWxTemplateMessageRecord, String> FIRST = createField("first", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "问候语");

	/**
	 * The column <code>hrdb.hr_wx_template_message.remark</code>. 结束语
	 */
	public final TableField<HrWxTemplateMessageRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(150), this, "结束语");

	/**
	 * Create a <code>hrdb.hr_wx_template_message</code> table reference
	 */
	public HrWxTemplateMessage() {
		this("hr_wx_template_message", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_wx_template_message</code> table reference
	 */
	public HrWxTemplateMessage(String alias) {
		this(alias, HR_WX_TEMPLATE_MESSAGE);
	}

	private HrWxTemplateMessage(String alias, Table<HrWxTemplateMessageRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrWxTemplateMessage(String alias, Table<HrWxTemplateMessageRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "微信模板消息");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrWxTemplateMessageRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_HR_WX_TEMPLATE_MESSAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrWxTemplateMessageRecord> getPrimaryKey() {
		return Keys.KEY_HR_WX_TEMPLATE_MESSAGE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrWxTemplateMessageRecord>> getKeys() {
		return Arrays.<UniqueKey<HrWxTemplateMessageRecord>>asList(Keys.KEY_HR_WX_TEMPLATE_MESSAGE_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessage as(String alias) {
		return new HrWxTemplateMessage(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrWxTemplateMessage rename(String name) {
		return new HrWxTemplateMessage(name, null);
	}
}
