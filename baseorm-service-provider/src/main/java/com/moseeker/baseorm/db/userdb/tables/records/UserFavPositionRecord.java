/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserFavPosition;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户职位收藏
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserFavPositionRecord extends UpdatableRecordImpl<UserFavPositionRecord> implements Record10<Integer, Integer, Byte, Timestamp, Timestamp, String, Integer, Integer, Integer, Integer> {

	private static final long serialVersionUID = 1405341208;

	/**
	 * Setter for <code>userdb.user_fav_position.sysuser_id</code>. 用户ID
	 */
	public void setSysuserId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.sysuser_id</code>. 用户ID
	 */
	public Integer getSysuserId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.position_id</code>. 职位ID
	 */
	public void setPositionId(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.position_id</code>. 职位ID
	 */
	public Integer getPositionId() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.favorite</code>. 0:收藏, 1:取消收藏, 2:感兴趣
	 */
	public void setFavorite(Byte value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.favorite</code>. 0:收藏, 1:取消收藏, 2:感兴趣
	 */
	public Byte getFavorite() {
		return (Byte) getValue(2);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.create_time</code>.
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.update_time</code>.
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.update_time</code>.
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(4);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.mobile</code>. 感兴趣的手机号
	 */
	public void setMobile(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.mobile</code>. 感兴趣的手机号
	 */
	public String getMobile() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.id</code>. ID
	 */
	public void setId(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.id</code>. ID
	 */
	public Integer getId() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.wxuser_id</code>. wx_user.id
	 */
	public void setWxuserId(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.wxuser_id</code>. wx_user.id
	 */
	public Integer getWxuserId() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.recom_id</code>. 推荐者 fk:wx_user.id。已经废弃，推荐者微信编号由推荐者C端账号编号替代，请参考recom_user_id
	 */
	public void setRecomId(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.recom_id</code>. 推荐者 fk:wx_user.id。已经废弃，推荐者微信编号由推荐者C端账号编号替代，请参考recom_user_id
	 */
	public Integer getRecomId() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>userdb.user_fav_position.recom_user_id</code>. userdb.user_user.id 推荐者C端账号编号
	 */
	public void setRecomUserId(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>userdb.user_fav_position.recom_user_id</code>. userdb.user_user.id 推荐者C端账号编号
	 */
	public Integer getRecomUserId() {
		return (Integer) getValue(9);
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
	// Record10 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<Integer, Integer, Byte, Timestamp, Timestamp, String, Integer, Integer, Integer, Integer> fieldsRow() {
		return (Row10) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<Integer, Integer, Byte, Timestamp, Timestamp, String, Integer, Integer, Integer, Integer> valuesRow() {
		return (Row10) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return UserFavPosition.USER_FAV_POSITION.SYSUSER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return UserFavPosition.USER_FAV_POSITION.POSITION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field3() {
		return UserFavPosition.USER_FAV_POSITION.FAVORITE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return UserFavPosition.USER_FAV_POSITION.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return UserFavPosition.USER_FAV_POSITION.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return UserFavPosition.USER_FAV_POSITION.MOBILE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return UserFavPosition.USER_FAV_POSITION.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return UserFavPosition.USER_FAV_POSITION.WXUSER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return UserFavPosition.USER_FAV_POSITION.RECOM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field10() {
		return UserFavPosition.USER_FAV_POSITION.RECOM_USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getSysuserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getPositionId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value3() {
		return getFavorite();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getMobile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getWxuserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getRecomId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value10() {
		return getRecomUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value1(Integer value) {
		setSysuserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value2(Integer value) {
		setPositionId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value3(Byte value) {
		setFavorite(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value4(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value5(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value6(String value) {
		setMobile(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value7(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value8(Integer value) {
		setWxuserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value9(Integer value) {
		setRecomId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord value10(Integer value) {
		setRecomUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserFavPositionRecord values(Integer value1, Integer value2, Byte value3, Timestamp value4, Timestamp value5, String value6, Integer value7, Integer value8, Integer value9, Integer value10) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserFavPositionRecord
	 */
	public UserFavPositionRecord() {
		super(UserFavPosition.USER_FAV_POSITION);
	}

	/**
	 * Create a detached, initialised UserFavPositionRecord
	 */
	public UserFavPositionRecord(Integer sysuserId, Integer positionId, Byte favorite, Timestamp createTime, Timestamp updateTime, String mobile, Integer id, Integer wxuserId, Integer recomId, Integer recomUserId) {
		super(UserFavPosition.USER_FAV_POSITION);

		setValue(0, sysuserId);
		setValue(1, positionId);
		setValue(2, favorite);
		setValue(3, createTime);
		setValue(4, updateTime);
		setValue(5, mobile);
		setValue(6, id);
		setValue(7, wxuserId);
		setValue(8, recomId);
		setValue(9, recomUserId);
	}
}
