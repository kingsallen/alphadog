namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxHrChatDO {

	1: optional i32 id,	//ID
	2: optional i32 chatlistId,	//wx_hr_chat_list.id
	3: optional string content,	//聊天内容
	4: optional i32 pid,	//hr_position.id
	5: optional i8 speaker,	//状态，0：求职者，1：HR
	6: optional i8 status,	//状态，0：有效，1：无效
	7: optional string createTime	//创建时间

}
