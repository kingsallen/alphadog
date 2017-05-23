package com.moseeker.baseorm.dao.historydb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.historydb.tables.HistoryJobApplication;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryJobApplicationRecord;
import com.moseeker.thrift.gen.dao.struct.historydb.HistoryJobApplicationDO;
@Service
public class HistoryJobApplicationDao extends JooqCrudImpl<HistoryJobApplicationDO, HistoryJobApplicationRecord> {

    public HistoryJobApplicationDao() {
        super(HistoryJobApplication.HISTORY_JOB_APPLICATION, HistoryJobApplicationDO.class);
    }


    public HistoryJobApplicationDao(TableImpl<HistoryJobApplicationRecord> table, Class<HistoryJobApplicationDO> historyJobApplicationDOClass) {
        super(table, historyJobApplicationDOClass);
    }

}
