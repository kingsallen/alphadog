namespace java com.moseeker.thrift.gen.dao.struct.logdb
namespace py thrift_gen.gen.dao.struct.logdb


struct LogCronjobDO {

	1: optional i32 id,	//null
	2: optional i32 cronjobId,	//null
	3: optional string startTime,	//开始时间
	4: optional string endTime,	//结束时间
	5: optional i32 result	//运行结果 1 失败, 0 成功

}


struct LogEmailSendrecordDO {

	1: optional i32 id,	//null
	2: optional i8 type,	//邮件类型
	3: optional i8 sys,	//来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 9:script
	4: optional string email,	//邮箱地址
	5: optional string content,	//邮件变量部分内容以json方式
	6: optional string createTime	//null

}


struct LogHrOperationRecordDO {

	1: optional i32 id,	//null
	2: optional i32 type,	//0:无效1：hr操作职位发布人
	3: optional i32 hraccountId,	//user_hr_account.id
	4: optional string description,	//记录描述
	5: optional string createTime	//null

}


struct LogSmsSendrecordDO {

	1: optional i32 id,	//null
	2: optional i8 sys,	//来自系统，0:未知 1:platform 2:qx 3:hr 4:官网 5:基础服务 9:script
	3: optional double mobile,	//null
	4: optional string msg,	//发送内容
	5: optional string ip,	//IP
	6: optional string createTime	//null

}


struct LogWxMenuRecordDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//null
	3: optional string name,	//null
	4: optional string json,	//菜单的json数据
	5: optional string createTime,	//null
	6: optional i32 errcode,	//微信调用返回的errcode
	7: optional string errmsg	//微信调用返回的errmsg

}


struct LogWxMessageRecordDO {

	1: optional i32 id,	//主key
	2: optional i32 templateId,	//我的模板ID
	3: optional i32 wechatId,	//所属公众号
	4: optional i32 msgid,	//发送消息ID
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


struct LogWxTemplateMessageSendrecordDO {

	1: optional i32 id,	//主key
	2: optional i32 templateId,	//我的模板ID
	3: optional i32 wechatId,	//所属公众号
	4: optional i32 msgid,	//发送消息ID
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
