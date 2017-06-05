namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileIntentionCityDO {

	1: optional i32 id,	//主key
	2: optional i32 profileIntentionId,	//profile_intention.id
	3: optional i32 cityCode,	//期望城市字典编码
	4: optional string cityName	//期望城市名称

}
