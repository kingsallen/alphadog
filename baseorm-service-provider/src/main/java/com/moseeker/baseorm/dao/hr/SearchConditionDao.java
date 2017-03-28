package com.moseeker.baseorm.dao.hr;

import java.util.HashMap;
import java.util.Map;

import com.moseeker.common.providerutils.QueryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

@Service
public class SearchConditionDao extends BaseDaoImpl<HrSearchConditionRecord, HrSearchCondition> {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrSearchCondition.HR_SEARCH_CONDITION;
	}
	
	public int delResource(int hrAccountId, int id) {
		QueryUtil query = new QueryUtil();
		Map<String, String> param = new HashMap<String, String>();
		query.setEqualFilter(param);
		param.put("hr_account_id", String.valueOf(hrAccountId));
		param.put("id", String.valueOf(id));
		try {
			HrSearchConditionRecord record = getResource(query);
			return delResource(record);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0;
	}
}
