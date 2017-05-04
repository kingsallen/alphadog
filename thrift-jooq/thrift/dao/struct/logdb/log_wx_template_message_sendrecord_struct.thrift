namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogWxTemplateMessageSendrecordDO {

	1: optional i32 id,	//主key
	2: optional i32 templateId,	//我的模板ID
	3: optional i32 wechatId,	//所属公众号
	4: optional i64 msgid,	//发送消息ID
	5: optional string openId,	//微信用户OPENID
	6: optional string url,	//link
	7: optional string topcolor,	//信息顶部颜色
	8: optional string jsondata,	//发送的json数据
	9: optional i32 errcode,	//返回结果值
	10: optional string errmsg,	//返回提示信息
	11: optional string sendtime,	//发送时间
	12: optional string updatetime,	//反馈状态时间
	13: optional string sendstatus,	//发送状态
	14: optional i32 sendtype,	//发送类型 0:微信 1:邮件 2:短信
	15: optional string accessToken	//发送时的access_token

}
