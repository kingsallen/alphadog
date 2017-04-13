package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogWxTemplateMessageSendrecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogWxTemplateMessageSendrecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxTemplateMessageSendrecordDO;

/**
* @author xxx
* LogWxTemplateMessageSendrecordDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class LogWxTemplateMessageSendrecordDao extends StructDaoImpl<LogWxTemplateMessageSendrecordDO, LogWxTemplateMessageSendrecordRecord, LogWxTemplateMessageSendrecord> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = LogWxTemplateMessageSendrecord.LOG_WX_TEMPLATE_MESSAGE_SENDRECORD;
   }
}
