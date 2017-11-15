/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.HrWxHrChatListBak;
import com.moseeker.baseorm.db.historydb.tables.records.HrWxHrChatListBakRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * IM聊天人关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatListBakDao extends DAOImpl<HrWxHrChatListBakRecord, com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak, Integer> {

    /**
     * Create a new HrWxHrChatListBakDao without any configuration
     */
    public HrWxHrChatListBakDao() {
        super(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK, com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak.class);
    }

    /**
     * Create a new HrWxHrChatListBakDao with an attached configuration
     */
    public HrWxHrChatListBakDao(Configuration configuration) {
        super(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK, com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchById(Integer... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak fetchOneById(Integer value) {
        return fetchOne(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.ID, value);
    }

    /**
     * Fetch records that have <code>sysuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchBySysuserId(Integer... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.SYSUSER_ID, values);
    }

    /**
     * Fetch records that have <code>hraccount_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchByHraccountId(Integer... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.HRACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchByStatus(Byte... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.STATUS, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchByCreateTime(Timestamp... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>wx_chat_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchByWxChatTime(Timestamp... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.WX_CHAT_TIME, values);
    }

    /**
     * Fetch records that have <code>hr_chat_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchByHrChatTime(Timestamp... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.HR_CHAT_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrWxHrChatListBak> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrWxHrChatListBak.HR_WX_HR_CHAT_LIST_BAK.UPDATE_TIME, values);
    }
}
