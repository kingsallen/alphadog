package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;

/**
* @author xxx
* HrWxTemplateMessageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxTemplateMessageDao extends StructDaoImpl<HrWxTemplateMessageDO, HrWxTemplateMessageRecord, HrWxTemplateMessage> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE;
   }
}
