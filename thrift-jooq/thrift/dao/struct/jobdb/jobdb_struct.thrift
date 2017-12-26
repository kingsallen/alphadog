namespace java com.moseeker.thrift.gen.dao.struct


struct JobPositionCcmailDO {

	1: optional i32 id,	//主键ID
	2: optional i32 position_id,	//job_position.id
	3: optional string to_email,	//抄送邮箱
	4: optional i32 hr_id,	//操作人User_hr_account.id
	5: optional string create_time,	//创建时间
	6: optional string update_time	//更新时间

}


struct JobApplicationDO {

	1: optional i32 id,	//null
	2: optional i32 wechat_id,	//sys_wechat.id, 公众号ID
	3: optional i32 position_id,	//hr_position.id, 职位ID
	4: optional i32 recommender_id,	//user_wx_user.id, 微信ID。现在已经废弃，推荐者信息请参考recommend_user_id
	5: optional string submit_time,	//申请提交时间
	6: optional i32 status_id,	//hr_points_conf.id, 申请状态ID
	7: optional i32 l_application_id,	//ATS的申请ID
	8: optional i32 reward,	//当前申请的积分记录
	9: optional i32 source_id,	//job_source.id, 对应的ATS ID
	10: optional string _create_time,	//time stamp when record created
	11: optional i32 applier_id,	//sys_user.id, 用户ID
	12: optional i32 interview_id,	//app_interview.id, 面试ID
	13: optional string resume_id,	//mongodb collection application[id]
	14: optional i32 ats_status,	//0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified 6:excess apply times
	15: optional string applier_name,	//姓名或微信昵称
	16: optional i32 disable,	//是否有效，0：有效，1：无效
	17: optional i32 routine,	//申请来源 0:微信企业端 1:微信聚合端 10:pc端
	18: optional double is_viewed,	//该申请是否被浏览，0：已浏览，1：未浏览
	19: optional double not_suitable,	//是否不合适，0：合适，1：不合适
	20: optional i32 company_id,	//company.id，公司表ID
	21: optional string update_time,	//null
	22: optional i32 app_tpl_id,	//申请状态,hr_award_config_template.id
	23: optional i8 proxy,	//是否是代理投递 0：正常数据，1：代理假投递
	24: optional i32 apply_type,	//投递区分， 0：profile投递， 1：email投递
	25: optional i32 email_status,	//0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；9，提取邮件失败
	26: optional i32 view_count,	//profile浏览次数
	27: optional i32 recommender_user_id,	//userdb.user_user.id 推荐人编号
	28: optional i32 origin	//申请来源 1 PC;2 企业号；4 聚合号； 8 51； 16 智联； 32 猎聘； 64 支付宝； 128 简历抽取； 256 员工代投 ; 512:程序导入（和黄经历导入）; 1024: email 申请; 2048:最佳东方;4096:一览英才

}


struct JobPositionCcmailDO {

	1: optional i32 id,	//主键ID
	2: optional i32 position_id,	//job_position.id
	3: optional string to_email,	//抄送邮箱
	4: optional i32 hr_id,	//操作人User_hr_account.id
	5: optional string create_time,	//创建时间
	6: optional string update_time	//更新时间

}
