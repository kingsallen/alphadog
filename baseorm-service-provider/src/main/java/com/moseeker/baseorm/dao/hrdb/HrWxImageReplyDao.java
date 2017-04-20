package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxImageReply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxImageReplyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxImageReplyDO;

/**
* @author xxx
* HrWxImageReplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxImageReplyDao extends StructDaoImpl<HrWxImageReplyDO, HrWxImageReplyRecord, HrWxImageReply> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxImageReply.HR_WX_IMAGE_REPLY;
   }
}
