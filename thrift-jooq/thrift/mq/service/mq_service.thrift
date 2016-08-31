include "../struct/mq_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.mq.service

/*
  消息队列服务接口
*/
service MqService {

   common_struct.Response messageTemplateNotice(1:mq_struct.MessageTemplateNoticeStruct messageTemplateNoticeStruct);

}
