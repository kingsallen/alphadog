package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxTemplateMessageSendrecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxTemplateMessageSendrecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* LogWxTemplateMessageSendrecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxTemplateMessageSendrecordDao extends JooqCrudImpl<LogWxTemplateMessageSendrecordDO, LogWxTemplateMessageSendrecordRecord> {

    public LogWxTemplateMessageSendrecordDao(TableImpl<LogWxTemplateMessageSendrecordRecord> table, Class<LogWxTemplateMessageSendrecordDO> logWxTemplateMessageSendrecordDOClass) {
        super(table, logWxTemplateMessageSendrecordDOClass);
    }
}
