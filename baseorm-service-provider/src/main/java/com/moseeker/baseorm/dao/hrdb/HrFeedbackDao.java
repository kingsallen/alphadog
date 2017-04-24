package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrFeedback;
import com.moseeker.baseorm.db.hrdb.tables.records.HrFeedbackRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrFeedbackDO;

/**
* @author xxx
* HrFeedbackDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrFeedbackDao extends StructDaoImpl<HrFeedbackDO, HrFeedbackRecord, HrFeedback> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrFeedback.HR_FEEDBACK;
   }
}
