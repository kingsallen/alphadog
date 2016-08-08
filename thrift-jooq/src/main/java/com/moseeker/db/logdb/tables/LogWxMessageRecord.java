/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.logdb.tables;


import com.moseeker.db.logdb.Keys;
import com.moseeker.db.logdb.Logdb;
import com.moseeker.db.logdb.tables.records.LogWxMessageRecordRecord;

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
 * 模板消息发送结果记录表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogWxMessageRecord extends TableImpl<LogWxMessageRecordRecord> {

	private static final long serialVersionUID = -318321271;

	/**
	 * The reference instance of <code>logdb.log_wx_message_record</code>
	 */
	public static final LogWxMessageRecord LOG_WX_MESSAGE_RECORD = new LogWxMessageRecord();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LogWxMessageRecordRecord> getRecordType() {
		return LogWxMessageRecordRecord.class;
	}

	/**
	 * The column <code>logdb.log_wx_message_record.id</code>. 主key
	 */
	public final TableField<LogWxMessageRecordRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>logdb.log_wx_message_record.template_id</code>. 我的模板ID
	 */
	public final TableField<LogWxMessageRecordRecord, UInteger> TEMPLATE_ID = createField("template_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "我的模板ID");

	/**
	 * The column <code>logdb.log_wx_message_record.wechat_id</code>. 所属公众号
	 */
	public final TableField<LogWxMessageRecordRecord, UInteger> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "所属公众号");

	/**
	 * The column <code>logdb.log_wx_message_record.msgid</code>. 发送消息ID
	 */
	public final TableField<LogWxMessageRecordRecord, Long> MSGID = createField("msgid", org.jooq.impl.SQLDataType.BIGINT.defaulted(true), this, "发送消息ID");

	/**
	 * The column <code>logdb.log_wx_message_record.open_id</code>. 微信用户OPENID
	 */
	public final TableField<LogWxMessageRecordRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false), this, "微信用户OPENID");

	/**
	 * The column <code>logdb.log_wx_message_record.url</code>. link
	 */
	public final TableField<LogWxMessageRecordRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false), this, "link");

	/**
	 * The column <code>logdb.log_wx_message_record.topcolor</code>. 信息顶部颜色
	 */
	public final TableField<LogWxMessageRecordRecord, String> TOPCOLOR = createField("topcolor", org.jooq.impl.SQLDataType.VARCHAR.length(10).nullable(false), this, "信息顶部颜色");

	/**
	 * The column <code>logdb.log_wx_message_record.jsondata</code>. 发送的json数据
	 */
	public final TableField<LogWxMessageRecordRecord, String> JSONDATA = createField("jsondata", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "发送的json数据");

	/**
	 * The column <code>logdb.log_wx_message_record.errcode</code>. 返回结果值
	 */
	public final TableField<LogWxMessageRecordRecord, UInteger> ERRCODE = createField("errcode", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "返回结果值");

	/**
	 * The column <code>logdb.log_wx_message_record.errmsg</code>. 返回提示信息
	 */
	public final TableField<LogWxMessageRecordRecord, String> ERRMSG = createField("errmsg", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "返回提示信息");

	/**
	 * The column <code>logdb.log_wx_message_record.sendtime</code>. 发送时间
	 */
	public final TableField<LogWxMessageRecordRecord, Timestamp> SENDTIME = createField("sendtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "发送时间");

	/**
	 * The column <code>logdb.log_wx_message_record.updatetime</code>. 反馈状态时间
	 */
	public final TableField<LogWxMessageRecordRecord, Timestamp> UPDATETIME = createField("updatetime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "反馈状态时间");

	/**
	 * The column <code>logdb.log_wx_message_record.sendstatus</code>. 发送状态
	 */
	public final TableField<LogWxMessageRecordRecord, String> SENDSTATUS = createField("sendstatus", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "发送状态");

	/**
	 * The column <code>logdb.log_wx_message_record.sendtype</code>. 发送类型 0:微信 1:邮件 2:短信
	 */
	public final TableField<LogWxMessageRecordRecord, Integer> SENDTYPE = createField("sendtype", org.jooq.impl.SQLDataType.INTEGER.defaulted(true), this, "发送类型 0:微信 1:邮件 2:短信");

	/**
	 * The column <code>logdb.log_wx_message_record.access_token</code>. 发送时的access_token
	 */
	public final TableField<LogWxMessageRecordRecord, String> ACCESS_TOKEN = createField("access_token", org.jooq.impl.SQLDataType.VARCHAR.length(600).nullable(false).defaulted(true), this, "发送时的access_token");

	/**
	 * Create a <code>logdb.log_wx_message_record</code> table reference
	 */
	public LogWxMessageRecord() {
		this("log_wx_message_record", null);
	}

	/**
	 * Create an aliased <code>logdb.log_wx_message_record</code> table reference
	 */
	public LogWxMessageRecord(String alias) {
		this(alias, LOG_WX_MESSAGE_RECORD);
	}

	private LogWxMessageRecord(String alias, Table<LogWxMessageRecordRecord> aliased) {
		this(alias, aliased, null);
	}

	private LogWxMessageRecord(String alias, Table<LogWxMessageRecordRecord> aliased, Field<?>[] parameters) {
		super(alias, Logdb.LOGDB, aliased, parameters, "模板消息发送结果记录表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<LogWxMessageRecordRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_LOG_WX_MESSAGE_RECORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LogWxMessageRecordRecord> getPrimaryKey() {
		return Keys.KEY_LOG_WX_MESSAGE_RECORD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LogWxMessageRecordRecord>> getKeys() {
		return Arrays.<UniqueKey<LogWxMessageRecordRecord>>asList(Keys.KEY_LOG_WX_MESSAGE_RECORD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMessageRecord as(String alias) {
		return new LogWxMessageRecord(alias, this);
	}

	/**
	 * Rename this table
	 */
	public LogWxMessageRecord rename(String name) {
		return new LogWxMessageRecord(name, null);
	}
}
