package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogHrOperationRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogHrOperationRecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogHrOperationRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogHrOperationRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogHrOperationRecordDao extends JooqCrudImpl<LogHrOperationRecordDO, LogHrOperationRecordRecord> {

    public LogHrOperationRecordDao() {
        super(LogHrOperationRecord.LOG_HR_OPERATION_RECORD, LogHrOperationRecordDO.class);
    }

    public LogHrOperationRecordDao(TableImpl<LogHrOperationRecordRecord> table, Class<LogHrOperationRecordDO> logHrOperationRecordDOClass) {
        super(table, logHrOperationRecordDOClass);
    }
}
