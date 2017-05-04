package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.records.LogCronjobRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogCronjobDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogCronjobDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogCronjobDao extends JooqCrudImpl<LogCronjobDO, LogCronjobRecord> {


    public LogCronjobDao(TableImpl<LogCronjobRecord> table, Class<LogCronjobDO> logCronjobDOClass) {
        super(table, logCronjobDOClass);
    }
}
