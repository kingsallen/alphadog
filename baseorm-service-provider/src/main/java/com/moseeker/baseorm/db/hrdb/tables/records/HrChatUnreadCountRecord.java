/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 聊天室未读消息
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrChatUnreadCountRecord extends UpdatableRecordImpl<HrChatUnreadCountRecord> implements Record3<UInteger, Integer, Integer> {

	private static final long serialVersionUID = -804936175;

	/**
	 * Setter for <code>hrdb.hr_chat_unread_count.room_id</code>. 聊天室编号
	 */
	public void setRoomId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>hrdb.hr_chat_unread_count.room_id</code>. 聊天室编号
	 */
	public UInteger getRoomId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>hrdb.hr_chat_unread_count.hr_unread_count</code>. hr未读消息数量
	 */
	public void setHrUnreadCount(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>hrdb.hr_chat_unread_count.hr_unread_count</code>. hr未读消息数量
	 */
	public Integer getHrUnreadCount() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>hrdb.hr_chat_unread_count.user_unread_count</code>. 员工未读消息数量
	 */
	public void setUserUnreadCount(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>hrdb.hr_chat_unread_count.user_unread_count</code>. 员工未读消息数量
	 */
	public Integer getUserUnreadCount() {
		return (Integer) getValue(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<UInteger, Integer, Integer> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<UInteger, Integer, Integer> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.ROOM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field2() {
		return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_UNREAD_COUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field3() {
		return HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_UNREAD_COUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getRoomId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getHrUnreadCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getUserUnreadCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChatUnreadCountRecord value1(UInteger value) {
		setRoomId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChatUnreadCountRecord value2(Integer value) {
		setHrUnreadCount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChatUnreadCountRecord value3(Integer value) {
		setUserUnreadCount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrChatUnreadCountRecord values(UInteger value1, Integer value2, Integer value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached HrChatUnreadCountRecord
	 */
	public HrChatUnreadCountRecord() {
		super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT);
	}

	/**
	 * Create a detached, initialised HrChatUnreadCountRecord
	 */
	public HrChatUnreadCountRecord(UInteger roomId, Integer hrUnreadCount, Integer userUnreadCount) {
		super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT);

		setValue(0, roomId);
		setValue(1, hrUnreadCount);
		setValue(2, userUnreadCount);
	}
}
