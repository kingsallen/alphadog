include "../struct/mq_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mq.service

/*
  消息队列服务接口
*/
service MqService {

   common_struct.Response messageTemplateNotice(1:mq_struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct);
   
   common_struct.Response sendEMail(1: mq_struct.EmailStruct emailStruct);
   
   common_struct.Response sendAuthEMail(1: map<string, string> params, 2: i32 eventType, 3: string email, 4: string subject);

   common_struct.Response sendMandrilEmail(1: mq_struct.MandrillEmailStruct mandrillEmailStruct);

}
