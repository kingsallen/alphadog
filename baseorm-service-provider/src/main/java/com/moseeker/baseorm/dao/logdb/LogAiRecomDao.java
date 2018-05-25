package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogAiRecom;
import com.moseeker.baseorm.db.logdb.tables.records.LogAiRecomRecord;
import static com.moseeker.baseorm.db.logdb.tables.LogAiRecom.LOG_AI_RECOM;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Component;

/**
 * Created by zztaiwll on 18/5/22.
 */
@Component
public class LogAiRecomDao extends JooqCrudImpl<LogAiRecom, LogAiRecomRecord> {

    public LogAiRecomDao() {
        super(LOG_AI_RECOM, LogAiRecom.class);
    }

    public LogAiRecomDao(TableImpl<LogAiRecomRecord> table, Class<LogAiRecom> logAiRecomClass) {
        super(table, logAiRecomClass);
    }
}
