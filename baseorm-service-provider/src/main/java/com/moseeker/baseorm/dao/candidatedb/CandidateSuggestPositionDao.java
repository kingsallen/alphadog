package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidateSuggestPosition;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateSuggestPositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateSuggestPositionDO;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateSuggestPositionDao extends StructDaoImpl<CandidateSuggestPositionDO, CandidateSuggestPositionRecord, CandidateSuggestPosition> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateSuggestPosition.CANDIDATE_SUGGEST_POSITION;
    }

    public void deleteCandidateShareChain(int id) throws CURDException {
        CandidateSuggestPositionDO p = new CandidateSuggestPositionDO();
        p.setId(id);
        this.deleteResource(p);
    }
}
