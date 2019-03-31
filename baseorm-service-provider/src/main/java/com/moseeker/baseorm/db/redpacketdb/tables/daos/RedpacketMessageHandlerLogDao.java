/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables.daos;


import com.moseeker.baseorm.db.redpacketdb.tables.RedpacketMessageHandlerLog;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketMessageHandlerLogRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 消息消费记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketMessageHandlerLogDao extends DAOImpl<RedpacketMessageHandlerLogRecord, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog, String> {

    /**
     * Create a new RedpacketMessageHandlerLogDao without any configuration
     */
    public RedpacketMessageHandlerLogDao() {
        super(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog.class);
    }

    /**
     * Create a new RedpacketMessageHandlerLogDao with an attached configuration
     */
    public RedpacketMessageHandlerLogDao(Configuration configuration) {
        super(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog object) {
        return object.getMessageId();
    }

    /**
     * Fetch records that have <code>message_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog> fetchByMessageId(String... values) {
        return fetch(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG.MESSAGE_ID, values);
    }

    /**
     * Fetch a unique record that has <code>message_id = value</code>
     */
    public com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog fetchOneByMessageId(String value) {
        return fetchOne(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG.MESSAGE_ID, value);
    }

    /**
     * Fetch records that have <code>times IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog> fetchByTimes(Byte... values) {
        return fetch(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG.TIMES, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog> fetchByCreateTime(Timestamp... values) {
        return fetch(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketMessageHandlerLog> fetchByUpdateTime(Timestamp... values) {
        return fetch(RedpacketMessageHandlerLog.REDPACKET_MESSAGE_HANDLER_LOG.UPDATE_TIME, values);
    }
}
