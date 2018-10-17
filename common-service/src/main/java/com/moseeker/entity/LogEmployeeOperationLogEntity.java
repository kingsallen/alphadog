package com.moseeker.entity;

import com.moseeker.baseorm.dao.logdb.LogEmployeeOperationLogDao;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogEmployeeOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogEmployeeOperationLogEntity {

    @Autowired
    LogEmployeeOperationLogDao logEmployeeOperationLogDao;

    /**
     *  我是员工数据埋点
     *
     */

    public void insertEmployeeOperationLog(int referenceId,int enterance,int operationType,int isSuccess,int companyId,Integer profileId){
        LogEmployeeOperationLog logEmployeeOperationLog = new LogEmployeeOperationLog();
        logEmployeeOperationLog.setUserId(referenceId);
        logEmployeeOperationLog.setType((byte)enterance);
        logEmployeeOperationLog.setOperationType((byte)operationType);
        logEmployeeOperationLog.setIsSuccess((byte)isSuccess);
        logEmployeeOperationLog.setCompanyId(companyId);
        if(profileId!=null) {
            logEmployeeOperationLog.setProfileId(profileId);
        }
        logEmployeeOperationLogDao.insertNewLog(logEmployeeOperationLog);
    }
}
