package com.moseeker.baseorm.dao.hr;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrTalentpool;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Service
public class TalentpoolDao extends BaseDaoImpl<HrTalentpoolRecord, HrTalentpool>{
	
	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrTalentpool.HR_TALENTPOOL;
	}
	
	
}
