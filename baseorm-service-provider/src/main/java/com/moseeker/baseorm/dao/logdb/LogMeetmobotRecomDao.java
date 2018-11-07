package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogMeetmobotRecom;
import com.moseeker.baseorm.db.logdb.tables.records.LogMeetmobotRecomRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.logdb.tables.LogMeetmobotRecom.LOG_MEETMOBOT_RECOM;

/**
 * Created by zztaiwll on 18/10/16.
 */
@Repository
public class LogMeetmobotRecomDao extends JooqCrudImpl<LogMeetmobotRecom, LogMeetmobotRecomRecord> {

    public LogMeetmobotRecomDao(){
        super(LOG_MEETMOBOT_RECOM,LogMeetmobotRecom.class);
    }
    public LogMeetmobotRecomDao(TableImpl<LogMeetmobotRecomRecord> table, Class<LogMeetmobotRecom> logMeetmobotRecomClass){
        super(table, logMeetmobotRecomClass);
    }


}
