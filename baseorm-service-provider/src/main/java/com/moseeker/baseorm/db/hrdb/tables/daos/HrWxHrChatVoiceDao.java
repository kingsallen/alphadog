/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatVoice;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatVoiceRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatVoiceDao extends DAOImpl<HrWxHrChatVoiceRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice, Integer> {

    /**
     * Create a new HrWxHrChatVoiceDao without any configuration
     */
    public HrWxHrChatVoiceDao() {
        super(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice.class);
    }

    /**
     * Create a new HrWxHrChatVoiceDao with an attached configuration
     */
    public HrWxHrChatVoiceDao(Configuration configuration) {
        super(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchById(Integer... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice fetchOneById(Integer value) {
        return fetchOne(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.ID, value);
    }

    /**
     * Fetch records that have <code>chat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchByChatId(Integer... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CHAT_ID, values);
    }

    /**
     * Fetch a unique record that has <code>chat_id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice fetchOneByChatId(Integer value) {
        return fetchOne(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CHAT_ID, value);
    }

    /**
     * Fetch records that have <code>server_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchByServerId(String... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.SERVER_ID, values);
    }

    /**
     * Fetch records that have <code>duration IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchByDuration(Byte... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION, values);
    }

    /**
     * Fetch records that have <code>local_url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchByLocalUrl(String... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.LOCAL_URL, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchByCreateTime(Timestamp... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxHrChatVoice> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.UPDATE_TIME, values);
    }
}