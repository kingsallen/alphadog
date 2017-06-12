namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxTemplateMessageDO {

	1: optional i32 id,	//主key
	2: optional i32 sysTemplateId,	//模板ID
	3: optional string wxTemplateId,	//微信模板ID
	4: optional i32 display,	//是否显示
	5: optional i32 priority,	//排序
	6: optional i32 wechatId,	//所属公众号
	7: optional i32 disable,	//是否可用
	8: optional string url,	//跳转URL
	9: optional string topcolor,	//消息头部颜色
	10: optional string first,	//问候语
	11: optional string remark	//结束语

}
