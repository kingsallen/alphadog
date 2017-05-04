namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPositionExtDO {

	1: optional i32 pid,	//job_position.id
	2: optional i32 jobCustomId,	//job_custom.id
	3: optional string createTime,	//创建时间
	4: optional string updateTime,	//修改时间
	5: optional i32 jobOccupationId,	//jobdb.job_occupation.id
	6: optional string extra	//SuccessFactors对接

}
