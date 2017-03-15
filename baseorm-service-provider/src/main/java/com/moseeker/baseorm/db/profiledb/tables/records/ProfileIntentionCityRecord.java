/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.profiledb.tables.records;


import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionCity;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * Profile的求职意向-期望城市关系表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileIntentionCityRecord extends UpdatableRecordImpl<ProfileIntentionCityRecord> implements Record4<UInteger, UInteger, UInteger, String> {

	private static final long serialVersionUID = 1840038647;

	/**
	 * Setter for <code>profiledb.profile_intention_city.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>profiledb.profile_intention_city.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>profiledb.profile_intention_city.profile_intention_id</code>. profile_intention.id
	 */
	public void setProfileIntentionId(UInteger value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>profiledb.profile_intention_city.profile_intention_id</code>. profile_intention.id
	 */
	public UInteger getProfileIntentionId() {
		return (UInteger) getValue(1);
	}

	/**
	 * Setter for <code>profiledb.profile_intention_city.city_code</code>. 期望城市字典编码
	 */
	public void setCityCode(UInteger value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>profiledb.profile_intention_city.city_code</code>. 期望城市字典编码
	 */
	public UInteger getCityCode() {
		return (UInteger) getValue(2);
	}

	/**
	 * Setter for <code>profiledb.profile_intention_city.city_name</code>. 期望城市名称
	 */
	public void setCityName(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>profiledb.profile_intention_city.city_name</code>. 期望城市名称
	 */
	public String getCityName() {
		return (String) getValue(3);
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
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, UInteger, UInteger, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, UInteger, UInteger, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return ProfileIntentionCity.PROFILE_INTENTION_CITY.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field2() {
		return ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field3() {
		return ProfileIntentionCity.PROFILE_INTENTION_CITY.CITY_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return ProfileIntentionCity.PROFILE_INTENTION_CITY.CITY_NAME;
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
		return getProfileIntentionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value3() {
		return getCityCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getCityName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileIntentionCityRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileIntentionCityRecord value2(UInteger value) {
		setProfileIntentionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileIntentionCityRecord value3(UInteger value) {
		setCityCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileIntentionCityRecord value4(String value) {
		setCityName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileIntentionCityRecord values(UInteger value1, UInteger value2, UInteger value3, String value4) {
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
	 * Create a detached ProfileIntentionCityRecord
	 */
	public ProfileIntentionCityRecord() {
		super(ProfileIntentionCity.PROFILE_INTENTION_CITY);
	}

	/**
	 * Create a detached, initialised ProfileIntentionCityRecord
	 */
	public ProfileIntentionCityRecord(UInteger id, UInteger profileIntentionId, UInteger cityCode, String cityName) {
		super(ProfileIntentionCity.PROFILE_INTENTION_CITY);

		setValue(0, id);
		setValue(1, profileIntentionId);
		setValue(2, cityCode);
		setValue(3, cityName);
	}
}
