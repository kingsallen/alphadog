package com.moseeker.baseorm.dao.logdb;

import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lucky8987 on 17/10/11.
 */
@Repository
public class LogResumeDao {

    @Autowired
    protected DefaultDSLContext create;

    @Transactional
    public LogResumeRecordRecord addRecord(LogResumeRecordRecord r) {
        create.execute("set names utf8mb4");
        create.attach(r);
        r.insert();
        return r;
    }

    @Transactional
    public int[] addRecords(List<LogResumeRecordRecord> list) {
        create.execute("set names utf8mb4");
        return create.batchInsert(list).execute();
    }
}
