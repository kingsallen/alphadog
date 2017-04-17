namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileIntentionIndustryDO {

	1: optional i32 id,	//主key
	2: optional i32 profileIntentionId,	//profile_intention.id
	3: optional i32 industryCode,	//行业字典编码
	4: optional string industryName	//行业名称

}
