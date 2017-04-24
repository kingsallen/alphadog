namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/candidatedb_struct.thrift"

service CandidateDBDao {
    //查询HR标记的候选人信息  
	candidatedb_struct.CandidateRemarkDO getCandidateRemark(1:common_struct.CommonQuery query) 
	list<candidatedb_struct.CandidateRemarkDO> listCandidateRemarks(1:common_struct.CommonQuery query) throws (1:candidatedb_struct.CURDException e)
    candidatedb_struct.CandidateRemarkDO saveCandidateRemark(1:candidatedb_struct.CandidateRemarkDO candidateRemark) throws (1:candidatedb_struct.CURDException e)
    candidatedb_struct.CandidateRemarkDO updateCandidateRemark(1:candidatedb_struct.CandidateRemarkDO candidateRemark) throws (1:candidatedb_struct.CURDException e)
    list<candidatedb_struct.CandidateRemarkDO> updateCandidateRemarks(1:list<candidatedb_struct.CandidateRemarkDO> candidateRemarks) throws (1:candidatedb_struct.CURDException e)
    void deleteCandidateRemark(1: i32 id) throws (1:candidatedb_struct.CURDException e)

	candidatedb_struct.CandidateCompanyDO getCandidateCompany(1:common_struct.CommonQuery query) 
	list<candidatedb_struct.CandidateCompanyDO> listCandidateCompanys(1:common_struct.CommonQuery query) 
	candidatedb_struct.CandidateCompanyDO saveCandidateCompanys(1:candidatedb_struct.CandidateCompanyDO candidateCompany) 
	candidatedb_struct.CandidateCompanyDO updateCandidateCompanys(1:candidatedb_struct.CandidateCompanyDO candidateCompany) 
	void deleteCandidateCompany(1:i32 id) 
	
    candidatedb_struct.CandidatePositionDO getCandidatePosition(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidatePositionDO> listCandidatePositions(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidatePositionDO> listCandidatePositionsByPositionIDUserID(1: list<map<i32, i32>> companyPositionIds) 
    candidatedb_struct.CandidatePositionDO updateCandidatePosition(1: candidatedb_struct.CandidatePositionDO candidatePosition) 
    candidatedb_struct.CandidatePositionDO saveCandidatePosition(1: candidatedb_struct.CandidatePositionDO candidatePosition) throws (1:common_struct.CURDException e) 
    void deleteCandidatePositions(1: i32 userId, 2: i32 positionId) 
    
    candidatedb_struct.CandidatePositionShareRecordDO getCandidatePositionShareRecord(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidatePositionShareRecordDO> listCandidatePositionShareRecord(1:common_struct.CommonQuery query) 
    candidatedb_struct.CandidatePositionShareRecordDO updateCandidatePositionShareRecord(1: candidatedb_struct.CandidatePositionShareRecordDO candidatePositionShareRecord) 
    void deleteCandidatePositionShareRecord(1: i32 id) 
    
    candidatedb_struct.CandidateRecomRecordDO getCandidateRecomRecord(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecord(1: i32 postUserId, 2 : string clickTime, 3 : list<i32> recoms) throws (1: common_struct.CURDException e) 
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecordExceptId(1:i32 id, 2:i32 postUserId, 3:string clickTime, 4:list<i32> recoms) throws (1: common_struct.CURDException e) 
    i32 countCandidateRecomRecordCustom(1: i32 postUserId, 2 : string clickTime, 3 : list<i32> recoms) throws (1: common_struct.CURDException e) 
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecords(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecordsForApplied(1: i32 userId, 2: i32 pageNo, 3: i32 pageSize) 
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecordsForAppliedByUserPositions(1: i32 userId, 2: list<i32> positionIdList, 3: i32 pageNo, 4: i32 pageSize) 
    list<candidatedb_struct.CandidateRecomRecordDO> listInterestedCandidateRecomRecord(1: i32 userId, 2: i32 pageNo, 3: i32 pageSize) 
    list<candidatedb_struct.CandidateRecomRecordDO> listInterestedCandidateRecomRecordByUserPositions(1: i32 userId, 2: list<i32> positionIdList, 3: i32 pageNo, 4: i32 pageSize) 
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecordsByPositionSetAndPresenteeId(1: set<i32> positionIdSet, 2:i32 presenteeId, 3: i32 pageNo, 4: i32 pageSize) 
    list<candidatedb_struct.CandidateRecomRecordSortingDO> listCandidateRecomRecordSorting(1: list<i32> postUserId) throws (1:common_struct.BIZException e) 
    i32 countCandidateRecomRecord(1: common_struct.CommonQuery query) 
    i32 countCandidateRecomRecordDistinctPresentee(1: i32 postUserId) 
    i32 countCandidateRecomRecordDistinctPresenteePosition(1: i32 postUserId, 2: list<i32> positionIdList) 
    i32 countAppliedCandidateRecomRecord(1: i32 userId) 
    i32 countAppliedCandidateRecomRecordByUserPosition(1: i32 userId, 2: list<i32> positionIdList) 
    i32 countInterestedCandidateRecomRecord(1: i32 userId) 
    i32 countInterestedCandidateRecomRecordByUserPosition(1: i32 userId, 2: list<i32> positionIdList) 
    candidatedb_struct.CandidateRecomRecordDO updateCandidateRecomRecords(1: candidatedb_struct.CandidateRecomRecordDO candidateRecomRecord) 
    void deleteCandidateRecomRecords(1: i32 id) 

    candidatedb_struct.CandidateShareChainDO getCandidateShareChain(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidateShareChainDO> listCandidateShareChain(1:common_struct.CommonQuery query) 
    candidatedb_struct.CandidateShareChainDO updateCandidateShareChain(1: candidatedb_struct.CandidateShareChainDO candidateShareChain) 
    void deleteCandidateShareChain(1: i32 id) 
    
    candidatedb_struct.CandidateSuggestPositionDO getCandidateSuggestPosition(1:common_struct.CommonQuery query) 
    list<candidatedb_struct.CandidateSuggestPositionDO> listCandidateSuggestPosition(1:common_struct.CommonQuery query) 
    candidatedb_struct.CandidateSuggestPositionDO updateCandidateSuggestPosition(1: candidatedb_struct.CandidateSuggestPositionDO candidateSuggestPosition) 
    void deleteCandidateSuggestPosition(1: i32 id) 
}
