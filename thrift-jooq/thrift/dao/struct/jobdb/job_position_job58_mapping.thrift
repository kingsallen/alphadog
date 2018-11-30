namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPositionJob58MappingDO {
    1: optional i32 id,         // id
	2: optional i32 positionId,	    // job_position.id
	3: optional string infoId,	// 向猎聘发送请求使用的id
	4: optional string url,	// 城市对应的仟寻code
	5: optional i8 state,     // 猎聘职位状态 0 下架 1 正常
	6: optional string createTime,     //
	7: optional string updateTime     //
}
