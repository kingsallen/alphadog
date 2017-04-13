package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogSmsSendrecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogSmsSendrecordDO;

/**
* @author xxx
* LogSmsSendrecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogSmsSendrecordDao extends StructDaoImpl<LogSmsSendrecordDO, LogSmsSendrecordRecord, LogSmsSendrecord> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogSmsSendrecord.LOG_SMS_SENDRECORD;
   }
}
