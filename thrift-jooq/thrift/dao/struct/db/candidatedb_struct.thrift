namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


struct CandidateCompanyDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id
	3: optional string updateTime,	//修改时间
	4: optional i32 wxuserId,	//user_wx_user.id 候选人绑定的user_wx_user.id，现在已经废弃。微信账号由C端账号替换，请参考user_id
	5: optional i32 status,	//候选人状态，0：删除，1：正常状态
	6: optional i8 isRecommend,	//是否推荐 false:未推荐，true:推荐
	7: optional string name,	//sys_user.name 姓名或微信昵称
	8: optional string email,	//sys_user.email 邮箱
	9: optional string mobile,	//sys_user.mobile 电话
	10: optional string nickname,	//wx_group_user.nickname 用户昵称
	11: optional string headimgurl,	//wx_group_user.headimgurl 用户头像
	12: optional i32 sysUserId,	//sys_user.id
	13: optional i32 clickFrom,	//来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage)
	14: optional i32 userId	//userdb.user_user.id C端账号编号，表示该候选人绑定的C端账号

}


struct CandidatePositionDO {

	1: optional i32 positionId,	//hr_position.id
	2: optional string updateTime,	//修改时间
	3: optional i32 wxuserId,	//user_wx_user.id，表示候选人代表的微信账号。已经废弃。微信账号由C端账号代替，请参考user_id
	4: optional i8 isInterested,	//是否感兴趣
	5: optional i32 candidateCompanyId,	//candidate_company.id
	6: optional i32 viewNumber,	//查看次数
	7: optional i8 sharedFromEmployee,	//null
	8: optional i32 userId	//userdb.user_user.id 候选人代表的C端用户

}


struct CandidatePositionShareRecordDO {

	1: optional i32 id,	//null
	2: optional double wechatId,	//所属公众号
	3: optional double positionId,	//position.id 分享职位ID
	4: optional double recomId,	//userdb.user_wx_user.id 分享用户微信ID。现在已经废弃，请参考sysuser_id字段
	5: optional double sysuserId,	//userdb.user_user.id 浏览用户userdb.user_user.id
	6: optional string viewerId,	//userdb.user_wx_viewer.id 浏览用户ID
	7: optional double viewerIp,	//浏览用户IP
	8: optional double source,	//来源0：企业后台1：聚合平台
	9: optional string createTime,	//创建时间
	10: optional string updateTime,	//修改时间
	11: optional double presenteeId,	//被动求职者,浏览者的微信ID，userdb.user_wx_user.id。现在已经废弃，请参考presentee_user_id
	12: optional double clickFrom,	//来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage) 3
	13: optional i32 presenteeUserId	//userdb.user_user.id 转发职位浏览者的C端账号编号

}


struct CandidateRecomRecordDO {

	1: optional i32 id,	//主key
	2: optional i32 positionId,	//推荐的职位ID, hr_position.id
	3: optional i32 appId,	//job_application.id, 被推荐者申请ID
	4: optional i32 presenteeId,	//userdb.user_wx_user.id, 被推荐者的微信ID。现在已经废弃，改由被推荐者的C端账号编号表示，请参考presentee_user_id
	5: optional string clickTime,	//职位点击时间
	6: optional i32 depth,	//第几层关系
	7: optional i32 recomId2,	//userdb.user_wx_usesr.id, 第2度人脉推荐人微信ID，用来标记谁的朋友。已经废弃，第2度人脉微信ID由第2度人脉C端账号编号替换，请参考repost_user_id
	8: optional i32 recomId,	//userdb.user_wx_user.id, 推荐者的微信ID。已经废弃，推荐者的微信ID被推荐者的C端账号编号替换，请参考post_user_id
	9: optional string realname,	//被推荐人真实姓名
	10: optional string company,	//被推荐者目前就职公司
	11: optional string position,	//被推荐者的职位
	12: optional string recomReason,	//推荐理由, 逗号分隔
	13: optional string recomTime,	//推荐时间
	14: optional i32 isRecom,	//推荐状态，0：推荐过，1：未推荐
	15: optional string createTime,	//创建时间
	16: optional string updateTime,	//更新时间
	17: optional string mobile,	//被推荐者的手机号
	18: optional i32 presenteeUserId,	//userdb.user_user.id 被推荐者的C端账号编号
	19: optional i32 repostUserId,	//userdb.user_user.id 第2度人脉微信ID由第2度人脉C端账号编号
	20: optional i32 postUserId	//userdb.user_user.id 推荐者的C端账号编号

}
