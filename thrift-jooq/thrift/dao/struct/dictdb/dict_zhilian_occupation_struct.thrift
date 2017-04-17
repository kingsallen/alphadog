namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictZhilianOccupationDO {

	1: optional i32 code,	//职能id
	2: optional i32 parentId,	//父Id，上一级职能的ID
	3: optional string name,	//职能名称
	4: optional i32 codeOther,	//null
	5: optional i32 level,	//职能级别 1是一级2是
	6: optional i32 status,	//只能状态 0 是无效 1是有效
	7: optional string createtime	//创建时间

}
