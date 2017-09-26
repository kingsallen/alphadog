namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPcRecommendPositionItemDO {

	1: optional i32 id,	//null
	2: optional i32 moduleId,	//推荐列表id
	3: optional i32 positionId,	//职位id
	4: optional string createTime,	//创建时间
	5: optional string updateTime,	//更新时间
	6: optional i8 status	//删除状态:0不可用（未删除） 1：可用（已删除）

}
