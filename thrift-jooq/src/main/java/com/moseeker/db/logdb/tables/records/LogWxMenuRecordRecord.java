/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.logdb.tables.records;


import com.moseeker.db.logdb.tables.LogWxMenuRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 微信菜单操作日志表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogWxMenuRecordRecord extends UpdatableRecordImpl<LogWxMenuRecordRecord> implements Record7<UInteger, UInteger, String, String, Timestamp, UInteger, String> {

	private static final long serialVersionUID = 1669791894;

	/**
	 * Setter for <code>logDB.log_wx_menu_record.id</code>.
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record.id</code>.
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>logDB.log_wx_menu_record.wechat_id</code>.
	 */
	public void setWechatId(UInteger value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record.wechat_id</code>.
	 */
	public UInteger getWechatId() {
		return (UInteger) getValue(1);
	}

	/**
	 * Setter for <code>logDB.log_wx_menu_record.name</code>.
	 */
	public void setName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record.name</code>.
	 */
	public String getName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>logDB.log_wx_menu_record.json</code>. 菜单的json数据
	 */
	public void setJson(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record.json</code>. 菜单的json数据
	 */
	public String getJson() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>logDB.log_wx_menu_record._create_time</code>.
	 */
	public void set_CreateTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record._create_time</code>.
	 */
	public Timestamp get_CreateTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>logDB.log_wx_menu_record.errcode</code>. 微信调用返回的errcode
	 */
	public void setErrcode(UInteger value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record.errcode</code>. 微信调用返回的errcode
	 */
	public UInteger getErrcode() {
		return (UInteger) getValue(5);
	}

	/**
	 * Setter for <code>logDB.log_wx_menu_record.errmsg</code>. 微信调用返回的errmsg
	 */
	public void setErrmsg(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>logDB.log_wx_menu_record.errmsg</code>. 微信调用返回的errmsg
	 */
	public String getErrmsg() {
		return (String) getValue(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<UInteger> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<UInteger, UInteger, String, String, Timestamp, UInteger, String> fieldsRow() {
		return (Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row7<UInteger, UInteger, String, String, Timestamp, UInteger, String> valuesRow() {
		return (Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field2() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD.WECHAT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD.JSON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD._CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field6() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD.ERRCODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return LogWxMenuRecord.LOG_WX_MENU_RECORD.ERRMSG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value2() {
		return getWechatId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getJson();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return get_CreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value6() {
		return getErrcode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getErrmsg();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value2(UInteger value) {
		setWechatId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value3(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value4(String value) {
		setJson(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value5(Timestamp value) {
		set_CreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value6(UInteger value) {
		setErrcode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord value7(String value) {
		setErrmsg(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogWxMenuRecordRecord values(UInteger value1, UInteger value2, String value3, String value4, Timestamp value5, UInteger value6, String value7) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached LogWxMenuRecordRecord
	 */
	public LogWxMenuRecordRecord() {
		super(LogWxMenuRecord.LOG_WX_MENU_RECORD);
	}

	/**
	 * Create a detached, initialised LogWxMenuRecordRecord
	 */
	public LogWxMenuRecordRecord(UInteger id, UInteger wechatId, String name, String json, Timestamp _CreateTime, UInteger errcode, String errmsg) {
		super(LogWxMenuRecord.LOG_WX_MENU_RECORD);

		setValue(0, id);
		setValue(1, wechatId);
		setValue(2, name);
		setValue(3, json);
		setValue(4, _CreateTime);
		setValue(5, errcode);
		setValue(6, errmsg);
	}
}
