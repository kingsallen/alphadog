/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.UserChatRoom;
import com.moseeker.baseorm.db.userdb.tables.records.UserChatRoomRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 员工和候选人的聊天室
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserChatRoomDao extends DAOImpl<UserChatRoomRecord, com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom, Integer> {

    /**
     * Create a new UserChatRoomDao without any configuration
     */
    public UserChatRoomDao() {
        super(UserChatRoom.USER_CHAT_ROOM, com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom.class);
    }

    /**
     * Create a new UserChatRoomDao with an attached configuration
     */
    public UserChatRoomDao(Configuration configuration) {
        super(UserChatRoom.USER_CHAT_ROOM, com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchById(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom fetchOneById(Integer value) {
        return fetchOne(UserChatRoom.USER_CHAT_ROOM.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByCompanyId(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserId(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_ID, values);
    }

    /**
     * Fetch records that have <code>employee_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeId(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_ID, values);
    }

    /**
     * Fetch records that have <code>latest_chat_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByLatestChatTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.LATEST_CHAT_TIME, values);
    }

    /**
     * Fetch records that have <code>user_enter_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserEnterTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_ENTER_TIME, values);
    }

    /**
     * Fetch records that have <code>employee_enter_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeEnterTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_ENTER_TIME, values);
    }

    /**
     * Fetch records that have <code>user_leave_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserLeaveTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_LEAVE_TIME, values);
    }

    /**
     * Fetch records that have <code>employee_leave_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeLeaveTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_LEAVE_TIME, values);
    }

    /**
     * Fetch records that have <code>user_del IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserDel(Byte... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_DEL, values);
    }

    /**
     * Fetch records that have <code>employee_del IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeDel(Byte... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_DEL, values);
    }

    /**
     * Fetch records that have <code>employee_unread_count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeUnreadCount(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_UNREAD_COUNT, values);
    }

    /**
     * Fetch records that have <code>user_unread_count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserUnreadCount(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_UNREAD_COUNT, values);
    }

    /**
     * Fetch records that have <code>employee_hint_schedule IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeHintSchedule(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_HINT_SCHEDULE, values);
    }

    /**
     * Fetch records that have <code>user_hint_schedule IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserHintSchedule(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_HINT_SCHEDULE, values);
    }

    /**
     * Fetch records that have <code>user_send_for_msg_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUserSendForMsgId(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.USER_SEND_FOR_MSG_ID, values);
    }

    /**
     * Fetch records that have <code>employee_send_for_msg_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByEmployeeSendForMsgId(Integer... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.EMPLOYEE_SEND_FOR_MSG_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByCreateTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserChatRoom> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserChatRoom.USER_CHAT_ROOM.UPDATE_TIME, values);
    }
}
