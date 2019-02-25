namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


struct CandidateShareChainDO {

	1: optional i32 id,	//id
	2: optional i32 positionId,	//jobdb.job_position.id,相关职位 id
	3: optional i32 rootRecomUserId,	//userdb.user_user.id,最初转发人的 user_user.id
	4: optional i32 root2RecomUserId,	//userdb.user_user.id,最初转发人后一个的 user_user.id
	5: optional i32 recomUserId,	//userdb.user_user.id,转发人的 user_user.id
	6: optional i32 presenteeUserId,	//userdb.user_user.id,点击者 user_id
	7: optional i32 depth,	//第几层关系, 默认从 1 开始,当 presentee_id 是员工时,depth 为 0,表示该员工把链路截断了
	8: optional i32 parentId,	//candidatedb.candidate_share_chain.id,上一条 share_chain.id
	9: optional string clickTime,	//candidatedb.candidate_position_share_record.click_time,点击时间
	10: optional string createTime,	//创建时间
	11: optional i32 type,
	12: optional i8 clickFrom

}
