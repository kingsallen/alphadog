namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


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
