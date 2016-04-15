/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb.tables.records;


import com.moseeker.db.userdb.tables.Userfriends_2degree;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 二度好友关系表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Userfriends_2degreeRecord extends UpdatableRecordImpl<Userfriends_2degreeRecord> implements Record4<Integer, Integer, Integer, Timestamp> {

	private static final long serialVersionUID = 1655682094;

	/**
	 * Setter for <code>userDB.userfriends_2degree.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>userDB.userfriends_2degree.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>userDB.userfriends_2degree.user1</code>. 第一个userid
	 */
	public void setUser1(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>userDB.userfriends_2degree.user1</code>. 第一个userid
	 */
	public Integer getUser1() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>userDB.userfriends_2degree.user2</code>.
	 */
	public void setUser2(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>userDB.userfriends_2degree.user2</code>.
	 */
	public Integer getUser2() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>userDB.userfriends_2degree.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>userDB.userfriends_2degree.create_time</code>.
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(3);
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
	public Row4<Integer, Integer, Integer, Timestamp> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Integer, Integer, Integer, Timestamp> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return Userfriends_2degree.USERFRIENDS_2DEGREE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return Userfriends_2degree.USERFRIENDS_2DEGREE.USER1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return Userfriends_2degree.USERFRIENDS_2DEGREE.USER2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return Userfriends_2degree.USERFRIENDS_2DEGREE.CREATE_TIME;
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
		return getUser1();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getUser2();
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
	public Userfriends_2degreeRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Userfriends_2degreeRecord value2(Integer value) {
		setUser1(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Userfriends_2degreeRecord value3(Integer value) {
		setUser2(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Userfriends_2degreeRecord value4(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Userfriends_2degreeRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4) {
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
	 * Create a detached Userfriends_2degreeRecord
	 */
	public Userfriends_2degreeRecord() {
		super(Userfriends_2degree.USERFRIENDS_2DEGREE);
	}

	/**
	 * Create a detached, initialised Userfriends_2degreeRecord
	 */
	public Userfriends_2degreeRecord(Integer id, Integer user1, Integer user2, Timestamp createTime) {
		super(Userfriends_2degree.USERFRIENDS_2DEGREE);

		setValue(0, id);
		setValue(1, user1);
		setValue(2, user2);
		setValue(3, createTime);
	}
}