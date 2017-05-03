package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.candidatedb.*;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.CandidateDBDao;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * candidatedb数据库表的增删该查操作
 * Created by jack on 15/02/2017.
 */
@Service
public class CandidateDaoThriftService implements CandidateDBDao.Iface {

    @Autowired
    private CandidateCompanyDao candidateCompanyDao;

    @Autowired
    private CandidatePositionDao candidatePositionDao;

    @Autowired
    private CandidatePositionShareRecordDao candidatePositionShareRecordDao;

    @Autowired
    private CandidateRecomRecordDao candidateRecomRecordDao;

    @Autowired
    private CandidateShareChainDao candidateShareChainDao;

    @Autowired
    private CandidateRemarkDao candidateRemarkDao;

    @Autowired
    private CandidateSuggestPositionDao candidateSuggestPositionDao;

    @Override
    public CandidateRemarkDO getCandidateRemark(CommonQuery query) throws TException {

        return candidateRemarkDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidateRemarkDO> listCandidateRemarks(CommonQuery query) throws CURDException, TException {
        return candidateRemarkDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public CandidateRemarkDO saveCandidateRemark(CandidateRemarkDO candidateRemark) throws TException {
        return candidateRemarkDao.saveResource(candidateRemark);
    }

    @Override
    public CandidateRemarkDO updateCandidateRemark(CandidateRemarkDO candidateRemark) throws CURDException, TException {
        return candidateRemarkDao.updateResource(candidateRemark);
    }

    @Override
    public List<CandidateRemarkDO> updateCandidateRemarks(List<CandidateRemarkDO> candidateRemarks) throws CURDException, TException {
        return candidateRemarkDao.updateResources(candidateRemarks);
    }

    @Override
    public void deleteCandidateRemark(int id) throws TException {
        candidateRemarkDao.deleteCandidateRemark(id);
    }

    @Override
    public CandidateCompanyDO getCandidateCompany(CommonQuery query) throws CURDException, TException {
        return candidateCompanyDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidateCompanyDO> listCandidateCompanys(CommonQuery query) throws CURDException, TException {
        return candidateCompanyDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public CandidateCompanyDO saveCandidateCompanys(CandidateCompanyDO candidateCompany) throws TException {
        return candidateCompanyDao.saveResource(candidateCompany);
    }

    @Override
    public CandidateCompanyDO updateCandidateCompanys(CandidateCompanyDO candidateCompany) throws TException {
        return candidateCompanyDao.updateResource(candidateCompany);
    }

    @Override
    public void deleteCandidateCompany(int id) throws TException {
        candidateCompanyDao.deleteCandidateCompany(id);
    }

    @Override
    public CandidatePositionDO getCandidatePosition(CommonQuery query) throws TException {
        return candidatePositionDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidatePositionDO> listCandidatePositions(CommonQuery query) throws TException {
        return candidatePositionDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidatePositionDO> listCandidatePositionsByPositionIDUserID(List<Map<Integer, Integer>> companyPositionIds) throws TException {
        return candidatePositionDao.listCandidatePositionsByPositionIDUserID(companyPositionIds);
    }

    @Override
    public CandidatePositionDO updateCandidatePosition(CandidatePositionDO candidatePosition) throws TException {
        return candidatePositionDao.updateResource(candidatePosition);
    }

    @Override
    public CandidatePositionDO saveCandidatePosition(CandidatePositionDO candidatePosition) throws com.moseeker.thrift.gen.common.struct.CURDException, TException {
        return candidatePositionDao.saveResource(candidatePosition);
    }

    @Override
    public void deleteCandidatePositions(int userId, int positionId) throws TException {
        candidatePositionDao.deleteCandidatePosition(userId, positionId);
    }

    @Override
    public CandidatePositionShareRecordDO getCandidatePositionShareRecord(CommonQuery query) throws TException {
        return candidatePositionShareRecordDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidatePositionShareRecordDO> listCandidatePositionShareRecord(CommonQuery query) throws TException {
        return candidatePositionShareRecordDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public CandidatePositionShareRecordDO updateCandidatePositionShareRecord(CandidatePositionShareRecordDO candidatePositionShareRecord) throws TException {
        return candidatePositionShareRecordDao.updateResource(candidatePositionShareRecord);
    }

    @Override
    public void deleteCandidatePositionShareRecord(int id) throws TException {
        candidatePositionShareRecordDao.deleteCandidatePositionShareRecord(id);
    }

    @Override
    public CandidateRecomRecordDO getCandidateRecomRecord(CommonQuery query) throws TException {
        return candidateRecomRecordDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecord(int postUserId, String clickTime, List<Integer> recoms) throws com.moseeker.thrift.gen.common.struct.CURDException, TException {
        return candidateRecomRecordDao.listCandidateRecomRecord(postUserId, clickTime, recoms);
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecordExceptId(int id, int postUserId, String clickTime, List<Integer> recoms) throws com.moseeker.thrift.gen.common.struct.CURDException, TException {
        return candidateRecomRecordDao.listCandidateRecomRecordExceptId(id, postUserId, clickTime, recoms);
    }

    @Override
    public int countCandidateRecomRecordCustom(int postUserId, String clickTime, List<Integer> recoms) throws com.moseeker.thrift.gen.common.struct.CURDException, TException {
        return candidateRecomRecordDao.countCandidateRecomRecordCustom(postUserId, clickTime, recoms);
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecords(CommonQuery query) throws TException {
        return candidateRecomRecordDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecordsForApplied(int userId, int pageNo, int pageSize) throws TException {
        return candidateRecomRecordDao.listCandidateRecomRecordsForApplied(userId, pageNo, pageSize);
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecordsForAppliedByUserPositions(int userId, List<Integer> positionIdList, int pageNo, int pageSize) throws TException {
        return candidateRecomRecordDao.listCandidateRecomRecordsForAppliedByUserPositions(userId, positionIdList, pageNo, pageSize);
    }

    @Override
    public List<CandidateRecomRecordDO> listInterestedCandidateRecomRecord(int userId, int pageNo, int pageSize) throws TException {
        return candidateRecomRecordDao.listInterestedCandidateRecomRecord(userId, pageNo, pageSize);
    }

    @Override
    public List<CandidateRecomRecordDO> listInterestedCandidateRecomRecordByUserPositions(int userId, List<Integer> positionIdList, int pageNo, int pageSize) throws TException {
        return candidateRecomRecordDao.listInterestedCandidateRecomRecordByUserPositions(userId, positionIdList, pageNo, pageSize);
    }

    @Override
    public List<CandidateRecomRecordDO> listCandidateRecomRecordsByPositionSetAndPresenteeId(Set<Integer> positionIdSet, int presenteeId, int pageNo, int pageSize) throws TException {
        return candidateRecomRecordDao.listCandidateRecomRecordsByPositionSetAndPresenteeId(positionIdSet, presenteeId, pageNo, pageSize);
    }

    @Override
    public List<CandidateRecomRecordSortingDO> listCandidateRecomRecordSorting(List<Integer> postUserId) throws BIZException, TException {
        return candidateRecomRecordDao.listCandidateRecomRecordSorting(postUserId);
    }

    @Override
    public int countCandidateRecomRecord(CommonQuery query) throws TException {
        try {
            return candidateRecomRecordDao.getResourceCount(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            throw new CURDException();
        }
    }

    @Override
    public int countCandidateRecomRecordDistinctPresentee(int postUserId) throws TException {
        return candidateRecomRecordDao.countCandidateRecomRecordDistinctPresentee(postUserId);
    }

    @Override
    public int countCandidateRecomRecordDistinctPresenteePosition(int postUserId, List<Integer> positionIdList) throws TException {
        return candidateRecomRecordDao.countCandidateRecomRecordDistinctPresenteePosition(postUserId, positionIdList);
    }

    @Override
    public int countAppliedCandidateRecomRecord(int userId) throws TException {
        return candidateRecomRecordDao.countAppliedCandidateRecomRecord(userId);
    }

    @Override
    public int countAppliedCandidateRecomRecordByUserPosition(int userId, List<Integer> positionIdList) throws TException {
        return candidateRecomRecordDao.countAppliedCandidateRecomRecordByUserPosition(userId, positionIdList);
    }

    @Override
    public int countInterestedCandidateRecomRecord(int userId) throws TException {
        return candidateRecomRecordDao.countInterestedCandidateRecomRecord(userId);
    }

    @Override
    public int countInterestedCandidateRecomRecordByUserPosition(int userId, List<Integer> positionIdList) throws TException {
        return candidateRecomRecordDao.countInterestedCandidateRecomRecordByUserPosition(userId, positionIdList);
    }

    @Override
    public CandidateRecomRecordDO updateCandidateRecomRecords(CandidateRecomRecordDO candidateRecomRecord) throws TException {
        return candidateRecomRecordDao.updateResource(candidateRecomRecord);
    }

    @Override
    public void deleteCandidateRecomRecords(int id) throws TException {
        candidateRecomRecordDao.deleteCandidateRecomRecord(id);
    }

    @Override
    public CandidateShareChainDO getCandidateShareChain(CommonQuery query) throws TException {
        return candidateShareChainDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidateShareChainDO> listCandidateShareChain(CommonQuery query) throws TException {
        return candidateShareChainDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public CandidateShareChainDO updateCandidateShareChain(CandidateShareChainDO candidateShareChain) throws TException {
        return candidateShareChainDao.updateResource(candidateShareChain);
    }

    @Override
    public void deleteCandidateShareChain(int id) throws TException {
        candidateShareChainDao.deleteCandidateShareChain(id);
    }

    @Override
    public CandidateSuggestPositionDO getCandidateSuggestPosition(CommonQuery query) throws TException {
        return candidateSuggestPositionDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public List<CandidateSuggestPositionDO> listCandidateSuggestPosition(CommonQuery query) throws TException {
        return candidateSuggestPositionDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public CandidateSuggestPositionDO updateCandidateSuggestPosition(CandidateSuggestPositionDO candidateSuggestPosition) throws TException {
        return candidateSuggestPositionDao.updateResource(candidateSuggestPosition);
    }

    @Override
    public void deleteCandidateSuggestPosition(int id) throws TException {
        candidateSuggestPositionDao.deleteCandidateShareChain(id);
    }
}
