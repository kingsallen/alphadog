package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogEmailSendrecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogEmailSendrecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogEmailSendrecordDO;

/**
* @author xxx
* LogEmailSendrecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogEmailSendrecordDao extends StructDaoImpl<LogEmailSendrecordDO, LogEmailSendrecordRecord, LogEmailSendrecord> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogEmailSendrecord.LOG_EMAIL_SENDRECORD;
   }
}
