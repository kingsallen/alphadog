package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrTopic;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTopicRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTopicDO;

/**
* @author xxx
* HrTopicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrTopicDao extends StructDaoImpl<HrTopicDO, HrTopicRecord, HrTopic> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrTopic.HR_TOPIC;
   }
}
