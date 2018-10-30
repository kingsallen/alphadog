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

    public void insertNewLog(com.moseeker.baseorm.db.logdb.tables.pojos.LogEmployeeOperationLog logEmployeeOperationLog) {
        create.insertInto(LOG_EMPLOYEE_OPERATION_LOG,LOG_EMPLOYEE_OPERATION_LOG.USER_ID,LOG_EMPLOYEE_OPERATION_LOG.COMPANY_ID,LOG_EMPLOYEE_OPERATION_LOG.TYPE,LOG_EMPLOYEE_OPERATION_LOG.PROFILE_ID,LOG_EMPLOYEE_OPERATION_LOG.OPERATION_TYPE,LOG_EMPLOYEE_OPERATION_LOG.IS_SUCCESS)
                .values(logEmployeeOperationLog.getUserId(),logEmployeeOperationLog.getCompanyId(),logEmployeeOperationLog.getType(),logEmployeeOperationLog.getProfileId(),logEmployeeOperationLog.getOperationType(),logEmployeeOperationLog.getIsSuccess()).execute();

    }
}
