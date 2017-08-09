package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogDeadLetter;
import com.moseeker.baseorm.db.logdb.tables.records.LogDeadLetterRecord;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;

/**
* @author xxx
* LogDeadLetterDao 实现类 （groovy 生成）
* 2017-08-08
*/
@Repository
public class LogDeadLetterDao extends JooqCrudImpl<LogDeadLetterDO, LogDeadLetterRecord> {


   public LogDeadLetterDao() {
        super(LogDeadLetter.LOG_DEAD_LETTER, LogDeadLetterDO.class);
   }
}
