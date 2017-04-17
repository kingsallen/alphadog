namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserBdUserDO {

	1: optional i32 id,	//null
	2: optional i32 uid,	//百度帐号 id
	3: optional i32 userId,	//user_user.id, C端用户ID
	4: optional string username,	//登录用户名
	5: optional i32 sex,	//用户性别 2:未知  0:女性 1:男性
	6: optional string headimgurl,	//用户头像
	7: optional string createTime,	//创建时间
	8: optional string updateTime	//null

}
