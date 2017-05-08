package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogWxMessageRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxMessageRecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogWxMessageRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxMessageRecordDao extends JooqCrudImpl<LogWxMessageRecordDO, LogWxMessageRecordRecord> {

    public LogWxMessageRecordDao() {
        super(LogWxMessageRecord.LOG_WX_MESSAGE_RECORD, LogWxMessageRecordDO.class);
    }


    public LogWxMessageRecordDao(TableImpl<LogWxMessageRecordRecord> table, Class<LogWxMessageRecordDO> logWxMessageRecordDOClass) {
        super(table, logWxMessageRecordDOClass);
    }
}
