/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.daos;


import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailLogRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 邮件日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogTalentpoolEmailLogDao extends DAOImpl<LogTalentpoolEmailLogRecord, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog, Integer> {

    /**
     * Create a new LogTalentpoolEmailLogDao without any configuration
     */
    public LogTalentpoolEmailLogDao() {
        super(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog.class);
    }

    /**
     * Create a new LogTalentpoolEmailLogDao with an attached configuration
     */
    public LogTalentpoolEmailLogDao(Configuration configuration) {
        super(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchById(Integer... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog fetchOneById(Integer value) {
        return fetchOne(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchByCompanyId(Integer... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchByType(Integer... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.TYPE, values);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchByHrId(Integer... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.HR_ID, values);
    }

    /**
     * Fetch records that have <code>balance IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchByBalance(Integer... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.BALANCE, values);
    }

    /**
     * Fetch records that have <code>lost IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchByLost(Integer... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.LOST, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> fetchByCreateTime(Timestamp... values) {
        return fetch(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME, values);
    }
}
