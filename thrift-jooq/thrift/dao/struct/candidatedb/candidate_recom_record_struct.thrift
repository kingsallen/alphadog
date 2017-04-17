namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


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
