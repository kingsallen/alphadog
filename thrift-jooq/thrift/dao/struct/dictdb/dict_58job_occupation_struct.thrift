namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct Dict58jobOccupationDO {

	1: optional i32 code,	//职能id
	2: optional string parentId,	//父Id，上一级职能的ID
	3: optional string name,	//职能名称
	4: optional string codeOther,	//第三方职能id
	5: optional i16 level,	//职能级别 1是一级2是二级依次类推
	6: optional i16 status,	//只能状态 0 是有效 1是无效

}
