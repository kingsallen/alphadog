namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb

struct CandidateTemplateShareChainDO {

	1: optional i32 id,	//null
	2: optional i32 positionId,	//
	3: optional i32 rootUserId,
	4: optional i32 recomUserId,	//
	5: optional i32 presenteeUserId,	//创建时间
	6: optional i64 sendTime,
	7: optional i32 parentId,
	8: optional i8 depth
	9: optional i8 type

}
