package com.moseeker.baseorm.dao.logdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.logdb.tables.LogSmsSendrecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Repository
public class SmsSendrecordDao extends BaseDaoImpl<LogSmsSendrecordRecord, LogSmsSendrecord> {

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		tableLike = LogSmsSendrecord.LOG_SMS_SENDRECORD;
	}
}
