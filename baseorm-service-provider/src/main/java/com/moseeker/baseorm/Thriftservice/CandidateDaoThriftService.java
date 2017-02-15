package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.CandidateDBDao;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * candidatedb数据库表的增删该查操作
 * Created by jack on 15/02/2017.
 */
public class CandidateDaoThriftService implements CandidateDBDao.Iface {

    @Autowired
    private CandidateCompanyDao candidateCompanyDao;

    @Override
    public CandidateRemarkDO getCandidateRemark(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public List<CandidateRemarkDO> listCandidateRemarks(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public CandidateRemarkDO saveCandidateRemark(CandidateRemarkDO candidateRemark) throws CURDException, TException {
        return null;
    }

    @Override
    public void deleteCandidateRemark(int id) throws CURDException, TException {

    }

    @Override
    public CandidateCompanyDO getCandidateCompany(CommonQuery query) throws CURDException, TException {
        return candidateCompanyDao.getCandidateCompany(query);
    }

    @Override
    public List<CandidateCompanyDO> listCandidateCompanys(CommonQuery query) throws CURDException, TException {
        return candidateCompanyDao.listCandidateCompanys(query);
    }

    @Override
    public CandidateCompanyDO saveCandidateCompanys(CandidateCompanyDO candidateCompany) throws CURDException, TException {
        return candidateCompanyDao.saveCandidateCompany(candidateCompany);
    }

    @Override
    public CandidateCompanyDO updateCandidateCompanys(CandidateCompanyDO candidateCompany) throws CURDException, TException {
        return candidateCompanyDao.updateCandidateCompany(candidateCompany);
    }

    @Override
    public void deleteCandidateCompany(int id) throws CURDException, TException {
        candidateCompanyDao.deleteCandidateCompany(id);
    }

    @Override
    public CandidatePositionDO getCandidatePosition(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public List<CandidatePositionDO> listCandidatePositions(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public CandidatePositionDO updateCandidatePositions(CandidatePositionDO candidatePosition) throws CURDException, TException {
        return null;
    }

    @Override
    public void deleteCandidatePositions(int userId, int positionId) throws CURDException, TException {

    }

    @Override
    public CandidatePositionShareRecordDO getCandidatePositionShareRecord(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public List<CandidatePositionShareRecordDO> listCandidatePositionShareRecord(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public CandidatePositionShareRecordDO updateCandidatePositionShareRecord(CandidatePositionShareRecordDO candidatePositionShareRecord) throws CURDException, TException {
        return null;
    }

    @Override
    public void deleteCandidatePositionShareRecord(int id) throws CURDException, TException {

    }

    @Override
    public CandidateRecomRecordDO getCandidateRecomRecord(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecords(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public CandidateRecomRecordDO updateCandidateRecomRecords(CandidateRecomRecordDO candidateRecomRecord) throws CURDException, TException {
        return null;
    }

    @Override
    public void deleteCandidateRecomRecords(int id) throws CURDException, TException {

    }

    @Override
    public CandidateShareChainDO getCandidateShareChain(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public List<CandidateShareChainDO> listCandidateShareChain(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public CandidateShareChainDO updateCandidateShareChain(CandidateShareChainDO candidateShareChain) throws CURDException, TException {
        return null;
    }

    @Override
    public void deleteCandidateShareChain(int id) throws CURDException, TException {

    }

    @Override
    public CandidateSuggestPositionDO getCandidateSuggestPosition(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public List<CandidateSuggestPositionDO> listCandidateSuggestPosition(CommonQuery query) throws CURDException, TException {
        return null;
    }

    @Override
    public CandidateSuggestPositionDO updateCandidateSuggestPosition(CandidateSuggestPositionDO candidateSuggestPosition) throws CURDException, TException {
        return null;
    }

    @Override
    public void deleteCandidateSuggestPosition(int id) throws CURDException, TException {

    }
}
