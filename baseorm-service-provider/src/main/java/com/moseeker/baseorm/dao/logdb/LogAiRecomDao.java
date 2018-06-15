package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.logdb.tables.pojos.LogAiRecom;
import com.moseeker.baseorm.db.logdb.tables.records.LogAiRecomRecord;
import static com.moseeker.baseorm.db.logdb.tables.LogAiRecom.LOG_AI_RECOM;
import static org.jooq.impl.DSL.using;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zztaiwll on 18/5/22.
 */
@Service
public class LogAiRecomDao extends JooqCrudImpl<LogAiRecom, LogAiRecomRecord> {

    public LogAiRecomDao() {
        super(LOG_AI_RECOM, LogAiRecom.class);
    }

    public LogAiRecomDao(TableImpl<LogAiRecomRecord> table, Class<LogAiRecom> logAiRecomClass) {
        super(table, logAiRecomClass);
    }

    public List<Integer> batchAdd(List<LogAiRecomRecord> list){
       create.execute("set names utf8mb4");
       create.attach(list);
       create.batchInsert(list).execute();
       List<Integer> result=new ArrayList<>();
       for(LogAiRecomRecord recomRecord:list){
           result.add(recomRecord.getId());
       }
       return result;
    }
    public int addrecord(LogAiRecomRecord recomRecord){
        create.attach(recomRecord);
        recomRecord.insert();
        return recomRecord.getId();
    }
}
