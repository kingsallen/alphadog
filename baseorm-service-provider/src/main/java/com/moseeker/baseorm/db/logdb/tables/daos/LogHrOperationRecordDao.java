/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.daos;


import com.moseeker.baseorm.db.logdb.tables.LogHrOperationRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogHrOperationRecordRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * HR操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogHrOperationRecordDao extends DAOImpl<LogHrOperationRecordRecord, com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord, Integer> {

    /**
     * Create a new LogHrOperationRecordDao without any configuration
     */
    public LogHrOperationRecordDao() {
        super(LogHrOperationRecord.LOG_HR_OPERATION_RECORD, com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord.class);
    }

    /**
     * Create a new LogHrOperationRecordDao with an attached configuration
     */
    public LogHrOperationRecordDao(Configuration configuration) {
        super(LogHrOperationRecord.LOG_HR_OPERATION_RECORD, com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord> fetchById(Integer... values) {
        return fetch(LogHrOperationRecord.LOG_HR_OPERATION_RECORD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord fetchOneById(Integer value) {
        return fetchOne(LogHrOperationRecord.LOG_HR_OPERATION_RECORD.ID, value);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord> fetchByType(Integer... values) {
        return fetch(LogHrOperationRecord.LOG_HR_OPERATION_RECORD.TYPE, values);
    }

    /**
     * Fetch records that have <code>hraccount_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord> fetchByHraccountId(Integer... values) {
        return fetch(LogHrOperationRecord.LOG_HR_OPERATION_RECORD.HRACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord> fetchByDescription(String... values) {
        return fetch(LogHrOperationRecord.LOG_HR_OPERATION_RECORD.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogHrOperationRecord> fetchByCreateTime(Timestamp... values) {
        return fetch(LogHrOperationRecord.LOG_HR_OPERATION_RECORD.CREATE_TIME, values);
    }
}
