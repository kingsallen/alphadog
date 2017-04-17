namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigSysTemplateMessageLibraryDO {

	1: optional i32 id,	//主key
	2: optional string title,	//模板标题
	3: optional string primaryIndustry,	//一级行业
	4: optional string twoIndustry,	//二级行业
	5: optional string content,	//详细内容
	6: optional string sample,	//内容示例
	7: optional i32 display,	//是否显示
	8: optional i32 priority,	//排序
	9: optional i32 disable,	//是否可用
	10: optional double type,	//模板类别 0:微信 1:邮件 2:短信 3:申请模板 4:其他
	11: optional string templateIdShort,	//模板库中模板的编号
	12: optional string sendCondition,	//触发条件
	13: optional string sendtime,	//发送时间
	14: optional string sendto,	//发送对象
	15: optional string first,	//消息模板first文案
	16: optional string remark,	//消息模板remark文案
	17: optional string url	//跳转页面

}
