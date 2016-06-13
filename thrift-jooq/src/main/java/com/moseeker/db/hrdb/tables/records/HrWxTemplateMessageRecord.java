/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrWxTemplateMessage;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;
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
public class HrWxTemplateMessageRecord extends UpdatableRecordImpl<HrWxTemplateMessageRecord> implements Record11<UInteger, Integer, String, UInteger, UInteger, UInteger, UInteger, String, String, String, String> {

	private static final long serialVersionUID = 131419362;

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.sys_template_id</code>. 模板ID
	 */
	public void setSysTemplateId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.sys_template_id</code>. 模板ID
	 */
	public Integer getSysTemplateId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.wx_template_id</code>. 微信模板ID
	 */
	public void setWxTemplateId(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.wx_template_id</code>. 微信模板ID
	 */
	public String getWxTemplateId() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.display</code>. 是否显示
	 */
	public void setDisplay(UInteger value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.display</code>. 是否显示
	 */
	public UInteger getDisplay() {
		return (UInteger) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.priority</code>. 排序
	 */
	public void setPriority(UInteger value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.priority</code>. 排序
	 */
	public UInteger getPriority() {
		return (UInteger) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.wechat_id</code>. 所属公众号
	 */
	public void setWechatId(UInteger value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.wechat_id</code>. 所属公众号
	 */
	public UInteger getWechatId() {
		return (UInteger) getValue(5);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.disable</code>. 是否可用
	 */
	public void setDisable(UInteger value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.disable</code>. 是否可用
	 */
	public UInteger getDisable() {
		return (UInteger) getValue(6);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.url</code>. 跳转URL
	 */
	public void setUrl(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.url</code>. 跳转URL
	 */
	public String getUrl() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.topcolor</code>. 消息头部颜色
	 */
	public void setTopcolor(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.topcolor</code>. 消息头部颜色
	 */
	public String getTopcolor() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.first</code>. 问候语
	 */
	public void setFirst(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.first</code>. 问候语
	 */
	public String getFirst() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>hrdb.hr_wx_template_message.remark</code>. 结束语
	 */
	public void setRemark(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>hrdb.hr_wx_template_message.remark</code>. 结束语
	 */
	public String getRemark() {
		return (String) getValue(10);
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
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<UInteger, Integer, String, UInteger, UInteger, UInteger, UInteger, String, String, String, String> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<UInteger, Integer, String, UInteger, UInteger, UInteger, UInteger, String, String, String, String> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WX_TEMPLATE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field4() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISPLAY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field5() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.PRIORITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field6() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field7() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.TOPCOLOR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.FIRST;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.REMARK;
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
	public Integer value2() {
		return getSysTemplateId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getWxTemplateId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value4() {
		return getDisplay();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value5() {
		return getPriority();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value6() {
		return getWechatId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value7() {
		return getDisable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getTopcolor();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getFirst();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getRemark();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value2(Integer value) {
		setSysTemplateId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value3(String value) {
		setWxTemplateId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value4(UInteger value) {
		setDisplay(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value5(UInteger value) {
		setPriority(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value6(UInteger value) {
		setWechatId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value7(UInteger value) {
		setDisable(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value8(String value) {
		setUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value9(String value) {
		setTopcolor(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value10(String value) {
		setFirst(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord value11(String value) {
		setRemark(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrWxTemplateMessageRecord values(UInteger value1, Integer value2, String value3, UInteger value4, UInteger value5, UInteger value6, UInteger value7, String value8, String value9, String value10, String value11) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrWxTemplateMessageRecord
	 */
	public HrWxTemplateMessageRecord() {
		super(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE);
	}

	/**
	 * Create a detached, initialised HrWxTemplateMessageRecord
	 */
	public HrWxTemplateMessageRecord(UInteger id, Integer sysTemplateId, String wxTemplateId, UInteger display, UInteger priority, UInteger wechatId, UInteger disable, String url, String topcolor, String first, String remark) {
		super(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE);

		setValue(0, id);
		setValue(1, sysTemplateId);
		setValue(2, wxTemplateId);
		setValue(3, display);
		setValue(4, priority);
		setValue(5, wechatId);
		setValue(6, disable);
		setValue(7, url);
		setValue(8, topcolor);
		setValue(9, first);
		setValue(10, remark);
	}
}
