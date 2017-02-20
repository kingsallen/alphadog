package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateCompanyDao extends StructDaoImpl<CandidateCompanyDO, CandidateCompanyRecord, CandidateCompany> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateCompany.CANDIDATE_COMPANY;
    }

    public void deleteCandidateCompany(int id) throws CURDException {
        CandidateCompanyDO r = new CandidateCompanyDO();
        r.setId(id);
        this.deleteResource(r);
    }
}
