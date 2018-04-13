/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.daos;


import com.moseeker.baseorm.db.logdb.tables.LogWxMessageRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxMessageRecordRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 模板消息发送结果记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogWxMessageRecordDao extends DAOImpl<LogWxMessageRecordRecord, com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord, Integer> {

    /**
     * Create a new LogWxMessageRecordDao without any configuration
     */
    public LogWxMessageRecordDao() {
        super(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD, com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord.class);
    }

    /**
     * Create a new LogWxMessageRecordDao with an attached configuration
     */
    public LogWxMessageRecordDao(Configuration configuration) {
        super(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD, com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchById(Integer... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord fetchOneById(Integer value) {
        return fetchOne(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.ID, value);
    }

    /**
     * Fetch records that have <code>template_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByTemplateId(Integer... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.TEMPLATE_ID, values);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByWechatId(Integer... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>msgid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByMsgid(Long... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.MSGID, values);
    }

    /**
     * Fetch records that have <code>open_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByOpenId(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.OPEN_ID, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByUrl(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.URL, values);
    }

    /**
     * Fetch records that have <code>topcolor IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByTopcolor(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.TOPCOLOR, values);
    }

    /**
     * Fetch records that have <code>jsondata IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByJsondata(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.JSONDATA, values);
    }

    /**
     * Fetch records that have <code>errcode IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByErrcode(Integer... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.ERRCODE, values);
    }

    /**
     * Fetch records that have <code>errmsg IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByErrmsg(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.ERRMSG, values);
    }

    /**
     * Fetch records that have <code>sendtime IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchBySendtime(Timestamp... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.SENDTIME, values);
    }

    /**
     * Fetch records that have <code>updatetime IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByUpdatetime(Timestamp... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.UPDATETIME, values);
    }

    /**
     * Fetch records that have <code>sendstatus IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchBySendstatus(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.SENDSTATUS, values);
    }

    /**
     * Fetch records that have <code>sendtype IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchBySendtype(Integer... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.SENDTYPE, values);
    }

    /**
     * Fetch records that have <code>access_token IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogWxMessageRecord> fetchByAccessToken(String... values) {
        return fetch(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD.ACCESS_TOKEN, values);
    }
}
