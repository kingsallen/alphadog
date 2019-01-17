# file: candidate_service.thrift

include "../../common/struct/common_struct.thrift"
include "../struct/candidate_struct.thrift"
namespace java com.moseeker.thrift.gen.candidate.service

service CandidateService {
    void glancePosition(1:i32 userId, 2:i32 positionId, 3:i32 shareChainId);
    
     // 感兴趣职位
    common_struct.Response changeInteresting(1: i32 user_id, 2: i32 position_id, 3: i8 is_interested);
    //查找职位转发浏览者信息
    list<candidate_struct.CandidateList> candidateList(1: candidate_struct.CandidateListParam param) throws (1: common_struct.BIZException e);
    //查找职位转发浏览记录
    candidate_struct.RecommendResult getRecomendations(1: i32 companyId, 2: list<i32> idList) throws (1: common_struct.BIZException e);
    //推荐职位浏览者 
    candidate_struct.RecommendResult recommend(1: candidate_struct.RecommmendParam param) throws (1: common_struct.BIZException e);
    //查找推荐信息
    candidate_struct.RecomRecordResult getRecommendation(1: i32 id, 2:i32 postUserId) throws (1: common_struct.BIZException e);
    //获取员工在公司的排序列表
    candidate_struct.SortResult getRecommendatorySorting(1: i32 postUserId, 2:i32 companyId) throws (1: common_struct.BIZException e);
    //忽略
    candidate_struct.RecommendResult ignore(1: i32 id, 2:i32 companyId, 3:i32 postUserId, 4:string clickTime) throws (1: common_struct.BIZException e);
     //潜在候选人详情
    common_struct.Response getCandidateInfo(1: i32 hrId, 2: i32 userId, 3: i32 positionId) throws (1: common_struct.BIZException e);
    //查找候选人最近浏览的职位
    candidate_struct.RecentPosition getRecentPosition(1:i32 hrId, 2:i32 userId) throws (1: common_struct.BIZException e);
    //申请时增加员工一度链路编号
    common_struct.Response addApplicationReferral(1: i32 applicationId, 2: i32 pscId, 3: i32 directReferralUserId) throws (1: common_struct.BIZException e);
    //根据申请查询员工一度链路信息
    common_struct.Response getApplicationReferral(1: i32 applicationId) throws (1: common_struct.BIZException e);
    //候选人进入职位详情弹层数据
    candidate_struct.PositionLayerInfo getPositionLayerInfo(1: i32 userId, 2: i32 companyId, 3: i32 positionId) throws (1: common_struct.BIZException e);
    //候选人进入职位详情弹层数据
    common_struct.Response handerElasticLayer(1: i32 userId, 2: i32 companyId, 3: i32 type ) throws (1: common_struct.BIZException e);
    //根据appIds获取CandidateRecom
    common_struct.Response getCandidateRecoms(1: list<i32> appIds) throws (1: common_struct.BIZException e);

}

