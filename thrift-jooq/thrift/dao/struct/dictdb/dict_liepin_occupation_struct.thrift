namespace java com.moseeker.thrift.gen.dao.struct
namespace py thrift_gen.gen.dao.struct.dictdb

struct DictLiepinOccupationDO {

	1: optional i32 id,	//null
	2: optional i32 code,	//仟寻生成的code，和仟寻的职位职能无关
	3: optional i32 parentId,	//上一级的职能id
	4: optional string otherCode,	//猎聘的code，可能是字符串
	5: optional i32 level,	//级层
	6: optional string createTime,	//null
	7: optional string updateTime,	//null
	8: optional i32 status,	//状态 0:有效，1:无效
	9: optional string name	//null

}
