package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 23/03/2017.
 */
@Repository
public class CandidatePositionDaoTest {
    @Autowired
    DefaultDSLContext dsl;

    public CandidatePositionRecord addCandidatePosition(CandidatePositionRecord candidatePositionRecord) {
        dsl.attach(candidatePositionRecord);
        candidatePositionRecord.insert();
        return candidatePositionRecord;
    }
}
