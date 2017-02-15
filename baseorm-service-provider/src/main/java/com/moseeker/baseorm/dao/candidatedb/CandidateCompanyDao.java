package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.baseorm.util.CURDExceptionUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.candidatedb.tables.CandidateCompany;
import com.moseeker.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateCompanyDao extends BaseDaoImpl<CandidateCompanyRecord, CandidateCompany> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateCompany.CANDIDATE_COMPANY;
    }


    public CandidateCompanyDO getCandidateCompany(CommonQuery query) throws CURDException {
        CandidateCompanyDO candidateCompanyDO = null;
        try {
            CandidateCompanyRecord record = this.getResource(query);
            if(record != null) {
                candidateCompanyDO = BeanUtils.DBToStruct(CandidateCompanyDO.class, record);
                return candidateCompanyDO;
            } else {
                throw CURDExceptionUtils.buildGetNothingException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildGetNothingException();
        }
    }

    public List<CandidateCompanyDO> listCandidateCompanys(CommonQuery query) throws CURDException {
        List<CandidateCompanyDO> candidateCompanies = null;
        try {
            List<CandidateCompanyRecord> records = this.getResources(query);
            if(records != null && records.size() > 0) {
                candidateCompanies = BeanUtils.DBToStruct(CandidateCompanyDO.class, records);
                return candidateCompanies;
            } else {
                throw CURDExceptionUtils.buildGetNothingException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildGetNothingException();
        }
    }

    public CandidateCompanyDO updateCandidateCompany(CandidateCompanyDO candidateCompanyDO) throws CURDException {

        CandidateCompanyRecord record = BeanUtils.structToDB(candidateCompanyDO, CandidateCompanyRecord.class);
        try {
            this.putResource(record);
            BeanUtils.DBToStruct(candidateCompanyDO, record, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildPutException();
        }
        return candidateCompanyDO;
    }

    public CandidateCompanyDO saveCandidateCompany(CandidateCompanyDO candidateCompanyDO) throws CURDException {

        CandidateCompanyRecord record = BeanUtils.structToDB(candidateCompanyDO, CandidateCompanyRecord.class);
        try {
            this.postResource(record);
            BeanUtils.DBToStruct(candidateCompanyDO, record, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildPostException();
        }
        return candidateCompanyDO;
    }

    public void deleteCandidateCompany(int id) throws CURDException {
        CandidateCompanyRecord record = new CandidateCompanyRecord();
        record.setId(id);
        try {
            this.delResource(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildDelException();
        }
    }
}
