package com.moseeker.baseorm.dao;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;

@Service
public class ThirdPartAccountDao extends BaseDaoImpl<HrThirdPartyAccountRecord, HrThirdPartyAccount> {

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike = HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT;
	}

}
