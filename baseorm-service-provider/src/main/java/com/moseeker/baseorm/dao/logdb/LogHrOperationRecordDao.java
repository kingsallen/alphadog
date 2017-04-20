package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogHrOperationRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogHrOperationRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogHrOperationRecordDO;

/**
* @author xxx
* LogHrOperationRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogHrOperationRecordDao extends StructDaoImpl<LogHrOperationRecordDO, LogHrOperationRecordRecord, LogHrOperationRecord> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogHrOperationRecord.LOG_HR_OPERATION_RECORD;
   }
}
