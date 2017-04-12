/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables.records;


import com.moseeker.db.hrdb.tables.HrCmsMedia;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 微信端新jd模块具体内容项
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCmsMediaRecord extends UpdatableRecordImpl<HrCmsMediaRecord> implements Record13<Integer, Integer, String, String, String, String, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

	private static final long serialVersionUID = -1956018581;

	/**
	 * Setter for <code>hrdb.hr_cms_media.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.module_id</code>. 模块id即hr_cms_module.id
	 */
	public void setModuleId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.module_id</code>. 模块id即hr_cms_module.id
	 */
	public Integer getModuleId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.longtexts</code>. 描述
	 */
	public void setLongtexts(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.longtexts</code>. 描述
	 */
	public String getLongtexts() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.attrs</code>. 扩展字段，地图存json
	 */
	public void setAttrs(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.attrs</code>. 扩展字段，地图存json
	 */
	public String getAttrs() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.title</code>. 模板名称
	 */
	public void setTitle(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.title</code>. 模板名称
	 */
	public String getTitle() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.sub_title</code>. 模板子名称
	 */
	public void setSubTitle(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.sub_title</code>. 模板子名称
	 */
	public String getSubTitle() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.link</code>. 模板链接
	 */
	public void setLink(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.link</code>. 模板链接
	 */
	public String getLink() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.res_id</code>. 资源hr_resource.id
	 */
	public void setResId(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.res_id</code>. 资源hr_resource.id
	 */
	public Integer getResId() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.orders</code>. 顺序
	 */
	public void setOrders(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.orders</code>. 顺序
	 */
	public Integer getOrders() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.is_show</code>. 是否显示
	 */
	public void setIsShow(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.is_show</code>. 是否显示
	 */
	public Integer getIsShow() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.disable</code>. 状态 0 是有效 1是无效
	 */
	public void setDisable(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.disable</code>. 状态 0 是有效 1是无效
	 */
	public Integer getDisable() {
		return (Integer) getValue(10);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.create_time</code>.
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(11);
	}

	/**
	 * Setter for <code>hrdb.hr_cms_media.update_time</code>.
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>hrdb.hr_cms_media.update_time</code>.
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(12);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record13 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row13<Integer, Integer, String, String, String, String, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
		return (Row13) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row13<Integer, Integer, String, String, String, String, String, Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
		return (Row13) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrCmsMedia.HR_CMS_MEDIA.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrCmsMedia.HR_CMS_MEDIA.MODULE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return HrCmsMedia.HR_CMS_MEDIA.LONGTEXTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return HrCmsMedia.HR_CMS_MEDIA.ATTRS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return HrCmsMedia.HR_CMS_MEDIA.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return HrCmsMedia.HR_CMS_MEDIA.SUB_TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return HrCmsMedia.HR_CMS_MEDIA.LINK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return HrCmsMedia.HR_CMS_MEDIA.RES_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return HrCmsMedia.HR_CMS_MEDIA.ORDERS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return HrCmsMedia.HR_CMS_MEDIA.IS_SHOW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return HrCmsMedia.HR_CMS_MEDIA.DISABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field12() {
		return HrCmsMedia.HR_CMS_MEDIA.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field13() {
		return HrCmsMedia.HR_CMS_MEDIA.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getModuleId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getLongtexts();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getAttrs();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getSubTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getLink();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getResId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getOrders();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getIsShow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getDisable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value12() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value13() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value2(Integer value) {
		setModuleId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value3(String value) {
		setLongtexts(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value4(String value) {
		setAttrs(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value5(String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value6(String value) {
		setSubTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value7(String value) {
		setLink(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value8(Integer value) {
		setResId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value9(Integer value) {
		setOrders(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value10(Integer value) {
		setIsShow(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value11(Integer value) {
		setDisable(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value12(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord value13(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrCmsMediaRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, String value7, Integer value8, Integer value9, Integer value10, Integer value11, Timestamp value12, Timestamp value13) {
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
		value12(value12);
		value13(value13);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrCmsMediaRecord
	 */
	public HrCmsMediaRecord() {
		super(HrCmsMedia.HR_CMS_MEDIA);
	}

	/**
	 * Create a detached, initialised HrCmsMediaRecord
	 */
	public HrCmsMediaRecord(Integer id, Integer moduleId, String longtexts, String attrs, String title, String subTitle, String link, Integer resId, Integer orders, Integer isShow, Integer disable, Timestamp createTime, Timestamp updateTime) {
		super(HrCmsMedia.HR_CMS_MEDIA);

		setValue(0, id);
		setValue(1, moduleId);
		setValue(2, longtexts);
		setValue(3, attrs);
		setValue(4, title);
		setValue(5, subTitle);
		setValue(6, link);
		setValue(7, resId);
		setValue(8, orders);
		setValue(9, isShow);
		setValue(10, disable);
		setValue(11, createTime);
		setValue(12, updateTime);
	}
}