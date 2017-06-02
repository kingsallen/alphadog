namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictConstantDO {

	1: optional i32 id,	//主key
	2: optional i32 code,	//字典code
	3: optional string name,	//字典name
	4: optional i8 priority,	//优先级
	5: optional i32 parentCode,	//父级字典code
	6: optional string createTime,	//null
	7: optional string updateTime	//null

}
