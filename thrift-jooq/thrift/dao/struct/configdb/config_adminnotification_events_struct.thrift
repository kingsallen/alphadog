namespace java com.moseeker.thrift.gen.dao.struct.configdb
namespace py thrift_gen.gen.dao.struct.configdb


struct ConfigAdminnotificationEventsDO {

	1: optional i32 id,	//null
	2: optional string projectAppid,	//项目appid
	3: optional string eventKey,	//事件标识符，大写英文
	4: optional string eventName,	//事件名称
	5: optional string eventDesc,	//事件描述
	6: optional i32 thresholdValue,	//触发几次后通知
	7: optional i32 thresholdInterval,	//单位秒，0表示不限制，  thresholdinterval和thresholdvalue搭配使用，表达 每分钟超过5次 报警。
	8: optional i8 enableNotifybyEmail,	//是否email通知， 1是 0否
	9: optional i8 enableNotifybySms,	//是否短信通知， 1是 0否
	10: optional i8 enableNotifybyWechattemplatemessage,	//是否微信模板消息通知， 1是 0否
	11: optional i32 groupid,	//发送给哪个组
	12: optional string createTime	//null

}
