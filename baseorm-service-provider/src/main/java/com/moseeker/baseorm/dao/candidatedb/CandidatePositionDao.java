package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.baseorm.util.CURDExceptionUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.candidatedb.tables.CandidatePosition;
import com.moseeker.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidatePositionDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidatePositionDao extends BaseDaoImpl<CandidatePositionRecord, CandidatePosition> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidatePosition.CANDIDATE_POSITION;
    }


    public CandidatePositionDO getCandidatePosition(CommonQuery query) throws CURDException {
        CandidatePositionDO CandidatePositionDO = null;
        try {
            CandidatePositionRecord record = this.getResource(query);
            if(record != null) {
                CandidatePositionDO = BeanUtils.DBToStruct(CandidatePositionDO.class, record);
                return CandidatePositionDO;
            } else {
                throw CURDExceptionUtils.buildGetNothingException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildGetNothingException();
        }
    }

    public List<CandidatePositionDO> listCandidatePositions(CommonQuery query) throws CURDException {
        List<CandidatePositionDO> candidateCompanies = null;
        try {
            List<CandidatePositionRecord> records = this.getResources(query);
            if(records != null && records.size() > 0) {
                candidateCompanies = BeanUtils.DBToStruct(CandidatePositionDO.class, records);
                return candidateCompanies;
            } else {
                throw CURDExceptionUtils.buildGetNothingException();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildGetNothingException();
        }
    }

    public CandidatePositionDO updateCandidatePosition(CandidatePositionDO CandidatePositionDO) throws CURDException {

        CandidatePositionRecord record = BeanUtils.structToDB(CandidatePositionDO, CandidatePositionRecord.class);
        try {
            this.putResource(record);
            BeanUtils.DBToStruct(CandidatePositionDO, record, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildPutException();
        }
        return CandidatePositionDO;
    }

    public CandidatePositionDO saveCandidatePosition(CandidatePositionDO CandidatePositionDO) throws CURDException {

        CandidatePositionRecord record = BeanUtils.structToDB(CandidatePositionDO, CandidatePositionRecord.class);
        try {
            this.postResource(record);
            BeanUtils.DBToStruct(CandidatePositionDO, record, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildPostException();
        }
        return CandidatePositionDO;
    }

    public void deleteCandidatePosition(int id) throws CURDException {
        CandidatePositionRecord record = new CandidatePositionRecord();
        try {
            this.delResource(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw CURDExceptionUtils.buildDelException();
        }
    }
}
