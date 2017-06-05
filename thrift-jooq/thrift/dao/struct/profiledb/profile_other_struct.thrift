namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileOtherDO {

	1: optional i32 profileId,	//profile.id
	2: optional string other,	//profile默认不显示字段
	3: optional string createTime,	//创建时间
	4: optional string updateTime	//更新时间

}
