package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordDO;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateRecomRecordDao extends StructDaoImpl<CandidateRecomRecordDO, CandidateRecomRecordRecord, CandidateRecomRecord> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateRecomRecord.CANDIDATE_RECOM_RECORD;
    }

    public void deleteCandidateRecomRecord(int id) throws CURDException {
        CandidateRecomRecordDO p = new CandidateRecomRecordDO();
        p.setId(id);
        this.deleteResource(p);
    }
}
