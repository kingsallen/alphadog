namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileWorksDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string name,	//作品名称
	4: optional string url,	//作品网址
	5: optional string cover,	//作品封面
	6: optional string description,	//作品描述
	7: optional string createTime,	//创建时间
	8: optional string updateTime	//更新时间

}
