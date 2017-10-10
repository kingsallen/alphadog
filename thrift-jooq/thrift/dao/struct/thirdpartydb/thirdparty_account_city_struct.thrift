namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyAccountCityDO {

	1: optional i32 id,	//主键
	2: optional i32 accountId,	//第三方渠道账号的编号
	3: optional i32 code,	//可发布的城市code
	4: optional i8 jobtype,	//1 社招， 2 校招
	5: optional i32 remainNum,	//可发布数
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//注册时间

}
