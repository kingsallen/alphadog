package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChainRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateShareChainDO;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateShareChainDao extends StructDaoImpl<CandidateShareChainDO, CandidateShareChainRecord, CandidateShareChain> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateShareChain.CANDIDATE_SHARE_CHAIN;
    }

    public void deleteCandidateShareChain(int id) throws CURDException {
        CandidateShareChainDO p = new CandidateShareChainDO();
        p.setId(id);
        this.deleteResource(p);
    }
}
