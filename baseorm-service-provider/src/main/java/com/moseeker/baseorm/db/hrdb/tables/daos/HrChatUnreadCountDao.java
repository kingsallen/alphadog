/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 聊天室未读消息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrChatUnreadCountDao extends DAOImpl<HrChatUnreadCountRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount, Integer> {

    /**
     * Create a new HrChatUnreadCountDao without any configuration
     */
    public HrChatUnreadCountDao() {
        super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount.class);
    }

    /**
     * Create a new HrChatUnreadCountDao with an attached configuration
     */
    public HrChatUnreadCountDao(Configuration configuration) {
        super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT, com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount object) {
        return object.getRoomId();
    }

    /**
     * Fetch records that have <code>room_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByRoomId(Integer... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.ROOM_ID, values);
    }

    /**
     * Fetch a unique record that has <code>room_id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount fetchOneByRoomId(Integer value) {
        return fetchOne(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.ROOM_ID, value);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByHrId(Integer... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID, values);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByUserId(Integer... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID, values);
    }

    /**
     * Fetch records that have <code>wx_chat_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByWxChatTime(Timestamp... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.WX_CHAT_TIME, values);
    }

    /**
     * Fetch records that have <code>hr_chat_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByHrChatTime(Timestamp... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_CHAT_TIME, values);
    }

    /**
     * Fetch records that have <code>hr_have_unread_msg IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByHrHaveUnreadMsg(Byte... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_HAVE_UNREAD_MSG, values);
    }

    /**
     * Fetch records that have <code>user_have_unread_msg IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByUserHaveUnreadMsg(Byte... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_HAVE_UNREAD_MSG, values);
    }

    /**
     * Fetch records that have <code>apply IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByApply(Byte... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrChatUnreadCount> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME, values);
    }
}
