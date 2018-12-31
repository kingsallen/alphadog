 namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
 namespace py thrift_gen.gen.dao.struct.candidatedb


 struct CandidateTemplateShareChainDO {

 	1: optional i32 id,	//id
 	2: optional i32 positionId,	//jobdb.job_position.id,相关职位 id
 	3: optional i32 rootUserId,	//userdb.user_user.id,最初转发人的 user_user.id
 	4: optional i32 root2UserId,	//userdb.user_user.id,最初转发人后一个的 user_user.id
 	5: optional i32 recomUserId,	//userdb.user_user.id,转发人的 user_user.id
 	6: optional i32 presenteeUserId,	//userdb.user_user.id,点击者 user_id
 	7: optional i32 depth,	//第几层关系, 默认从 1 开始,当 presentee_id 是员工时,depth 为 0,表示该员工把链路截断了
 	8: optional i32 parentId,	//candidatedb.candidate_share_chain.id,上一条 share_chain.id
 	9: optional i32 seekReferral,	// 是否求推荐
 	10: optional i32 type,	// 标记是否已经邀请投递或不熟悉
    11: optional i64 sendTime       // 十分钟消息模板发送时间

 }
