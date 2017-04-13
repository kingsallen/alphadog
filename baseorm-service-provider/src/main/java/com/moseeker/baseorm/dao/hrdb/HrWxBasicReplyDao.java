package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxBasicReply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxBasicReplyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxBasicReplyDO;

/**
* @author xxx
* HrWxBasicReplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxBasicReplyDao extends StructDaoImpl<HrWxBasicReplyDO, HrWxBasicReplyRecord, HrWxBasicReply> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxBasicReply.HR_WX_BASIC_REPLY;
   }
}
