/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.logdb.tables;


import com.moseeker.db.logdb.Keys;
import com.moseeker.db.logdb.Logdb;
import com.moseeker.db.logdb.tables.records.LogWxTemplateMessageSendrecordRecord;

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
 * 模板消息发送结果记录
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogWxTemplateMessageSendrecord extends TableImpl<LogWxTemplateMessageSendrecordRecord> {

	private static final long serialVersionUID = -1359980685;

	/**
	 * The reference instance of <code>logDB.log_wx_template_message_sendrecord</code>
	 */
	public static final LogWxTemplateMessageSendrecord LOG_WX_TEMPLATE_MESSAGE_SENDRECORD = new LogWxTemplateMessageSendrecord();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LogWxTemplateMessageSendrecordRecord> getRecordType() {
		return LogWxTemplateMessageSendrecordRecord.class;
	}

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.id</code>. 主key
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.template_id</code>. 我的模板ID
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, UInteger> TEMPLATE_ID = createField("template_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "我的模板ID");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.wechat_id</code>. 所属公众号
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, UInteger> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "所属公众号");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.msgid</code>. 发送消息ID
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, Long> MSGID = createField("msgid", org.jooq.impl.SQLDataType.BIGINT.defaulted(true), this, "发送消息ID");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.open_id</code>. 微信用户OPENID
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "微信用户OPENID");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.url</code>. link
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false), this, "link");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.topcolor</code>. 信息顶部颜色
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> TOPCOLOR = createField("topcolor", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "信息顶部颜色");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.jsondata</code>. 发送的json数据
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> JSONDATA = createField("jsondata", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "发送的json数据");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.errcode</code>. 返回结果值
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, UInteger> ERRCODE = createField("errcode", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "返回结果值");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.errmsg</code>. 返回提示信息
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> ERRMSG = createField("errmsg", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "返回提示信息");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.sendtime</code>. 发送时间
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, Timestamp> SENDTIME = createField("sendtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "发送时间");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.updatetime</code>. 反馈状态时间
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "反馈状态时间");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.sendstatus</code>. 发送状态
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> SENDSTATUS = createField("sendstatus", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "发送状态");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.sendtype</code>. 发送类型 0:微信 1:邮件 2:短信
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, Integer> SENDTYPE = createField("sendtype", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "发送类型 0:微信 1:邮件 2:短信");

	/**
	 * The column <code>logDB.log_wx_template_message_sendrecord.access_token</code>. 发送时的access_token
	 */
	public final TableField<LogWxTemplateMessageSendrecordRecord, String> ACCESS_TOKEN = createField("access_token", org.jooq.impl.SQLDataType.VARCHAR.length(600).nullable(false).defaulted(true), this, "发送时的access_token");

	/**
	 * Create a <code>logDB.log_wx_template_message_sendrecord</code> table reference
	 */
	public LogWxTemplateMessageSendrecord() {
		this("log_wx_template_message_sendrecord", null);
	}

	/**
	 * Create an aliased <code>logDB.log_wx_template_message_sendrecord</code> table reference
	 */
	public LogWxTemplateMessageSendrecord(String alias) {
		this(alias, LOG_WX_TEMPLATE_MESSAGE_SENDRECORD);
	}

	private LogWxTemplateMessageSendrecord(String alias, Table<LogWxTemplateMessageSendrecordRecord> aliased) {
		this(alias, aliased, null);
	}

	private LogWxTemplateMessageSendrecord(String alias, Table<LogWxTemplateMessageSendrecordRecord> aliased, Field<?>[] parameters) {
		super(alias, Logdb.LOGDB, aliased, parameters, "模板消息发送结果记录");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<LogWxTemplateMessageSendrecordRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_LOG_WX_TEMPLATE_MESSAGE_SENDRECORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LogWxTemplateMessageSendrecordRecord> getPrimaryKey() {
		return Keys.KEY_LOG_WX_TEMPLATE_MESSAGE_SENDRECORD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LogWxTemplateMessageSendrecordRecord>> getKeys() {
		return Arrays.<UniqueKey<LogWxTemplateMessageSendrecordRecord>>asList(Keys.KEY_LOG_WX_TEMPLATE_MESSAGE_SENDRECORD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxTemplateMessageSendrecord as(String alias) {
		return new LogWxTemplateMessageSendrecord(alias, this);
	}

	/**
	 * Rename this table
	 */
	public LogWxTemplateMessageSendrecord rename(String name) {
		return new LogWxTemplateMessageSendrecord(name, null);
	}
}
