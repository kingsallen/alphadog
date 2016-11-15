/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record16;
import org.jooq.Row16;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;

import com.moseeker.baseorm.db.hrdb.tables.HrChildCompany;


/**
 * 子公司表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrChildCompanyRecord extends UpdatableRecordImpl<HrChildCompanyRecord> implements Record16<Integer, String, String, UByte, String, String, String, String, String, String, Byte, String, String, UInteger, String, UInteger> {

	private static final long serialVersionUID = -703622263;

	/**
	 * Setter for <code>hrdb.hr_child_company.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.name</code>.
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.name</code>.
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.ename</code>.
	 */
	public void setEname(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.ename</code>.
	 */
	public String getEname() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.status</code>. 0:onuse 1:unused
	 */
	public void setStatus(UByte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.status</code>. 0:onuse 1:unused
	 */
	public UByte getStatus() {
		return (UByte) getValue(3);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.CEO</code>. CEO
	 */
	public void setCeo(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.CEO</code>. CEO
	 */
	public String getCeo() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.introduction</code>. introduction
	 */
	public void setIntroduction(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.introduction</code>. introduction
	 */
	public String getIntroduction() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.scale</code>. people number of the company
	 */
	public void setScale(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.scale</code>. people number of the company
	 */
	public String getScale() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.province</code>. province
	 */
	public void setProvince(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.province</code>. province
	 */
	public String getProvince() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.city</code>. city
	 */
	public void setCity(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.city</code>. city
	 */
	public String getCity() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.address</code>. address
	 */
	public void setAddress(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.address</code>. address
	 */
	public String getAddress() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.property</code>. 0:国有1:三资2:集体3:私有
	 */
	public void setProperty(Byte value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.property</code>. 0:国有1:三资2:集体3:私有
	 */
	public Byte getProperty() {
		return (Byte) getValue(10);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.business</code>. business
	 */
	public void setBusiness(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.business</code>. business
	 */
	public String getBusiness() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.establish_time</code>. 公司成立时间
	 */
	public void setEstablishTime(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.establish_time</code>. 公司成立时间
	 */
	public String getEstablishTime() {
		return (String) getValue(12);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.parent_id</code>. 上级公司
	 */
	public void setParentId(UInteger value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.parent_id</code>. 上级公司
	 */
	public UInteger getParentId() {
		return (UInteger) getValue(13);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.homepage</code>. company home page
	 */
	public void setHomepage(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.homepage</code>. company home page
	 */
	public String getHomepage() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>hrdb.hr_child_company.company_id</code>. hr_company.id
	 */
	public void setCompanyId(UInteger value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>hrdb.hr_child_company.company_id</code>. hr_company.id
	 */
	public UInteger getCompanyId() {
		return (UInteger) getValue(15);
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
	// Record16 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row16<Integer, String, String, UByte, String, String, String, String, String, String, Byte, String, String, UInteger, String, UInteger> fieldsRow() {
		return (Row16) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row16<Integer, String, String, UByte, String, String, String, String, String, String, Byte, String, String, UInteger, String, UInteger> valuesRow() {
		return (Row16) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return HrChildCompany.HR_CHILD_COMPANY.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return HrChildCompany.HR_CHILD_COMPANY.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return HrChildCompany.HR_CHILD_COMPANY.ENAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UByte> field4() {
		return HrChildCompany.HR_CHILD_COMPANY.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return HrChildCompany.HR_CHILD_COMPANY.CEO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return HrChildCompany.HR_CHILD_COMPANY.INTRODUCTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return HrChildCompany.HR_CHILD_COMPANY.SCALE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return HrChildCompany.HR_CHILD_COMPANY.PROVINCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field9() {
		return HrChildCompany.HR_CHILD_COMPANY.CITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return HrChildCompany.HR_CHILD_COMPANY.ADDRESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field11() {
		return HrChildCompany.HR_CHILD_COMPANY.PROPERTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return HrChildCompany.HR_CHILD_COMPANY.BUSINESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field13() {
		return HrChildCompany.HR_CHILD_COMPANY.ESTABLISH_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field14() {
		return HrChildCompany.HR_CHILD_COMPANY.PARENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field15() {
		return HrChildCompany.HR_CHILD_COMPANY.HOMEPAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field16() {
		return HrChildCompany.HR_CHILD_COMPANY.COMPANY_ID;
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
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getEname();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UByte value4() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getCeo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getIntroduction();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getScale();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getProvince();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value9() {
		return getCity();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value11() {
		return getProperty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getBusiness();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value13() {
		return getEstablishTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value14() {
		return getParentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value15() {
		return getHomepage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value16() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value2(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value3(String value) {
		setEname(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value4(UByte value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value5(String value) {
		setCeo(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value6(String value) {
		setIntroduction(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value7(String value) {
		setScale(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value8(String value) {
		setProvince(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value9(String value) {
		setCity(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value10(String value) {
		setAddress(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value11(Byte value) {
		setProperty(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value12(String value) {
		setBusiness(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value13(String value) {
		setEstablishTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value14(UInteger value) {
		setParentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value15(String value) {
		setHomepage(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord value16(UInteger value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChildCompanyRecord values(Integer value1, String value2, String value3, UByte value4, String value5, String value6, String value7, String value8, String value9, String value10, Byte value11, String value12, String value13, UInteger value14, String value15, UInteger value16) {
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
		value14(value14);
		value15(value15);
		value16(value16);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrChildCompanyRecord
	 */
	public HrChildCompanyRecord() {
		super(HrChildCompany.HR_CHILD_COMPANY);
	}

	/**
	 * Create a detached, initialised HrChildCompanyRecord
	 */
	public HrChildCompanyRecord(Integer id, String name, String ename, UByte status, String ceo, String introduction, String scale, String province, String city, String address, Byte property, String business, String establishTime, UInteger parentId, String homepage, UInteger companyId) {
		super(HrChildCompany.HR_CHILD_COMPANY);

		setValue(0, id);
		setValue(1, name);
		setValue(2, ename);
		setValue(3, status);
		setValue(4, ceo);
		setValue(5, introduction);
		setValue(6, scale);
		setValue(7, province);
		setValue(8, city);
		setValue(9, address);
		setValue(10, property);
		setValue(11, business);
		setValue(12, establishTime);
		setValue(13, parentId);
		setValue(14, homepage);
		setValue(15, companyId);
	}
}
