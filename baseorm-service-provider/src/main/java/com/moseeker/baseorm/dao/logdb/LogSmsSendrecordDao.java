package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogSmsSendrecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogSmsSendrecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogSmsSendrecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogSmsSendrecordDao extends JooqCrudImpl<LogSmsSendrecordDO, LogSmsSendrecordRecord> {

    public LogSmsSendrecordDao() {
        super(LogSmsSendrecord.LOG_SMS_SENDRECORD, LogSmsSendrecordDO.class);
    }

    public LogSmsSendrecordDao(TableImpl<LogSmsSendrecordRecord> table, Class<LogSmsSendrecordDO> logSmsSendrecordDOClass) {
        super(table, logSmsSendrecordDOClass);
    }
}
