package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogWxMenuRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxMenuRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMenuRecordDO;

/**
* @author xxx
* LogWxMenuRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxMenuRecordDao extends StructDaoImpl<LogWxMenuRecordDO, LogWxMenuRecordRecord, LogWxMenuRecord> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogWxMenuRecord.LOG_WX_MENU_RECORD;
   }
}
