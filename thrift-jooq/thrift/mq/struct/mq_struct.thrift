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
    1: optional i32 user_id,
    2: optional i32 sys_template_id,
    3: optional string url,
    4: optional i32 company_id,
    5: optional map<string, MessageTplDataCol> data,
    6: optional byte enable_qx_retry = 1,
    7: optional byte delay = 0
}

struct EmailStruct {
    1: 		i32 user_id,
    2: 		string email,
    3: 		string url,
    4: optional i32 eventType,
    5: optional string subject
}
