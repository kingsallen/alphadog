package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.records.LogEmailSendrecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogEmailSendrecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogEmailSendrecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogEmailSendrecordDao extends JooqCrudImpl<LogEmailSendrecordDO, LogEmailSendrecordRecord> {


    public LogEmailSendrecordDao(TableImpl<LogEmailSendrecordRecord> table, Class<LogEmailSendrecordDO> logEmailSendrecordDOClass) {
        super(table, logEmailSendrecordDOClass);
    }
}
