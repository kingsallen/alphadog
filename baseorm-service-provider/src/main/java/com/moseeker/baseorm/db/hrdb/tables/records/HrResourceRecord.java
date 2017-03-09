/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrResource;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 资源集合表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrResourceRecord extends UpdatableRecordImpl<HrResourceRecord> implements Record4<Integer, String, Integer, String> {

	private static final long serialVersionUID = 735402184;

	/**
	 * Setter for <code>hrdb.hr_resource.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_resource.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_resource.res_url</code>. 资源链接
	 */
	public void setResUrl(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_resource.res_url</code>. 资源链接
	 */
	public String getResUrl() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_resource.res_type</code>. 0: image  1: video
	 */
	public void setResType(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_resource.res_type</code>. 0: image  1: video
	 */
	public Integer getResType() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_resource.remark</code>. 备注资源
	 */
	public void setRemark(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_resource.remark</code>. 备注资源
	 */
	public String getRemark() {
		return (String) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, Integer, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, String, Integer, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrResource.HR_RESOURCE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return HrResource.HR_RESOURCE.RES_URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return HrResource.HR_RESOURCE.RES_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return HrResource.HR_RESOURCE.REMARK;
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
	public String value2() {
		return getResUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getResType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getRemark();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrResourceRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrResourceRecord value2(String value) {
		setResUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrResourceRecord value3(Integer value) {
		setResType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrResourceRecord value4(String value) {
		setRemark(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrResourceRecord values(Integer value1, String value2, Integer value3, String value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrResourceRecord
	 */
	public HrResourceRecord() {
		super(HrResource.HR_RESOURCE);
	}

	/**
	 * Create a detached, initialised HrResourceRecord
	 */
	public HrResourceRecord(Integer id, String resUrl, Integer resType, String remark) {
		super(HrResource.HR_RESOURCE);

		setValue(0, id);
		setValue(1, resUrl);
		setValue(2, resType);
		setValue(3, remark);
	}
}
