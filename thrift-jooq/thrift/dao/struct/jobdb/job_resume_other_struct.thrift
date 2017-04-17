namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobResumeOtherDO {

	1: optional i32 appId,	//job_application.id
	2: optional string other,	//自定义字段
	3: optional string createTime,	//创建时间
	4: optional string updateTime	//更新时间

}
