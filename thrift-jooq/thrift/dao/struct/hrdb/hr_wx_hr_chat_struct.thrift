namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxHrChatDO {

	1: optional i32 id,	//ID
	2: optional i32 chatlistId,	//wx_hr_chat_list.id
	3: optional string content,	//聊天内容
	4: optional i32 pid,	//hr_position.id
	5: optional i8 speaker,	//状态，0：求职者，1：HR
	6: optional i8 status,	//状态，0：有效，1：无效
	7: optional string createTime,	//创建时间
	8: optional i8 origin,   // 来源 0 用户输入， 1 系统自动生成：欢迎语， 2 AI输入
	9: optional string msgType,     // 消息类型
	10:optional string picUrl,      //照片Url
	11:optional string btnContent   //控件内容
	12:optional string compoundContent   //聊天内容，表单、button等复合字段,保存为json格式
	13:optional string stats   //数据统计时使用的参数

}
