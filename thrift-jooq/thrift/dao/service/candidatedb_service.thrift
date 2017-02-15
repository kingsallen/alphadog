namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/candidatedb_struct.thrift"

service CandidateDBDao {
    //查询HR标记的候选人信息  
	candidatedb_struct.CandidateRemarkDO getCandidateRemark(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
	list<candidatedb_struct.CandidateRemarkDO> listCandidateRemarks(1:common_struct.CommonQuery query) throws (1:candidatedb_struct.CURDException e)
    candidatedb_struct.CandidateRemarkDO saveCandidateRemark(1:candidatedb_struct.CandidateRemarkDO candidateRemark) throws (1:candidatedb_struct.CURDException e)
    void deleteCandidateRemark(1: i32 id) throws (1:candidatedb_struct.CURDException e)

	candidatedb_struct.CandidateCompanyDO getCandidateCompany(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
	list<candidatedb_struct.CandidateCompanyDO> listCandidateCompanys(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
	candidatedb_struct.CandidateCompanyDO saveCandidateCompanys(1:candidatedb_struct.CandidateCompanyDO candidateCompany) throws (1: candidatedb_struct.CURDException e)
	candidatedb_struct.CandidateCompanyDO updateCandidateCompanys(1:candidatedb_struct.CandidateCompanyDO candidateCompany) throws (1: candidatedb_struct.CURDException e)
	void deleteCandidateCompany(1:i32 id) throws (1: candidatedb_struct.CURDException e)
	
    candidatedb_struct.CandidatePositionDO getCandidatePosition(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    list<candidatedb_struct.CandidatePositionDO> listCandidatePositions(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    candidatedb_struct.CandidatePositionDO updateCandidatePositions(1: candidatedb_struct.CandidatePositionDO candidatePosition) throws (1: candidatedb_struct.CURDException e)
    void deleteCandidatePositions(1: i32 userId, 2: i32 positionId) throws (1: candidatedb_struct.CURDException e)
    
    candidatedb_struct.CandidatePositionShareRecordDO getCandidatePositionShareRecord(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    list<candidatedb_struct.CandidatePositionShareRecordDO> listCandidatePositionShareRecord(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    candidatedb_struct.CandidatePositionShareRecordDO updateCandidatePositionShareRecord(1: candidatedb_struct.CandidatePositionShareRecordDO candidatePositionShareRecord) throws (1: candidatedb_struct.CURDException e)
    void deleteCandidatePositionShareRecord(1: i32 id) throws (1: candidatedb_struct.CURDException e)
    
    candidatedb_struct.CandidateRecomRecordDO getCandidateRecomRecord(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    list<candidatedb_struct.CandidateRecomRecordDO> listCandidateRecomRecords(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    candidatedb_struct.CandidateRecomRecordDO updateCandidateRecomRecords(1: candidatedb_struct.CandidateRecomRecordDO candidateRecomRecord) throws (1: candidatedb_struct.CURDException e)
    void deleteCandidateRecomRecords(1: i32 id) throws (1: candidatedb_struct.CURDException e)

    candidatedb_struct.CandidateShareChainDO getCandidateShareChain(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    list<candidatedb_struct.CandidateShareChainDO> listCandidateShareChain(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    candidatedb_struct.CandidateShareChainDO updateCandidateShareChain(1: candidatedb_struct.CandidateShareChainDO candidateShareChain) throws (1: candidatedb_struct.CURDException e)
    void deleteCandidateShareChain(1: i32 id) throws (1: candidatedb_struct.CURDException e)
    
    candidatedb_struct.CandidateSuggestPositionDO getCandidateSuggestPosition(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    list<candidatedb_struct.CandidateSuggestPositionDO> listCandidateSuggestPosition(1:common_struct.CommonQuery query) throws (1: candidatedb_struct.CURDException e)
    candidatedb_struct.CandidateSuggestPositionDO updateCandidateSuggestPosition(1: candidatedb_struct.CandidateSuggestPositionDO candidateSuggestPosition) throws (1: candidatedb_struct.CURDException e)
    void deleteCandidateSuggestPosition(1: i32 id) throws (1: candidatedb_struct.CURDException e)
}
