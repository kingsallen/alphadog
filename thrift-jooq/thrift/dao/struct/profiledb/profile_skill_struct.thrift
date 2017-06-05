namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileSkillDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string name,	//技能名称
	4: optional i8 level,	//掌握程度 0:未填写 1:了解, 2:掌握 3:熟练 4:精通
	5: optional i32 month,	//使用 单位(月)
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//更新时间

}
