package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolEmailDailyLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailDailyLogRecord;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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

    public List<LogTalentpoolEmailDailyLogRecord> fetchEmailAccountConsumption(int companyId, byte value, int index, int pageSize) {
        return create.selectFrom(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                .where(LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID.eq(companyId))
                .and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.TYPE.eq(value))
                .limit(index, pageSize)
                .fetch();
    }

    public int countEmailAccountConsumption(int companyId, byte value) {
        return create.selectCount()
                .from(LOG_TALENTPOOL_EMAIL_DAILY_LOG)
                .where(LOG_TALENTPOOL_EMAIL_DAILY_LOG.COMPANY_ID.eq(companyId))
                .and(LOG_TALENTPOOL_EMAIL_DAILY_LOG.TYPE.eq(value))
                .fetchOne()
                .value1();
    }
}
