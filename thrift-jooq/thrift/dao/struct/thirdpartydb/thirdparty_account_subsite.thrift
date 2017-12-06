namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyAccountSubsiteDO {

	1: optional i32 id,	//主键
	2: optional i32 accountId,	//第三方渠道账号的编号
	3: optional string text,	//第三方账号对应的发布网站名称
	4: optional string site,	//第三方账号对应的发布网站
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//注册时间
}
