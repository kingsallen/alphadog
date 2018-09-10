include "../struct/position_struct.thrift"
include "../../common/struct/common_struct.thrift"
include "../../apps/struct/appbs_struct.thrift"
include "../../dao/struct/dao_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_position_struct.thrift"
include "../../dao/struct/campaignrvo_struct.thrift"
include "../../dao/struct/jobdb/job_position_struct.thrift"
include "../struct/third_position_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_account_struct.thrift"
include "../../dao/struct/jobdb/job_pc_reported_struct.thrift"
include "../../dao/struct/jobdb/job_position_liepin_mapping.thrift"

namespace java com.moseeker.thrift.gen.position.service
/*
    查询第三方职位职能
*/
service PositionServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getRecommendedPositions(1:i32 pid);
    common_struct.Response verifyCustomize(1:i32 positionId);
    // 根据职位Id获取当前职位
    common_struct.Response getPositionById(1:i32 positionId);  
    //获取公司两种自定义的字段
    common_struct.Response CustomField(1:string param);
    //是否可以刷新
    bool ifAllowRefresh(1:i32 positionId, 2: i32 account_id);
    list<string> getThirdPartyPositions(1: common_struct.CommonQuery query);

    // 批量修改职位
    common_struct.Response batchHandlerJobPostion(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // 批量修改职位并且同步
    common_struct.Response saveAndSync(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // 删除职位
    common_struct.Response deleteJobposition(1:position_struct.DelePostion delePostion);
    // 通过companyId和部门名获取TeamId
    common_struct.Response getTeamIdByDepartmentName(1:i32 companyId, 2:string departmentName);

    // 通过职位id，返回第三方渠道需要的格式，用于职位同步， channel=5，支付宝
    common_struct.Response getPositionForThirdParty(1:i32 positionId, 2:i32 channel);

    // 根据指定渠道 channel=5（支付宝），指定时间段（"2017-05-10 14:57:14"），指定类型 type=0 插入、更新， 1 刷新， 2 下架， 返回第三方渠道同步的职位id列表。
    list<i32> getPositionListForThirdParty(1:i32 channel,2:i32 type, 3:string start_time, 4:string end_time);

    //微信端职位列表
    list<position_struct.WechatPositionListData> getPositionList(1: position_struct.WechatPositionListQuery query);

    //微信端职位列表的附加红包信息
    list<position_struct.RpExtInfo> getPositionListRpExt(1: list<i32> pids);

    //微信红包职位列表
    list<position_struct.WechatRpPositionListData> getRpPositionList(1: i32 hb_config_id,2:i32 pageNum,3:i32 pageSize);

    //微信获取红包转发信息
    position_struct.WechatShareData getShareInfo(1: i32 hb_config_id);

    /**Gamma 0.9 接口**/
    // 获取职位列表页头图信息
    campaignrvo_struct.CampaignHeadImageVO headImage();
    // 查询单个职位详情
    position_struct.PositionDetailsVO positionDetails(1:i32 positionId);

    // 查询公司热招职位的详细信息
    position_struct.PositionDetailsListVO companyHotPositionDetailsList(1:i32 companyId, 2:i32 page, 3:i32 per_age);
    // 职位相关职位接口
    position_struct.PositionDetailsListVO similarityPositionDetailsList(1:i32 pid, 2:i32 page, 3:i32 per_age);

    third_position_struct.ThirdPartyPositionResult getThirdPartyPositionInfo(1:third_position_struct.ThirdPartyPositionInfoForm infoForm ) throws (1: common_struct.BIZException e);

    i32 updateThirdPartyPosition(1:hr_third_party_position_struct.HrThirdPartyPositionDO thirdPartyPosition,2:map<string,string> extData) throws (1: common_struct.BIZException e);

    i32 updateThirdPartyPositionWithAccount(1:hr_third_party_position_struct.HrThirdPartyPositionDO thirdPartyPosition,2:hr_third_party_account_struct.HrThirdPartyAccountDO thirdPartyAccount,3:map<string,string> extData) throws (1: common_struct.BIZException e);

    // 通过hr在猎聘的用户id获取hr在猎聘发布的职位id
    list<job_position_liepin_mapping.JobPositionLiepinMappingDO> getLiepinPositionIds(1:i32 userId);

    //获取pc端职位推荐
    common_struct.Response getPcRecommand(1:i32 page,2:i32 pageSize);

    common_struct.Response getPcRecommandCompany(1:i32 page,2:i32 pageSize);

    common_struct.Response getPcRecommandCompanyAll(1:i32 page,2:i32 pageSize);

    common_struct.Response getPcPositionDetail(1:i32 positionId);

    common_struct.Response getPcRecommendPosition(1:i32 positionId,2:i32 page,3:i32 pageSize);

    common_struct.Response addPcReport(1:job_pc_reported_struct.JobPcReportedDO jobPcReportedDO);

    common_struct.Response getPcAdvertisement(1:i32 page,2:i32 pageSize);

    common_struct.Response getPositionRecommendByModuleId(1:i32 page,2:i32 pageSize,3:i32 moduleId);

    common_struct.Response getThirdpartySyncedPositions(1:i32 channel,2:i32 publisher,3:i32 companyId,4:i32 candidateSource,5:i32 page,6:i32 pageSize);

    common_struct.Response putAlipayResult(1:i32 channel,2:i32 positionId,3:i32 alipayJobId );

    common_struct.Response getPersonaRecomPositionList(1:i32 userId,2:i32 companyId,3:i32 type,4:i32 pageNum,5:i32 pageSize);

    common_struct.Response positionCvConf(1:i32 positionId);

    common_struct.Response getEmployeeRecomPositionByIds(1:i32 recomPushId,2:i32 companyId,3:i32 type,4:i32 pageNum,5: i32 pageSize);

    common_struct.Response updatePosition(1:string param);

    common_struct.Response getMiniPositionList(1:i32 accountId,2:string keyword,3:i32 page,4: i32 pageSize);

    common_struct.Response getMiniPositionSuggest(1:i32 accountId,2:string keyword,3:i32 page,4: i32 pageSize);

    common_struct.Response getMiniPositionNumStatus(1:i32 accountId,2:string keyword,3:i32 page,4: i32 pageSize);

    common_struct.Response getMiniPositionDetail(1:i32 positionId);

    common_struct.Response getMiniPositionShare(1:i32 positionId);


     common_struct.Response getFeatureByPId(1:i32 pid);

     common_struct.Response updatePositionFeature(1:i32 pid,2:i32 fid);

     common_struct.Response updatePositionFeatures(1:i32 pid,2:list<i32> fidList);

     common_struct.Response updatePositionFeatureBatch(1: list<position_struct.JobPositionHrCompanyFeatureDO> featureList);

     common_struct.Response getPositionFeatureBetch(1:list<i32> pidList);

    //内推职位列表
    list<position_struct.WechatPositionListData> getReferralPositionList(1: map<string,string> query);
}
/*
	查询第三方自定义职能
*/
service PositionDao{
	common_struct.Response getJobCustoms(1:common_struct.CommonQuery query);
	common_struct.Response getJobOccupations(1:common_struct.CommonQuery query);
}

/*
    内推职位功能
*/
service ReferralPositionServices{
    void putReferralPositions(1:list<i32> pids);
    void delReferralPositions(1:list<i32> pids);
    void updatePointsConfig(1:i32 companyId,2:i32 flag);
    common_struct.Response getPointsConfig(1:i32 companyId);
}

/*

*/
enum HistoryReferralPositionRecordType {
    ADD,
    UPDATE,
    DELETE
}
