namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPositionJob58MappingDO {
    1: optional i32 id,         // id
	2: optional i32 positionId,	    // job_position.id
	3: optional string infoId,	// 58返回的职位id
	4: optional string url,	// 58返回的url
	5: optional i8 state,     // 58职位上下架状态
	6: optional string openId,     // 58用户的唯一标识
	7: optional string createTime,     //
	8: optional string updateTime     //
}
