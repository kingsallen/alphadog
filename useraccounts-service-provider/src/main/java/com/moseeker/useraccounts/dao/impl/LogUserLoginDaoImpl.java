package com.moseeker.useraccounts.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.logdb.tables.LogUserloginRecord;
import com.moseeker.db.logdb.tables.records.LogUserloginRecordRecord;

@Repository
public class LogUserLoginDaoImpl extends BaseDaoImpl<LogUserloginRecordRecord, LogUserloginRecord> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = LogUserloginRecord.LOG_USERLOGIN_RECORD;
	}
}
