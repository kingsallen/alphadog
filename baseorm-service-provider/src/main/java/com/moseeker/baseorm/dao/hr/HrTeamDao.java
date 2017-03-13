package com.moseeker.baseorm.dao.hr;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;

@Service
public class HrTeamDao extends BaseDaoImpl<HrTeamRecord, HrTeam>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=HrTeam.HR_TEAM;
	}

}
