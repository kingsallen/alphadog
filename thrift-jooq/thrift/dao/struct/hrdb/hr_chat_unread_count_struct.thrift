namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrChatUnreadCountDO {

	1: optional i32 roomId,	//聊天室编号
	2: optional i32 hrId,	//HR编号 userdb.user_hr_account
	3: optional i32 userId,	//用户编号 userdb.user_user.id
	4: optional string wxChatTime,	//sysuser最近一次聊天时间
	5: optional string hrChatTime,	//HR最近一次聊天时间
	6: optional i8 hrHaveUnreadMsg,	//HR是否有未读消息，0：没有，1有未读消息
	7: optional i8 userHaveUnreadMsg	//user是否有未读消息 ，0：没有，1有未读消息

}
