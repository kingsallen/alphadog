/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables.records;


import com.moseeker.db.analytics.tables.StInterest;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 记录留点击我感兴趣用户信息
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StInterestRecord extends UpdatableRecordImpl<StInterestRecord> implements Record6<Integer, Timestamp, Integer, Integer, String, String> {

	private static final long serialVersionUID = -1476354755;

	/**
	 * Setter for <code>analytics.st_interest.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>analytics.st_interest.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>analytics.st_interest.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>analytics.st_interest.create_time</code>.
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(1);
	}

	/**
	 * Setter for <code>analytics.st_interest.pid</code>.
	 */
	public void setPid(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>analytics.st_interest.pid</code>.
	 */
	public Integer getPid() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>analytics.st_interest.recom</code>. 转发人id，对应wx_group_user的id
	 */
	public void setRecom(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>analytics.st_interest.recom</code>. 转发人id，对应wx_group_user的id
	 */
	public Integer getRecom() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>analytics.st_interest.viewer_id</code>.
	 */
	public void setViewerId(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>analytics.st_interest.viewer_id</code>.
	 */
	public String getViewerId() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>analytics.st_interest.open_id</code>.
	 */
	public void setOpenId(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>analytics.st_interest.open_id</code>.
	 */
	public String getOpenId() {
		return (String) getValue(5);
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
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Timestamp, Integer, Integer, String, String> fieldsRow() {
		return (Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row6<Integer, Timestamp, Integer, Integer, String, String> valuesRow() {
		return (Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return StInterest.ST_INTEREST.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field2() {
		return StInterest.ST_INTEREST.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return StInterest.ST_INTEREST.PID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return StInterest.ST_INTEREST.RECOM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return StInterest.ST_INTEREST.VIEWER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return StInterest.ST_INTEREST.OPEN_ID;
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
	public Timestamp value2() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getPid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getRecom();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getViewerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getOpenId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord value2(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord value3(Integer value) {
		setPid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord value4(Integer value) {
		setRecom(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord value5(String value) {
		setViewerId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord value6(String value) {
		setOpenId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StInterestRecord values(Integer value1, Timestamp value2, Integer value3, Integer value4, String value5, String value6) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached StInterestRecord
	 */
	public StInterestRecord() {
		super(StInterest.ST_INTEREST);
	}

	/**
	 * Create a detached, initialised StInterestRecord
	 */
	public StInterestRecord(Integer id, Timestamp createTime, Integer pid, Integer recom, String viewerId, String openId) {
		super(StInterest.ST_INTEREST);

		setValue(0, id);
		setValue(1, createTime);
		setValue(2, pid);
		setValue(3, recom);
		setValue(4, viewerId);
		setValue(5, openId);
	}
}
