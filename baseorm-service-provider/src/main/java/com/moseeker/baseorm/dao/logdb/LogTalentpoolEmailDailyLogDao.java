package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailDailyLogRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailDailyLog.LOG_TALENTPOOL_EMAIL_DAILY_LOG;


/**
 * Created by jack on 2018/4/25.
 */
@Repository
public class LogTalentpoolEmailDailyLogDao extends JooqCrudImpl<LogTalentpoolEmailDailyLog, LogTalentpoolEmailDailyLogRecord> {

    public LogTalentpoolEmailDailyLogDao() {
        super(LOG_TALENTPOOL_EMAIL_DAILY_LOG, LogTalentpoolEmailDailyLog.class);
    }

    public LogTalentpoolEmailDailyLogDao(TableImpl<LogTalentpoolEmailDailyLogRecord> table, Class<LogTalentpoolEmailDailyLog> logTalentpoolEmailDailyLogClass) {
        super(table, logTalentpoolEmailDailyLogClass);
    }

    public List<LogTalentpoolEmailDailyLogRecord> fetchEmailAccountConsumption(int companyId, byte value, int index, int pageSize, Date startDate, Date endDate) {

        Condition condition = LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID.eq(companyId);
        if (startDate != null) {
            condition = condition.and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE.ge(startDate));
        }
        if (endDate != null) {
            condition = condition.and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE.lt(endDate));
        }

        return create.selectFrom(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                .where(condition)
                .orderBy(LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE.desc())
                .limit(index, pageSize)
                .fetch();
    }

    public int countEmailAccountConsumption(int companyId, byte value, Date startDate, Date endDate) {
        Condition condition = LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID.eq(companyId);
        if (startDate != null) {
            condition = condition.and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE.ge(startDate));
        }
        if (endDate != null) {
            condition = condition.and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE.lt(endDate));
        }
        return create.selectCount()
                .from(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                .where(condition)
                .fetchOne()
                .value1();
    }

    public void upsertDailyLog(long today, int companyId, int useCount) throws CommonException {
        int execute = create
                .insertInto(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                .columns(LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID,
                        LOG_TALENTPOOL_EMAIL_DAILY_LOG.LOST,
                        LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE)
                .values(companyId, useCount, new Date(today))
                .onDuplicateKeyIgnore()
                .execute();
        if (execute == 0) {
            updateLost(today, companyId, useCount, 0);
        }
    }

    private void updateLost(long today, int companyId, int useCount, int index) throws CommonException {
        if (index >= Constant.RETRY_UPPER_LIMIT) {
            throw CommonException.PROGRAM_UPDATE_FIALED;
        }
        index ++;
        LogTalentpoolEmailDailyLogRecord logTalentpoolEmailDailyLogRecord =
                create.selectFrom(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                .where(LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID.eq(companyId))
                .and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.DATE.eq(new Date(today)))
                .fetchOne();
        if (logTalentpoolEmailDailyLogRecord != null) {
            int execute = create.update(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                    .set(LOG_TALENTPOOL_EMAIL_DAILY_LOG.LOST, logTalentpoolEmailDailyLogRecord.getLost()+useCount)
                    .where(LOG_TALENTPOOL_EMAIL_DAILY_LOG.ID.eq(logTalentpoolEmailDailyLogRecord.getId()))
                    .and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.LOST.eq(logTalentpoolEmailDailyLogRecord.getLost()))
                    .execute();
            if (execute == 0) {
                updateLost(today, companyId, useCount, index);
            }
        } else {
            throw CommonException.NODATA_EXCEPTION;
        }
    }
}
