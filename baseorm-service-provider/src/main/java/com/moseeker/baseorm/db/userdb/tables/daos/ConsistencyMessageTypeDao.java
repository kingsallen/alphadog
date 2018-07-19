/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.daos;


import com.moseeker.baseorm.db.userdb.tables.ConsistencyMessageType;
import com.moseeker.baseorm.db.userdb.tables.records.ConsistencyMessageTypeRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 消息类型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConsistencyMessageTypeDao extends DAOImpl<ConsistencyMessageTypeRecord, com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType, String> {

    /**
     * Create a new ConsistencyMessageTypeDao without any configuration
     */
    public ConsistencyMessageTypeDao() {
        super(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE, com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType.class);
    }

    /**
     * Create a new ConsistencyMessageTypeDao with an attached configuration
     */
    public ConsistencyMessageTypeDao(Configuration configuration) {
        super(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE, com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType object) {
        return object.getName();
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType> fetchByName(String... values) {
        return fetch(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME, values);
    }

    /**
     * Fetch a unique record that has <code>name = value</code>
     */
    public com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType fetchOneByName(String value) {
        return fetchOne(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.NAME, value);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType> fetchByCreateTime(Timestamp... values) {
        return fetch(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType> fetchByUpdateTime(Timestamp... values) {
        return fetch(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>class_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType> fetchByClassName(String... values) {
        return fetch(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.CLASS_NAME, values);
    }

    /**
     * Fetch records that have <code>method IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType> fetchByMethod(String... values) {
        return fetch(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.METHOD, values);
    }

    /**
     * Fetch records that have <code>period IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.userdb.tables.pojos.ConsistencyMessageType> fetchByPeriod(Integer... values) {
        return fetch(ConsistencyMessageType.CONSISTENCY_MESSAGE_TYPE.PERIOD, values);
    }
}
