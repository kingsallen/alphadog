namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxHrChatListDO {

	1: optional i32 id,	//ID
	2: optional i32 sysuserId,	//sysuser.id
	3: optional i32 hraccountId,	//hr_account.id
	4: optional i8 status,	//状态，0：有效，1：无效
	5: optional string createTime,	//创建时间
	6: optional string wxChatTime,	//sysuser最近一次聊天时间
	7: optional string hrChatTime,	//HR最近一次聊天时间
	8: optional string updateTime	//更新时间

}
