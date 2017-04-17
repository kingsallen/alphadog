package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogWxMessageRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxMessageRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;

/**
* @author xxx
* LogWxMessageRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxMessageRecordDao extends StructDaoImpl<LogWxMessageRecordDO, LogWxMessageRecordRecord, LogWxMessageRecord> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogWxMessageRecord.LOG_WX_MESSAGE_RECORD;
   }
}
