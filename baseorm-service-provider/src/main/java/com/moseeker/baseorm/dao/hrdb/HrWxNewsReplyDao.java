package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxNewsReply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNewsReplyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNewsReplyDO;

/**
* @author xxx
* HrWxNewsReplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxNewsReplyDao extends StructDaoImpl<HrWxNewsReplyDO, HrWxNewsReplyRecord, HrWxNewsReply> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxNewsReply.HR_WX_NEWS_REPLY;
   }
}
