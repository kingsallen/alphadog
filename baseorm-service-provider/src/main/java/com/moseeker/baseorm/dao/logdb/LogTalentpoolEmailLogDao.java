package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolEmailLogRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

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


}
