namespace java com.moseeker.thrift.gen.referral.service

include "../../common/struct/common_struct.thrift"
include "../struct/referral_struct.thrift"

/*
*  员工服务接口
*/
service ReferralService {
    // 获取公司员工认证配置信息
    referral_struct.RedPackets getRedPackets(1: i32 userId, 2: i32 companyId, 3 : i32 pageNum, 4: i32 pageSize) throws (1: common_struct.BIZException e);
    // 获取奖金列表
    referral_struct.BonusList getBonus(1: i32 userId, 2 : i32 companyId, 3 : i32 pageNum, 4: i32 pageSize) throws (1: common_struct.BIZException e);
    //获取用户的推荐简历列表
    list<referral_struct.ReferralProfileTab> getReferralProfileList(1: i32 userId, 2: i32 companyId,3: i32 hrId) throws (1: common_struct.BIZException e);
    //修改红包活动
    void updateActivity(1: referral_struct.ActivityDTO activityDTO) throws (1: common_struct.BIZException e);
    //获取推荐填写的推荐信息
    list<referral_struct.ReferralReasonInfo> getReferralReason(1: i32 userId, 2: i32 companyId, 3: i32 hrId) throws (1: common_struct.BIZException e);
    //修改内推规则关键信息推荐配置
    void handerKeyInformationStatus(1: i32 companyId, 2: i32 keyInformation) throws (1: common_struct.BIZException e);
    //获取内推规则关键信息推荐配置
    i32 fetchKeyInformationStatus(1: i32 companyId) throws (1: common_struct.BIZException e);

    // 10分钟消息模板-人脉筛选，获取卡片数据
    string getRadarCards(1:referral_struct.ReferralCardInfo cardInfo) throws (1: common_struct.BIZException e);
    // 10分钟消息模板-邀请投递
    string inviteApplication(1:referral_struct.ReferralInviteInfo inviteInfo) throws (1: common_struct.BIZException e);
    // 10分钟消息模板-我不熟悉
    string ignoreCurrentViewer(1:referral_struct.ReferralInviteInfo ignoreInfo) throws (1: common_struct.BIZException e);
    // 点击人脉连连看按钮/点击分享的人脉连连看页面
    string connectRadar(1:referral_struct.ConnectRadarInfo radarInfo) throws (1: common_struct.BIZException e);
    //候选人求推荐记录保存
    void addUserSeekRecommend(1:i32 companyId,2:i32 userId, 3:i32 postUserId, 4:i32 positionId, 5: i32 origin)throws (1: common_struct.BIZException e);
    //员工推荐评价
    void employeeReferralReason(1: i32 companyId, 2: i32 postUserId, 3:i32 positionId, 4:i32 referralId, 5: list<string> referralReasons, 6: i8 relationship, 7: string recomReasonText) throws (1: common_struct.BIZException e);
    //候选人联系内推模板消息发送之后员工点击之后展示内容
    referral_struct.ContactPushInfo fetchSeekRecommend(1:i32 companyId, 2: i32 referralId, 3:i32 postUserId) throws (1: common_struct.BIZException e);
    // 候选人打开职位连接判断推荐人是否是员工
    string checkEmployee(1: referral_struct.CheckEmployeeInfo checkInfo) throws (1: common_struct.BIZException e);
     // 10分钟消息模板-人脉筛选，存储十分钟内的卡片数据
    void saveTenMinuteCandidateShareChain(1:referral_struct.ReferralCardInfo cardInfo) throws (1: common_struct.BIZException e);
    // 获取某个候选人的推荐进度
    string getProgressByOne(1:referral_struct.ReferralProgressQueryInfo progressQuery) throws (1: common_struct.BIZException e);
    // 根据条件批量查询候选人推荐进度
    string getProgressBatch(1:referral_struct.ReferralProgressInfo progressInfo) throws (1: common_struct.BIZException e);
    // 推荐进度搜索框输入名字显示该员工推荐申请中的申请人名字
    string progressQueryKeyword(1:referral_struct.ReferralProgressInfo progressInfo) throws (1: common_struct.BIZException e);
    //员工推荐评价
    void employeeReferralRecomEvaluation(1:i32 companyId, 2: i32 postUserId, 3:i32 positionId, 4:i32 presenteeId, 5: list<string> referralReasons, 6: i8 relationship, 7: string recomReasonText) throws (1: common_struct.BIZException e);
    // 检查职位详情页点击人是否对该职位发起过帮我内推
    i32 checkSeekReferral(1: i32 userId, 2:i32 presenteeId, 3:i32 positionId, 4: i32 companyId, 5: i32 parentChainId) throws (1: common_struct.BIZException e);
    // 邀请投递不可触达候选人时，掉此接口将候选人标记为已处理
    void handleCandidateState(1:referral_struct.ReferralStateInfo stateInfo) throws (1: common_struct.BIZException e);
    //人员上传文件时，调用此接口返回上传记录
    referral_struct.ReferralUploadFiles uploadFiles(1:string sceneId , 2:string fileName, 3 :binary fileData) throws (1:common_struct.BIZException e);
    //上传文件分页列表
    list<referral_struct.ReferralUploadFiles> getUploadFiles(1:string unionId,2:i32 pageSize,3:i32 pageNo ) throws (1:common_struct.BIZException e);
    //下载文件
    string downLoadFiles(1:string sceneId) throws (1:common_struct.BIZException e);
    //解析上传文件，返回结果
    referral_struct.ReferralUploadFiles referralResumeInfo(1:string sceneId) throws (1:common_struct.BIZException e);
}
