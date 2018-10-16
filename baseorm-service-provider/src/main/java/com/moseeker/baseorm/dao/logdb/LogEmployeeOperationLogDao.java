package com.moseeker.baseorm.dao.logdb;


import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.records.LogEmployeeOperationLogRecord;
import com.moseeker.thrift.gen.profile.struct.LogEmployeeOperationLog;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.logdb.tables.LogEmployeeOperationLog.LOG_EMPLOYEE_OPERATION_LOG;
@Repository
public class LogEmployeeOperationLogDao extends JooqCrudImpl<LogEmployeeOperationLog, LogEmployeeOperationLogRecord> {

    public LogEmployeeOperationLogDao() {
        super(LOG_EMPLOYEE_OPERATION_LOG, LogEmployeeOperationLog.class);
    }
    public LogEmployeeOperationLogDao(TableImpl<LogEmployeeOperationLogRecord> table, Class<LogEmployeeOperationLog> logEmployeeOperationLogClass) {
        super(table, logEmployeeOperationLogClass);
    }
}
