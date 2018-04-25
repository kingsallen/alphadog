package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailLogRecord;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class LogTalentpoolEmailLogDao extends JooqCrudImpl<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog,LogTalentpoolEmailLogRecord> {

    public LogTalentpoolEmailLogDao(){
        super(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog.class);
    }
    public LogTalentpoolEmailLogDao(TableImpl<LogTalentpoolEmailLogRecord> table, Class<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailLog> logTalentpoolEmailLogClass) {
        super(table, logTalentpoolEmailLogClass);
    }


    public LogTalentpoolEmailLogRecord getById(int id) {
        return create.selectFrom(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG)
                .where(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.ID.eq(id))
                .fetchOne();
    }

    public boolean updateLostById(int id, int lost) {
        int execute = create.update(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG)
                .set(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.LOST, lost)
                .where(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.ID.eq(id))
                .execute();
        return execute == 1 ? true:false;
    }

    public int countRecharge(int companyId, Timestamp startDate, Timestamp endDate) {
        Condition condition = LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.COMPANY_ID.eq(companyId)
                .and(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.TYPE.eq(0));
        if (startDate != null) {
            condition = condition.and(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME.ge(startDate));
        }
        if (endDate != null) {
            condition = condition.and(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME.le(endDate));
        }

        return create.selectCount()
                .from(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG)
                .where(condition)
                .fetchOne()
                .value1();
    }

    public List<LogTalentpoolEmailLogRecord> fetchEmailAccountRechargeRecords(int companyId, int index, int pageSize, Timestamp startDate, Timestamp endDate) {

        Condition condition = LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.COMPANY_ID.eq(companyId)
                .and(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.TYPE.eq(0));
        if (startDate != null) {
            condition = condition.and(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME.ge(startDate));
        }
        if (endDate != null) {
            condition = condition.and(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME.le(endDate));
        }

        return create.selectFrom(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG)
                .where(condition)
                .orderBy(LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG.CREATE_TIME.desc())
                .limit(index, pageSize)
                .fetch();
    }
}
