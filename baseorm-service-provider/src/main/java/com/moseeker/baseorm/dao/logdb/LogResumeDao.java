package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lucky8987 on 17/10/11.
 */
@Repository
public class LogResumeDao {

    @Autowired
    protected DefaultDSLContext create;

    public LogResumeRecordRecord addRecord(LogResumeRecordRecord r) {
        create.execute("set names utf8mb4");
        create.attach(r);
        r.insert();
        return r;
    }

    public int[] addRecords(List<LogResumeRecordRecord> list) {
        create.execute("set names utf8mb4");
        return create.batchInsert(list).execute();
    }
}
