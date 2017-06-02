namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobApplicationConfDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//company.id, 部门ID
	3: optional i32 appnotice,	//简历投递反馈通知
	4: optional i32 appnoticeTpl,	//简历投递通知使用模板
	5: optional string appReply,	//申请提交成功回复信息
	6: optional i32 emailAppnotice,	//0：需要，1：不需要
	7: optional string emailAppreply,	//申请邮箱回复内容html格式
	8: optional i32 smsAppnoticeId,	//短信通知ID
	9: optional string smsAppnoticePrefix,	//短信通知职位前缀
	10: optional string smsAppnoticeSignature,	//短信通知签名
	11: optional i32 smsDelay,	//短信通知延迟 单位： min
	12: optional i32 forwardClickReward,	//转发点击奖励通知0:需要1:不需要
	13: optional i32 forwardClickRewardTpl,	//转发点击奖励通知模板
	14: optional i32 forwardAppReward,	//转发申请奖励通知0:需要1:不需要
	15: optional i32 forwardAppRewardTpl,	//转发申请奖励通知模板
	16: optional i8 emailResumeConf,	//0:允许使用email简历进行投递；1:不允许使用email简历投递
	17: optional string createTime,	//null
	18: optional string updateTime	//null

}
