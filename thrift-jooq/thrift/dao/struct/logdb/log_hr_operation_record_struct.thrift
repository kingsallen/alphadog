namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogHrOperationRecordDO {

	1: optional i32 id,	//null
	2: optional i32 type,	//0:无效1：hr操作职位发布人
	3: optional i32 hraccountId,	//user_hr_account.id
	4: optional string description,	//记录描述
	5: optional string createTime	//null

}
