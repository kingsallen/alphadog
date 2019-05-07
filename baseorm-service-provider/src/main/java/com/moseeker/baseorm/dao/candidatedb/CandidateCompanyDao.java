package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public CandidateCompanyDO updateCandidateCompanys(CandidateCompanyDO candidateCompanyDO) throws CURDException {
        CandidateCompanyRecord candidateCompanyRecord = this.dataToRecord(candidateCompanyDO);
        create.attach(candidateCompanyRecord);
        candidateCompanyRecord.update();
        return recordToData(candidateCompanyRecord);
    }

    public void updateCandidateCompanySetPositionWxLayerQrcode(int id, byte status) throws CURDException {
        create.update(CandidateCompany.CANDIDATE_COMPANY)
                .set(CandidateCompany.CANDIDATE_COMPANY.POSITION_WX_LAYER_QRCODE, status)
                .where(CandidateCompany.CANDIDATE_COMPANY.ID.eq(id))
                .execute();
    }

    public void updateCandidateCompanySetPositionWxLayerProfile(int id, byte status) throws CURDException {
        create.update(CandidateCompany.CANDIDATE_COMPANY)
                .set(CandidateCompany.CANDIDATE_COMPANY.POSITION_WX_LAYER_PROFILE, status)
                .where(CandidateCompany.CANDIDATE_COMPANY.ID.eq(id))
                .execute();
    }

    public CandidateCompanyDO saveCandidateCompany(CandidateCompanyDO candidateCompanyDO) throws CURDException {
        candidateCompanyDO = addData(candidateCompanyDO);
        return candidateCompanyDO;
    }

    public void deleteCandidateCompany(int id) throws CURDException {
        CandidateCompanyRecord record = new CandidateCompanyRecord();
        record.setId(id);
        create.attach(record);
        record.delete();
    }

    /**
     * 根据公司编号和用户编号查找潜在候选人
     * @param companyId 公司编号
     * @param userIDList 用户编号
     * @return 潜在候选人
     */
    public List<CandidateCompanyDO> getCandidateCompanyByCompanyIDAndUserID(int companyId, List<Integer> userIDList) {
        List<CandidateCompanyDO> candidateCompanyDOList = new ArrayList<>();
        List<CandidateCompanyRecord> candidateCompanyRecords =
                create.selectFrom(CandidateCompany.CANDIDATE_COMPANY)
                        .where(CandidateCompany.CANDIDATE_COMPANY.COMPANY_ID.eq(companyId))
                        .and(CandidateCompany.CANDIDATE_COMPANY.STATUS.eq(AbleFlag.ENABLE.getValue()))
                        .and(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.in(userIDList)).fetch();
        if (candidateCompanyRecords != null && candidateCompanyRecords.size() > 0) {
            for (CandidateCompanyRecord record : candidateCompanyRecords) {
                candidateCompanyDOList.add(recordToData(record));
            }
        }
        return candidateCompanyDOList;
    }
}
