package com.moseeker.baseorm.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartAccountRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

@Service
public class ThirdPartAccountDao extends BaseDaoImpl<HrThirdPartAccountRecord, HrThirdPartAccount> {

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=HrThirdPartAccount.HR_THIRD_PART_ACCOUNT;
	}

}
