package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;

/**
* @author xxx
* HrWxNoticeMessageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxNoticeMessageDao extends StructDaoImpl<HrWxNoticeMessageDO, HrWxNoticeMessageRecord, HrWxNoticeMessage> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE;
   }
}
