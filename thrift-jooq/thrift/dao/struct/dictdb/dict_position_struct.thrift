namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictPositionDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional i32 parent,	//父编码
	4: optional i8 level	//字典level

}
