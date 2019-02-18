namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxNoticeMessageDO {

	1: optional i32 id,	//主key
	2: optional i32 wechatId,	//所属公众号
	3: optional i32 noticeId,	//sys_notice_message.id
	4: optional string first,	//消息模板first文案
	5: optional string remark,	//消息模板remark文案
	6: optional double status,	//是否开启, 1:开启, 0:关闭
	7: optional i32 disable	//是否有效  0:有效, 1:无效

}
