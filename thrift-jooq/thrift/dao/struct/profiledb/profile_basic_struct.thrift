namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileBasicDO {

	1: optional i32 profileId,	//profile.id
	2: optional string name,	//姓名
	3: optional i8 gender,	//性别, dict_constant.parent_code:3109
	4: optional i32 nationalityCode,	//国籍code，国家字典表, dict_country.id
	5: optional string nationalityName,	//国籍名称
	6: optional i32 cityCode,	//现居住地, 城市字典
	7: optional string cityName,	//现居住地, 城市名称
	8: optional string birth,	//出生年月 yyyy-mm-dd
	9: optional string weixin,	//微信号
	10: optional string qq,	//QQ号
	11: optional string motto,	//座右铭
	12: optional string selfIntroduction,	//自我介绍
	13: optional string createTime,	//创建时间
	14: optional string updateTime	//更新时间

}
