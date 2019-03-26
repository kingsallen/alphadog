namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictMarsMajorDO {

	1: optional i32 id,	//主键
	2: optional string name,	//专业名称
	3: optional i32 parent_id,	//所属上级的id
	4: optional i32 disable,	//状态 0有效 1无效
	5: optional string create_time	//创建时间

}
