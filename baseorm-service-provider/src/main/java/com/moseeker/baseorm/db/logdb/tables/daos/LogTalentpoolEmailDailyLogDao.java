/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb.tables.daos;


import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailDailyLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailDailyLogRecord;

import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 邮件额度每天使用日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogTalentpoolEmailDailyLogDao extends DAOImpl<LogTalentpoolEmailDailyLogRecord, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog, Integer> {

    /**
     * Create a new LogTalentpoolEmailDailyLogDao without any configuration
     */
    public LogTalentpoolEmailDailyLogDao() {
        super(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog.class);
    }

    /**
     * Create a new LogTalentpoolEmailDailyLogDao with an attached configuration
     */
    public LogTalentpoolEmailDailyLogDao(Configuration configuration) {
        super(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog> fetchById(Integer... values) {
        return fetch(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog fetchOneById(Integer value) {
        return fetchOne(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog> fetchByCompanyId(Integer... values) {
        return fetch(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>lost IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog> fetchByLost(Integer... values) {
        return fetch(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.LOST, values);
    }

    /**
     * Fetch records that have <code>date IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog> fetchByDate(Date... values) {
        return fetch(LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE, values);
    }
}
