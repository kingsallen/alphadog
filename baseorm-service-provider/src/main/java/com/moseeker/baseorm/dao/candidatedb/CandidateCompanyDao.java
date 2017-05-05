package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateCompanyDao extends JooqCrudImpl<CandidateCompanyDO, CandidateCompanyRecord> {

    public CandidateCompanyDao() {
        super(CandidateCompany.CANDIDATE_COMPANY, CandidateCompanyDO.class);
    }

    public CandidateCompanyDao(TableImpl<CandidateCompanyRecord> table, Class<CandidateCompanyDO> candidateCompanyDOClass) {
        super(table, candidateCompanyDOClass);
    }

    public CandidateCompanyDO getCandidateCompany(Query query) throws CURDException {
        return getData(query);
    }

    public List<CandidateCompanyDO> listCandidateCompanys(Query query) throws CURDException {
        return getDatas(query);
    }

    public CandidateCompanyDO updateCandidateCompany(CandidateCompanyDO candidateCompanyDO) throws CURDException {

        CandidateCompanyRecord candidateCompanyRecord = this.dataToRecord(candidateCompanyDO);
        create.attach(candidateCompanyRecord);
        candidateCompanyRecord.update();
        return recordToData(candidateCompanyRecord);
    }

    public CandidateCompanyDO saveCandidateCompany(CandidateCompanyDO candidateCompanyDO) throws CURDException {
        addData(candidateCompanyDO);
        return candidateCompanyDO;
    }

    public void deleteCandidateCompany(int id) throws CURDException {
        CandidateCompanyRecord record = new CandidateCompanyRecord();
        record.setId(id);
        create.attach(record);
        record.delete();
    }
}
