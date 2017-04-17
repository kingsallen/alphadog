namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserThirdpartyUserDO {

	1: optional i32 id,	//主key
	2: optional i32 userId,	//user_user.id, C端用户ID
	3: optional i32 sourceId,	//第三方平台ID，0：SF，1：Taleo，2：workday,3:51job,4:zhaopin,5,liepin
	4: optional string username,	//用户名，比如手机号、邮箱等
	5: optional string password,	//密码, AES 128位加密
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//null

}
