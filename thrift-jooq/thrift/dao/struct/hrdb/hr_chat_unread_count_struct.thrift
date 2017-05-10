namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrChatUnreadCountDO {

	1: optional i32 roomId,	//聊天室编号
	2: optional i32 hrId,	//HR编号 userdb.user_hr_account
	3: optional i32 userId,	//用户编号 userdb.user_user.id
	4: optional i32 hrUnreadCount,	//hr未读消息数量
	5: optional i32 userUnreadCount,	//员工未读消息数量
	6: optional i8 status,	//状态，0：有效，1：无效
	7: optional string wxChatTime,	//sysuser最近一次聊天时间
	8: optional string hrChatTime	//HR最近一次聊天时间

}
