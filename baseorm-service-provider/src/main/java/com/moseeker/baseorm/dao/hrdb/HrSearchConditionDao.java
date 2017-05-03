package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.common.util.query.Query;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrSearchConditionDO;

/**
* @author xxx
* HrSearchConditionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrSearchConditionDao extends StructDaoImpl<HrSearchConditionDO, HrSearchConditionRecord, HrSearchCondition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrSearchCondition.HR_SEARCH_CONDITION;
   }
   
   public int delResource(int hrAccountId, int id) {
		Query query =  new Query.QueryBuilder().where("hr_account_id", hrAccountId).and("id", id).buildQuery();
		try {
			HrSearchConditionRecord record = getResource(query);
			return delResource(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}
}
