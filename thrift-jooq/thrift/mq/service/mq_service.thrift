include "../struct/mq_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mq.service

/*
  消息队列服务接口
*/
service MqService {

   common_struct.Response messageTemplateNotice(1:mq_struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct);
   
   common_struct.Response sendEMail(1: mq_struct.EmailStruct emailStruct);
   
   common_struct.Response sendAuthEMail(1: map<string, string> params, 2: i32 eventType, 3: string email, 4: string subject 5: string senderName, 6: string senderDisplay);

   common_struct.Response sendMandrilEmail(1: mq_struct.MandrillEmailStruct mandrillEmailStruct);

   void sendMandrilEmailList(1: mq_struct.MandrillEmailListStruct mandrillEmailStruct);

   common_struct.Response sendSMS(1: mq_struct.SmsType smsType, 2: string mobile, 3: map<string, string> data, 4: string sys, 5: string ip);

   common_struct.Response sendMessageAndEmailToDelivery(1: mq_struct.MessageEmailStruct messageEmailStruct);

   common_struct.Response sendMessageAndEmailToDeliveryNew(1: mq_struct.MessageEmailStruct messageEmailStruct);

   list<mq_struct.MessageBody> listMessages(1: i32 wechatId) throws (1: common_struct.BIZException e);
}
