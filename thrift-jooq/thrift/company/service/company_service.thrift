# file: company_service.thrift


include "../../common/struct/common_struct.thrift"
include "../struct/company_struct.thrift"
include "../struct/talentpool_struct.thrift"
include "../../dao/struct/hrdb_struct.thrift"
include "../../dao/struct/hrdb/hr_company_conf_struct.thrift"
include "../../employee/struct/employee_struct.thrift"

namespace java com.moseeker.thrift.gen.company.service

service CompanyServices {
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getAllCompanies(1:common_struct.CommonQuery query);
    common_struct.Response add(1:company_struct.Hrcompany company);
    common_struct.Response getWechat(1:i64 companyId, 2:i64 wechatId);
    common_struct.Response getPcBanner(1:i32 page, 2:i32 pageSize);
    // 更新公司员工认证配置
    bool updateEmployeeBindConf(1:i32 companyId,2:i32 authMode,3:string emailSuffix,4:string custom, 5:string customHint, 6:string questions,7:string filePath,8:string fileName,9:i32 type,10:i32 hraccountId) throws (1: common_struct.BIZException e);
    // 设置企业微信员工认证配置
    bool setWorkWechatEmployeeBindConf(1:i32 companyId,2:i32 hraccountId,3:string corpId, 4:string secret) throws (1: common_struct.BIZException e);
    // 重新获取企业微信员工认证配置的access token
    bool updateWorkWeChatConfToken(1:i32 companyId) throws (1: common_struct.BIZException e);
    company_struct.WorkWxCertConf getWorkWechatEmployeeBindConf(1:i32 companyId) throws (1: common_struct.BIZException e);

    // 获取公司员工认证配置
    company_struct.CompanyCertConf getHrEmployeeCertConf(1:i32 companyId,2:i32 type, 3:i32 hraccountId) throws (1: common_struct.BIZException e);
    // 获取公司积分配置信息
    list<employee_struct.RewardConfig> getCompanyRewardConf(1: i32 companyId) throws (1: common_struct.BIZException e);
    // 更新公司积分配置信息
    common_struct.Response updateCompanyRewardConf(1:i32 companyId,2:list<employee_struct.RewardConfig> rewardConfigs) throws (1: common_struct.BIZException e)
    list<company_struct.CompanyForVerifyEmployee> getGroupCompanies(1: i32 companyId) throws (1: common_struct.BIZException e)
    bool isGroupCompanies(1: i32 companyId) throws (1: common_struct.BIZException e)
    // 获取公司部门与职能信息(员工认证补填字段显示)
    company_struct.CompanyOptions getCompanyOptions(1:i32 companyId) throws (1: common_struct.BIZException e)
    // 添加公司员工认证模板数据
    common_struct.Response addImporterMonitor(1:i32 comanyId, 2:i32 hraccountId, 3:i32 type, 4:string file, 5:i32 status, 6:string message, 7:string fileName) throws (1: common_struct.BIZException e)
    // 查找公司认证模板数据（取最新一条数据）
    hrdb_struct.HrImporterMonitorDO getImporterMonitor(1:i32 comanyId, 2:i32 hraccountId, 3:i32 type) throws (1: common_struct.BIZException e)
    // 公司员工认证开关
    common_struct.Response bindingSwitch(1:i32 companyId, 2:i32 disable) throws (1: common_struct.BIZException e)
    //获取公司详细请
    common_struct.Response companyDetails(1:i32 companyId) throws (1: common_struct.BIZException e)
    //  公司员工认证后补填字段配置信息列表
    list<company_struct.HrEmployeeCustomFieldsVO> getHrEmployeeCustomFields(1:i32 companyId) throws (1: common_struct.BIZException e)
    //获取pc端团队列表的企业信息
    common_struct.Response companyMessage(1:i32 companyId) throws (1: common_struct.BIZException e)
    common_struct.Response companyPaidOrFortune() throws (1: common_struct.BIZException e)
    common_struct.Response getTalentPoolStatus(1:i32 hrId,2:i32 companyId)throws (1: common_struct.BIZException e)

    hr_company_conf_struct.HrCompanyConfDO getCompanyConfById(1:i32 companyId)throws (1: common_struct.BIZException e)
    common_struct.Response updateHrCompanyConf(1:company_struct.HrCompanyConf hrCompanyConf)throws (1: common_struct.BIZException e)
    common_struct.Response addHrAccountAndCompany(1:string companyName, 2: string mobile, 3:i32 wxuserId, 4:string remoteIp, 5:i32 source, 6:i32 hr_source) throws (1: common_struct.BIZException e)

    common_struct.Response getFeatureById(1:i32 id) throws (1: common_struct.BIZException e)
    common_struct.Response getFeatureByCompanyId(1:i32 companyId) throws (1: common_struct.BIZException e)
    common_struct.Response updateCompanyFeature(1:company_struct.HrCompanyFeatureDO data) throws (1: common_struct.BIZException e)
    common_struct.Response updateCompanyFeatures(1:list<company_struct.HrCompanyFeatureDO> dataList) throws (1: common_struct.BIZException e)
    common_struct.Response addCompanyFeature(1:company_struct.HrCompanyFeatureDO data) throws (1: common_struct.BIZException e)
    common_struct.Response addCompanyFeatures(1:list<company_struct.HrCompanyFeatureDO> dataList) throws (1: common_struct.BIZException e)
    common_struct.Response getCompanyFeatureIdList(1:list<i32> dataList) throws (1: common_struct.BIZException e)
    common_struct.Response getWechatBySignature(1:string signature, 2:i32 companyId) throws (1: common_struct.BIZException e)
    common_struct.Response updateHrCompanyConfStatus(1:i32 status, 2:i32 companyId) throws (1: common_struct.BIZException e)
    common_struct.Response findSubAccountNum(1:i32 companyId) throws (1: common_struct.BIZException e)
    common_struct.Response updateWechatThenm(1:i32 status, 2:i32 companyId) throws (1: common_struct.BIZException e),
    common_struct.Response getCompanyWechatList(1:i32 companyId)throws (1: common_struct.BIZException e)
    //校验申请人在某一家公司下是否触发GDPR隐私条款保护
    list<company_struct.GDPRProtectedInfo> validateGDPR(1: list<i32> userIds, 2: i32 companyId) throws (1: common_struct.BIZException e)
    //查看指定公司是否开启GDPR隐私保护条款
    bool fetchGDPRSwitch(1: i32 companyId) throws (1: common_struct.BIZException e)
    //查看指定的HR 所在的公司是否开启GDPR隐私保护条款
    bool fetchGDPRSwitchByHR(1: i32 hrId) throws (1: common_struct.BIZException e)

    hr_company_conf_struct.HrCompanyMobotConfDO getMobotConf(1: i32 companyId) throws (1: common_struct.BIZException e)
    hr_company_conf_struct.HrCompanyMobotConfDO updateMobotConf(1: hr_company_conf_struct.HrCompanyMobotConfDO mobotConf) throws (1: common_struct.BIZException e)

    //获取当前公司的开关权限
     list<company_struct.CompanySwitchVO> switchCheck(1: i32 companyId, 2: list<string> moduleNames) throws (1: common_struct.BIZException e)
     company_struct.CompanySwitchVO companySwitch(1: i32 companyId, 2: string moduleNames) throws (1: common_struct.BIZException e)
     company_struct.CompanySwitchVO switchPost(1:company_struct.CompanySwitchVO data) throws (1: common_struct.BIZException e)
     company_struct.CompanySwitchVO switchPatch(1:company_struct.CompanySwitchVO data) throws (1: common_struct.BIZException e)
}

