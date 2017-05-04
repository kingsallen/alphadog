package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxMenuRecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMenuRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogWxMenuRecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxMenuRecordDao extends JooqCrudImpl<LogWxMenuRecordDO, LogWxMenuRecordRecord> {


    public LogWxMenuRecordDao(TableImpl<LogWxMenuRecordRecord> table, Class<LogWxMenuRecordDO> logWxMenuRecordDOClass) {
        super(table, logWxMenuRecordDOClass);
    }
}
