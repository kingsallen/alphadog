namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


struct CandidateSuggestPositionDO {

	1: optional i32 id,	//null
	2: optional i32 hraccountId,	//做职位推荐的账号编号 hr_account.id
	3: optional i32 positionId,	//hr_position.id
	4: optional i32 wxuserId,	//user_wx_user.id 候选人关联的微信账号。已经废弃，微信账号由C端账号代替，请参考user_id
	5: optional string createTime,	//创建时间
	6: optional string updateTime,	//修改时间
	7: optional i32 disable,	//是否生效 0：生效 1：不生效
	8: optional i32 userId	//userdb.user_user.id C端账号编号。

}
