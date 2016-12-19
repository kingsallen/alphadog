package com.moseeker.baseorm.dao;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
@Service
public class ThirdPartPositionDao extends BaseDaoImpl<HrThirdPartyPositionRecord, HrThirdPartyPosition>{

	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=HrThirdPartyPosition.HR_THIRD_PARTY_POSITION;
		
	}

}
