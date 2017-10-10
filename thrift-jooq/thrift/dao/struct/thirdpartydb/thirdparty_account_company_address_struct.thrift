namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyAccountCompanyAddressDO {

	1: optional i32 id,	//主键
	2: optional i32 accountId,	//第三方渠道账号的编号
	3: optional string city,	//上班城市
	4: optional string address,	//上班地址
	5: optional string createTime,	//创建时间
	6: optional string updateTime	//注册时间

}
