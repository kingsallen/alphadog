namespace java com.moseeker.thrift.gen.mq.struct

/*
  消息模板通知数据结构 - data
*/

struct MessageTplDataCol {
    1: optional string color,
    2: optional string value
}

/*
  消息模板通知数据结构
*/
struct MessageTemplateNoticeStruct {
    1: optional string openid,
    2: optional i32 sys_template_id,
    3: optional string access_token,
    4: optional string url,
    5: optional i32 wechat_id,
    6: optional map<string, map<string, MessageTplDataCol>> data
}