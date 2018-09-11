namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxHrChatListDO {

	1: optional i32 id,	//ID
	2: optional i32 sysuserId,	//sysuser.id
	3: optional i32 hraccountId,	//hr_account.id
	4: optional string createTime,	//创建时间
	5: optional string wxChatTime,	//sysuser最近一次聊天时间
	6: optional string hrChatTime,	//HR最近一次聊天时间
	7: optional string updateTime,	//更新时间
	8: optional i32 hrUnreadCount,	//hr未读消息数量
	9: optional i32 userUnreadCount,	//C端用户未读消息数量
	10: optional i8 welcomeStatus   //是否发送欢迎语

}
