package com.moseeker.baseorm.dao.hrdb;

import java.util.HashMap;
import java.util.Map;

import com.moseeker.common.providerutils.CommonCondition;
import com.moseeker.common.providerutils.QueryCreator;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
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
		CommonQuery query = QueryCreator
				.where(CommonCondition.equal("hr_account_id", String.valueOf(hrAccountId)))
				.and(CommonCondition.equal("id", String.valueOf(id))).getCommonQuery();
		try {
			HrSearchConditionRecord record = getResource(query);
			return delResource(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}
}