service HrTeamServices {
    list<hrdb_struct.HrTeamDO> getHrTeams(1:common_struct.CommonQuery query);
    common_struct.Response teamListInfo(1:i32 companyId,2:i32 page,3:i32 pageSize) throws (1: common_struct.BIZException e)
    common_struct.Response teamDeatils(1:i32 companyId,2:i32 teamId) throws (1: common_struct.BIZException e)
}

service TalentpoolServices {
    common_struct.Response upsertTalentPoolApp(1:i32 hrId,2:i32 companyId,3: i32 type)throws (1: common_struct.BIZException e)

    common_struct.Response getTalentAllComment(1:i32 hr_id,2:i32 company_id,3:i32 user_id,4: i32 page_number,5:i32 page_size)throws(1: common_struct.BIZException e)

    common_struct.Response getHrTag(1:i32 hr_id,2:i32 company_id,3:i32 page_number,4:i32 page_size) throws (1: common_struct.BIZException e)

    common_struct.Response batchAddTalent(1:i32 hr_id,2:list<i32> user_ids,3:i32 company_id,4:i32 isGdpr) throws (1: common_struct.BIZException e)

    common_struct.Response batchCancelTalent(1:i32 hr_id,2:list<i32> user_ids,3:i32 company_id,4:i32 isGdpr) throws (1: common_struct.BIZException e)

    common_struct.Response hrAddTag(1:i32 hr_id,2:i32 company_id,3:string name) throws (1: common_struct.BIZException e)

    common_struct.Response hrDelTag(1:i32 hr_id,2:i32 company_id,3:i32 tag_id) throws (1: common_struct.BIZException e)

    common_struct.Response hrUpdateTag(1:i32 hr_id,2:i32 company_id,3:i32 tag_id,4:string name) throws (1: common_struct.BIZException e)

    common_struct.Response batchCancleTalentTag(1:i32 hr_id,2:list<i32> user_ids,3:list<i32> tag_ids,4:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response batchAddTalentTag(1:i32 hr_id,2:list<i32> user_ids,3:list<i32> tag_ids,4:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response hrAddComment(1:i32 hr_id,2:i32 company_id,3:i32 user_id,4:string content) throws (1: common_struct.BIZException e)

    common_struct.Response hrDelComment(1:i32 hr_id,2:i32 company_id,3:i32 comment_id) throws (1: common_struct.BIZException e)

    common_struct.Response batchAddPublicTalent(1:i32 hr_id,2:i32 company_id,3:list<i32> user_ids,4:i32 isGdpr) throws (1: common_struct.BIZException e)

    common_struct.Response batchCancelPublicTalent(1:i32 hr_id,2:i32 company_id,3:list<i32> user_ids) throws (1: common_struct.BIZException e)

    common_struct.Response getCompanyPulicTalent(1:i32 hr_id,2:i32 company_id,3:i32 page_number,4: i32 page_size)throws(1: common_struct.BIZException e)

    common_struct.Response batchNewAddTalentTag(1:i32 hr_id,2:list<i32> user_ids,3:list<i32> tag_ids,4:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response getTalentStat(1:i32 hr_id,2:i32 company_id,3:i32 type) throws (1: common_struct.BIZException e)

    common_struct.Response getCompanyUserPublic(1:i32 hr_id,2:i32 company_id,3:i32 user_id) throws (1: common_struct.BIZException e)

    common_struct.Response getCompanyTalent(1:i32 hr_id,2:i32 company_id,3:i32 user_id) throws (1: common_struct.BIZException e)

    common_struct.Response getHrUserTag(1:i32 hr_id,2:i32 company_id,3:i32 user_id) throws (1: common_struct.BIZException e)

    common_struct.Response getUserOrigin(1:i32 hr_id,2:i32 company_id,3:i32 user_id) throws (1: common_struct.BIZException e)

    common_struct.Response getTalentAndPublicHr(1:i32 hr_id,2:i32 company_id,3:list<i32> user_ids) throws (1: common_struct.BIZException e)

    common_struct.Response getPositionOrCompanyPast(1:i32 company_id,2:i32 type,3: i32 flag) throws (1: common_struct.BIZException e)

    common_struct.Response addPositionOrCompanyPast(1:i32 company_id,2:i32 type,3: i32 flag,4: string name) throws (1: common_struct.BIZException e)

    common_struct.Response getTalentTagList(1:i32 hr_id,2:i32 company_id,3:i32 page_number, 4:i32 page_size) throws (1: common_struct.BIZException e)

    common_struct.Response getProfileFilterList(1:i32 hr_id,2:i32 company_id,3:i32 page_number, 4:i32 page_size) throws (1: common_struct.BIZException e)

    common_struct.Response handerProfileFilterByIds(1:i32 hr_id,2:i32 company_id,3:i32 disable, 4:list<i32> filter_ids) throws (1: common_struct.BIZException e)

    common_struct.Response getProfileFilterInfo(1:i32 hr_id,2:i32 company_id,3:i32 filter_id) throws (1: common_struct.BIZException e)

    common_struct.Response addProfileFilter(1:talentpool_struct.TalentpoolCompanyTagDO companyTagDO, 2:list<talentpool_struct.ActionForm> actionForm,3:list<i32> positionIdList, 4:i32 hr_id, 5:i32 position_total) throws (1: common_struct.BIZException e)

    common_struct.Response updateProfileFilter(1:talentpool_struct.TalentpoolCompanyTagDO companyTagDO, 2:list<talentpool_struct.ActionForm> actionForm,3:list<i32> positionIdList, 4:i32 hr_id, 5:i32 position_total) throws (1: common_struct.BIZException e)

    void  handlerCompanyTagAndProfile(1:set<i32> userid_list,2:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response getTalentCountByPositionFilter(1:i32 hr_id, 2:i32 company_id, 3:i32 position_id) throws (1: common_struct.BIZException e)

    common_struct.Response getEmailBalance(1:i32 hr_id,2:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response getEmailTemplateList(1:i32 hr_id,2:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response getEmailInfo(1:i32 hr_id,2:i32 company_id,3:i32 type) throws (1: common_struct.BIZException e)

    common_struct.Response updateCompanyEmailInfo(1:i32 hr_id,2:i32 company_id,3:i32 type, 4:i32 disable, 5:string context,6:string inscribe) throws (1: common_struct.BIZException e)

    talentpool_struct.EmailAccountForm fetchEmailAccounts(1: i32 companyId, 2: string companyName, 3: i32 pageNumber, 4: i32 pageSize) throws (1: common_struct.BIZException e)

    talentpool_struct.EmailAccountConsumptionForm fetchEmailAccountConsumption(1: i32 companyId, 2: i8 type, 3: i32 pageNumber, 4: i32 pageSize, 5: string startDate, 6: string endDate) throws (1: common_struct.BIZException e)

    i32 rechargeEmailAccount(1: i32 companyId, 2: i32 lost) throws (1: common_struct.BIZException e)

    void updateEmailAccountRechargeValue(1:i32 id, 2: i32 lost) throws (1: common_struct.BIZException e)

    void  addAllTalent(1:i32 hrId,2:map<string,string> params,3:i32 companyId,4:i32 isGdpr) throws (1: common_struct.BIZException e)

    void  addAllTalentTag(1:map<string,string> params,2:list<i32> tagList,3:i32 companyId,4:i32 hrId) throws (1: common_struct.BIZException e)

    void  addAllTalentPublic(1:i32 hrId,2:map<string,string> params,3:i32 companyId,4:i32 isGdpr) throws (1: common_struct.BIZException e)

    void  addAllTalentPrivate(1:i32 hrId,2:map<string,string> params,3:i32 companyId) throws (1: common_struct.BIZException e)

    void  cancleAllTalent(1:i32 hrId,2:map<string,string> params,3:i32 companyId,4:i32 isGdpr) throws (1: common_struct.BIZException e)

    common_struct.Response updateCompanyEmailBalance(1:i32 company_id,2:i32 balance) throws (1: common_struct.BIZException e)

    common_struct.Response sendInviteEmail(1: map<string,string> params ,2: list<i32> userIdList,3: list<i32> positionIdList,4: i32 companyId,5: i32 hrId,6: i32 flag,7: i32 positionFlag) throws (1: common_struct.BIZException e)

    common_struct.Response sendResumeEmail(1: map<string,string> params ,2: list<i32> userIdList,3: list<i32> idList,4: i32 companyId,5: i32 hrId,6: i32 flag,7:list<string> emailList) throws (1: common_struct.BIZException e)

    common_struct.Response sendPositionInviteEmail( 1:i32 hrId,2: i32 positionId,3: i32 companyId) throws (1: common_struct.BIZException e)

    common_struct.Response getCompanyCommentByUserIdList( 1:i32 companyId,2: list<i32> userIdList) throws (1: common_struct.BIZException e)

}

service TalentpoolNewServices {

    common_struct.Response getCompanyTagList(1:i32 hr_id,2:i32 company_id,3:i32 page_number, 4:i32 page_size) throws (1: common_struct.BIZException e)

    common_struct.Response deleteCompanyTagByIds(1:i32 hr_id,2:i32 company_id,3:list<i32> company_tag_ids) throws (1: common_struct.BIZException e)

    common_struct.Response getCompanyIdInfo(1:i32 hr_id,2:i32 company_id,3:i32 company_tag_id) throws (1: common_struct.BIZException e)

    common_struct.Response addCompanyTag(1:talentpool_struct.TalentpoolCompanyTagDO companyTagDO, 2:i32 hr_id) throws (1: common_struct.BIZException e)

    common_struct.Response updateCompanyTag(1:talentpool_struct.TalentpoolCompanyTagDO companyTagDO, 2:i32 hr_id) throws (1: common_struct.BIZException e)

    common_struct.Response addProfileContent(1:i32 userId,2:i32 accountId, 3:string content)throws (1: common_struct.BIZException e)

    common_struct.Response deleteHrAutoMaticTagByIds(1:i32 hr_id,2:i32 company_id,3:list<i32> tag_ids) throws (1: common_struct.BIZException e)

    common_struct.Response addHrAutoMaticTag(1:talentpool_struct.TalentpoolHrAutomaticTagDO hrAutoTagDO, 2:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response updateHrAutoMaticTag(1:talentpool_struct.TalentpoolHrAutomaticTagDO hrAutoTagDO, 2:i32 company_id) throws (1: common_struct.BIZException e)

    common_struct.Response getHrAutoMaticTagList(1:i32 hr_id,2:i32 company_id,3:i32 page_number, 4:i32 page_size) throws (1: common_struct.BIZException e)

    common_struct.Response getHrAutoMaticTagSingle(1:i32 hr_id,2:i32 company_id,3:i32 id) throws (1: common_struct.BIZException e)
}
