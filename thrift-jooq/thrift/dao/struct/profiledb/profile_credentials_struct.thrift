namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileCredentialsDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string name,	//证书名称
	4: optional string organization,	//证书机构
	5: optional string code,	//证书编码
	6: optional string url,	//认证链接
	7: optional string getDate,	//获得时间
	8: optional string score,	//成绩
	9: optional string createTime,	//创建时间
	10: optional string updateTime	//更新时间

}
