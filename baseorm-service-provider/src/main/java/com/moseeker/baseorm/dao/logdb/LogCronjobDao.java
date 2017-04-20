package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogCronjob;
import com.moseeker.baseorm.db.logdb.tables.records.LogCronjobRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogCronjobDO;

/**
* @author xxx
* LogCronjobDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogCronjobDao extends StructDaoImpl<LogCronjobDO, LogCronjobRecord, LogCronjob> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogCronjob.LOG_CRONJOB;
   }
}
