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
    2: optional i8 type,
    3: optional i32 sys_template_id,
    4: optional string url,
    5: optional i32 company_id,
    6: optional map<string, MessageTplDataCol> data,
    7: optional i8 enable_qx_retry = 1,
    8: optional i64 delay = 0,
    9: optional string validators,
    10: optional string id,
    11: optional string validators_params,
    12: optional i32 wx_id
}

struct EmailStruct {
    1: 		i32 user_id,
    2: 		string email,
    3: 		string url,
    4: optional i32 eventType,
    5: optional string subject
}

struct MessageEmailStruct {
    1: 		i32 position_id,
    2: 		i32 applier_id,
    3: 		i32 recommender_user_id,
    4: i32 origin,
    5: i32 apply_type,
    6: i32 email_status,
    7: i32 application_id
}


struct MandrillEmailStruct {
       1: 		string templateName,
       2: 		string to_email,
       3: optional string to_name,
       4: optional	map<string,string> mergeVars,
       5: optional string from_email,
       6: optional string from_name,
       7: optional string subject

   }

   struct MandrillEmailListStruct {
       1: 		string templateName,
       2: 		list<map<string,string>> to,
       3: optional string mergeVars,
       4: optional string from_email,
       5: optional string from_name,
       6: optional string subject,
       7: optional i32    type,
       8: optional i32    company_id

   }

enum SmsType {
	EMPLOYEE_MERGE_ACCOUNT_SMS,
	RANDOM_SMS,
	RANDOM_PWD_SMS,
	POSITION_FAV_SMS,
	NEW_APPLICATION_TO_HR_SMS,
	NEW_APPLIACATION_TO_APPLIER_SMS,
	APPLICATION_IS_VIEW_SMS,
	APPLICATION_REJECT_SMS,
	APPLICATION_CANCEL_REJECT_SMS,
	APPLICATION_APPROVED_SMS,
	APPLICATION_INTERVIEW_SMS,
	APPLICATION_ENTRY_SMS,
	UPDATE_SYSUSER_SMS,
	REGISTERED_THREE_DAYS_SMS,
	APPLIER_REMIND_EMAIL_ATTACHMENT_SMS,
	APPLIER_REMIND_EMAIL_ATTACHMENT_COM_SMS,
	HR_INVITE_BYPASS_ACCOUNT_SMS,
	HR_BYPASS_ACCOUNT_SMS,
	HR_BYPASS_ACCOUNT_OPEN_SMS,
	HR_BYPASS_ACCOUNT_REJECT_SMS,
	APPLIER_EMAIL_APP_SUC_SMS,
	APPLIER_EMAIL_APP_NO_ATTACH_SMS,
	APPLIER_EMAIL_APP_ATTACH_ERROR_SMS,
	APPLIER_EMAIL_APP_ATTACH_OVERSIZE_SMS,
	APPLIER_EMAIL_APP_RESOLVE_FAIL_SMS,
	APPLIER_EMAIL_APP_ATTACH_RESOLVE_FAIL_SMS,
	APPLIER_APP_ATTACH_RESOLVE_SUC_SMS,
	APPLIER_APP_ATTACH_RESOLVE_FAIL_SMS,
	APPLIER_APP_ATTACH_RESOLVE_ERROR_SMS,
	PPLIER_APP_ATTACH_RESOLVE_OVERSIZE_SMS,
	APPLIER_APP_RESOLVE_FAIL_SMS,
	ALARM_SMS
}

struct MessageBody {
    1: optional i32 id,                    //模板消息编号
    2: optional string title,              //模板标题
    3: optional string sendCondition,      //触发条件
    4: optional string sendTime,           //发送时间
    5: optional string sendTo,             //发送对象
    6: optional string sample,             //内容实例
    7: optional string first,              //模板消息first文案
    8: optional string priority,           //排序
    9: optional string remark,             //模板消息remark文案
    10: optional i8 status,                //是否开启, 1:开启, 0:关闭
    11: optional string customFirst,       //公司下配置的模板消息first文案
    12: optional string customRemark,      //公司下配置的模板消息remark文案
    13: optional list<FlexibleField> flexibleFields,      //可更改的内容
    14: optional WxMessageFrequency frequency // 发送模板消息频率时间
}

struct WxMessageFrequency {
    1: optional string fixedTime,
    2: optional list<WxMessageFrequencyOption> options ,
    3: optional string value ,
    4: optional string defaultValue
}

struct WxMessageFrequencyOption {
    1: optional string text,
    2: optional string value,
    3: optional string send_time
}

struct FlexibleField {
    1: optional string key,                 //关键词
    2: optional string name,                //名称
    3: optional string value,               //值
    4: optional bool editable,              //是否可以更改
}
