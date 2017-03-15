package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidatePositionShareRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionShareRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidatePositionShareRecordDO;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidatePositionShareRecordDao extends StructDaoImpl<CandidatePositionShareRecordDO, CandidatePositionShareRecordRecord, CandidatePositionShareRecord> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD;
    }

    public void deleteCandidatePositionShareRecord(int id) throws CURDException {
        CandidatePositionShareRecordDO p = new CandidatePositionShareRecordDO();
        p.setId(id);
        this.deleteResource(p);
    }
}
