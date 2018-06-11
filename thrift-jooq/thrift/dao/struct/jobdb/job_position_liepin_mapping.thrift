namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPositionLiepinMappingDO {
    1: optional i32 id,         // id
	2: optional i32 jobId,	    // job_position.id
	3: optional i32 liepinJobId,	// 向猎聘发送请求使用的id
	4: optional i32 cityCode,	// 城市对应的仟寻code
	5: optional string jobTitle,	// 职位名称
	6: optional string errMsg,	// 如果同步失败，记录失败信息
	7: optional i8 state,     // 猎聘职位状态 0 下架 1 正常
	8: optional string createTime,     //
	9: optional string updateTime     //
}
