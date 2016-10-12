package com.moseeker.useraccounts.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.logdb.tables.LogSmsSendrecord;
import com.moseeker.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.useraccounts.dao.SMSRecordDao;

/**
 * 用户数据接口
 *
 *
 */
@Repository
public class SMSRecordDaoImpl extends BaseDaoImpl<LogSmsSendrecordRecord, LogSmsSendrecord> implements SMSRecordDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = LogSmsSendrecord.LOG_SMS_SENDRECORD;
    }
}
