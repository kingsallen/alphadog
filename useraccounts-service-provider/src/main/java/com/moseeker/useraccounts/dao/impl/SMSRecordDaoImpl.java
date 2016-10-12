package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;

import org.jooq.DSLContext;
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

    private Connection conn = null;

    private DSLContext create = null;

    @Override
    protected void initJOOQEntity() {
        this.tableLike = LogSmsSendrecord.LOG_SMS_SENDRECORD;
    }

    
}
