namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileLanguageDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string name,	//语言
	4: optional i8 level,	//掌握程度, dict_constant.parent_code:3108
	5: optional string createTime,	//创建时间
	6: optional string updateTime	//更新时间

}
