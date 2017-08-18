namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserAliUserDO {

	1: optional i32 id,	//编号
	2: optional i32 userId,	//userdb.user_user.id, C端用户ID
	3: optional string uid,	//阿里账号id
	4: optional string createTime,	//创建时间
	5: optional string updateTime	//修改时间

}
