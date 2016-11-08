package com.moseeker.baseorm.dao;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartPositionRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
@Service
public class ThirdPartPositionDao extends BaseDaoImpl<HrThirdPartPositionRecord, HrThirdPartPosition>{

	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=HrThirdPartPosition.HR_THIRD_PART_POSITION;
		
	}

}
