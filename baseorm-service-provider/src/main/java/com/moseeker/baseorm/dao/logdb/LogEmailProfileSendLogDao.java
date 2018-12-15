package com.moseeker.baseorm.dao.logdb;/**
 * Created by zztaiwll on 18/12/4.
 */

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogEmailProfileSendLog;
import com.moseeker.baseorm.db.logdb.tables.records.LogEmailProfileSendLogRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.logdb.tables.LogEmailProfileSendLog.LOG_EMAIL_PROFILE_SEND_LOG;

/**
 * @version 1.0
 * @className LogEmailProfileSendLogDao
 * @Description TODO
 * @Author zztaiwll
 * @DATE 18/12/4 下午2:44
 **/
@Repository
public class LogEmailProfileSendLogDao extends JooqCrudImpl<LogEmailProfileSendLog,LogEmailProfileSendLogRecord> {
    public LogEmailProfileSendLogDao(){
        super(LOG_EMAIL_PROFILE_SEND_LOG,LogEmailProfileSendLog.class);
    }
    public LogEmailProfileSendLogDao(TableImpl<LogEmailProfileSendLogRecord> table, Class<LogEmailProfileSendLog> logEmailProfileSendLogClass) {
        super(table, logEmailProfileSendLogClass);
    }

}
