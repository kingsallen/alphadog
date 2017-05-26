namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigAdminnotificationMembersDO {

	1: optional i32 id,	//null
	2: optional string name,	//姓名
	3: optional string mobilephone,	//接收通知的手机
	4: optional string wechatopenid,	//接收通知的微信openid
	5: optional string email,	//接收通知的email
	6: optional i8 status,	//1 有效 0 无效
	7: optional string createTime	//null

}
