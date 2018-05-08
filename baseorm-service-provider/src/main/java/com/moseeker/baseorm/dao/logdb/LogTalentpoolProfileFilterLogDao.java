package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolProfileFilterLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolProfileFilterLogRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class LogTalentpoolProfileFilterLogDao extends JooqCrudImpl<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolProfileFilterLog,LogTalentpoolProfileFilterLogRecord> {

    public LogTalentpoolProfileFilterLogDao(){
        super(LogTalentpoolProfileFilterLog.LOG_TALENTPOOL_PROFILE_FILTER_LOG, com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolProfileFilterLog.class);
    }
    public LogTalentpoolProfileFilterLogDao(TableImpl<LogTalentpoolProfileFilterLogRecord> table, Class<com.moseeker.baseorm.db.logdb.tables.pojos.LogTalentpoolProfileFilterLog> logTalentpoolProfileFilterLogClass) {
        super(table, logTalentpoolProfileFilterLogClass);
    }


}
