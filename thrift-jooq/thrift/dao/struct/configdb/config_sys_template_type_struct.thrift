namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysTemplateTypeDO {

	1: optional i32 id,	//null
	2: optional string name,	//类型名称
	3: optional string createTime,	//创建时间
	4: optional string updateTime,	//修改时间
	5: optional double status	//是否有效 0：有效1：无效

}
