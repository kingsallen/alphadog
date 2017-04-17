namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobApplicationDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//sys_wechat.id, 公众号ID
	3: optional i32 positionId,	//hr_position.id, 职位ID
	4: optional i32 recommenderId,	//user_wx_user.id,微信ID
	5: optional string submitTime,	//申请提交时间
	6: optional i32 statusId,	//hr_points_conf.id, 申请状态ID
	7: optional i32 lApplicationId,	//ATS的申请ID
	8: optional i32 reward,	//当前申请的积分记录
	9: optional i32 sourceId,	//job_source.id, 对应的ATS ID
	10: optional string createTime,	//time stamp when record created
	11: optional i32 applierId,	//sys_user.id, 用户ID
	12: optional i32 interviewId,	//app_interview.id, 面试ID
	13: optional string resumeId,	//mongodb collection application[id]
	14: optional i32 atsStatus,	//0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified 6:excess apply times
	15: optional string applierName,	//姓名或微信昵称
	16: optional i32 disable,	//是否有效，0：有效，1：无效
	17: optional i32 routine,	//申请来源 0:微信企业端 1:微信聚合端 10:pc端
	18: optional double isViewed,	//该申请是否被浏览，0：已浏览，1：未浏览
	19: optional double notSuitable,	//是否不合适，0：合适，1：不合适
	20: optional i32 companyId,	//company.id，公司表ID
	21: optional string updateTime,	//null
	22: optional i32 appTplId,	//申请状态,hr_award_config_template.id
	23: optional i8 proxy,	//是否是代理投递 0：正常数据，1：代理假投递
	24: optional i32 applyType,	//投递区分， 0：profile投递， 1：email投递
	25: optional i32 emailStatus,	//0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；8: 包含特殊字体;  9，提取邮件失败
	26: optional i32 viewCount,	//profile浏览次数
	27: optional i32 recommenderUserId	//userdb.user_user.id 推荐人编号

}
