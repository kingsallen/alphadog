package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.LogSmsSendrecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogSmsSendrecordRecord;
import com.moseeker.thrift.gen.dao.struct.logdb.LogSmsSendrecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class SmsSendrecordDao extends JooqCrudImpl<LogSmsSendrecordDO, LogSmsSendrecordRecord> {

    public SmsSendrecordDao() {
        super(LogSmsSendrecord.LOG_SMS_SENDRECORD, LogSmsSendrecordDO.class);
    }

	public SmsSendrecordDao(TableImpl<LogSmsSendrecordRecord> table, Class<LogSmsSendrecordDO> logSmsSendrecordDOClass) {
		super(table, logSmsSendrecordDOClass);
	}
}
