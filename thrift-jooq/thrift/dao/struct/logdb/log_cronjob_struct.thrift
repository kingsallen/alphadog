namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogCronjobDO {

	1: optional i32 id,	//null
	2: optional i32 cronjobId,	//null
	3: optional string startTime,	//开始时间
	4: optional string endTime,	//结束时间
	5: optional i32 result	//运行结果 1 失败, 0 成功

}
