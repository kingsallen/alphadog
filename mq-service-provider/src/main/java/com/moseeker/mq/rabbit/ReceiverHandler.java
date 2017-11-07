package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.entity.PersonaRecomEntity;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/8/3.
 */
@Component
@PropertySource("classpath:common.properties")
public class ReceiverHandler {

    private static Logger log = LoggerFactory.getLogger(ReceiverHandler.class);

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private LogDeadLetterDao logDeadLetterDao;

    @Autowired
    private MessageTemplateEntity messageTemplateEntity;

    @Autowired
    private TemplateMsgProducer templateMsgProducer;

    @Autowired
    private Environment env;

    @Autowired
    private PersonaRecomEntity personaRecomEntity;



    @RabbitListener(queues = "#{addAwardQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void addAwardHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            employeeEntity.addAwardBefore(jsonObject.getIntValue("employeeId"), jsonObject.getIntValue("companyId"),
                    jsonObject.getIntValue("positionId"), jsonObject.getIntValue("templateId"), jsonObject.getIntValue("berecomUserId"), jsonObject.getIntValue("applicationId"));
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(Integer.valueOf(message.getMessageProperties().getAppId()));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(message.getMessageProperties().getReceivedExchange());
            logDeadLetterDO.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
            logDeadLetterDO.setQueueName(message.getMessageProperties().getConsumerQueue());
            logDeadLetterDao.addData(logDeadLetterDO);
            log.error(e.getMessage(), e);
        }
    }
    @RabbitListener(queues = "#{sendTemplateQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerMessageTemplate(Message message, Channel channel){
        String msgBody = "{}";
        try{
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("rabitmq的参数是========"+jsonObject.toJSONString());
            int userId=jsonObject.getIntValue("user_id");
            int companyId=jsonObject.getIntValue("company_id");
            int type=jsonObject.getIntValue("type");
            int templateId=jsonObject.getIntValue("template_id");
            String url=jsonObject.getString("url");
            String jobName=jsonObject.getString("job_name");
            String companyName=jsonObject.getString("company_name");
            if(StringUtils.isEmpty(url)){
               url=handlerUrl(type);
            }
            String enable_qx_retry=jsonObject.getString("enable_qx_retry");
            MessageTemplateNoticeStruct messageTemplate=messageTemplateEntity.handlerTemplate(userId,companyId,templateId,type,url,jobName,companyName);
            log.info("messageTemplate========"+JSONObject.toJSONString(messageTemplate));
            if(messageTemplate!=null){
                if(StringUtils.isNotEmpty(enable_qx_retry)){
                    messageTemplate.setEnable_qx_retry(Byte.parseByte(enable_qx_retry));
                }
                templateMsgProducer.messageTemplateNotice(messageTemplate);
                personaRecomEntity.updateIsSendPersonaRecom(userId,1,20);

            }else{
                this.handleTemplateLogDeadLetter(message,msgBody,"没有查到模板所需的具体内容");
            }
        }catch(Exception e){
            this.handleTemplateLogDeadLetter(message,msgBody,"没有查到模板所需的具体内容");
            log.error(e.getMessage(), e);
        }
    }
    @RabbitListener(queues = "#{personaRecomQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPersonRecom(Message message, Channel channel){
        String msgBody = "{}";
        try{
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("推送职位的rabitmq的参数是========"+jsonObject.toJSONString());
            int userId=jsonObject.getIntValue("user_id");
            String positionIds=jsonObject.getString("position_ids");
            if(userId!=0&&StringUtils.isNotEmpty(positionIds)){
                int result=personaRecomEntity.handlePersonaRecomData(userId,positionIds);
            }

        }catch(Exception e){
            this.handleTemplateLogDeadLetter(message,msgBody,"插入推荐职位数据失败");
            log.error(e.getMessage(), e);
        }
    }

    private void handleTemplateLogDeadLetter(Message message,String msgBody,String errorMessage){
        LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
        logDeadLetterDO.setAppid(NumberUtils.toInt(message.getMessageProperties().getAppId(), 0));
        logDeadLetterDO.setErrorLog(errorMessage);
        logDeadLetterDO.setMsg(msgBody);
        logDeadLetterDO.setExchangeName(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedExchange(), ""));
        logDeadLetterDO.setRoutingKey(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedRoutingKey(), ""));
        logDeadLetterDO.setQueueName(StringUtils.defaultIfBlank(message.getMessageProperties().getConsumerQueue(), ""));
        logDeadLetterDao.addData(logDeadLetterDO);
    }
    /*
      处理url
      */
    private String handlerUrl(int type){
        String url="";
        if(type==1){
            url=env.getProperty("message.template.fans.url");
        }else if(type==2||type==3){

        }else if(type==4){

        }
        return url;
    }


}
